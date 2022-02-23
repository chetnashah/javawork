

### AM state holder
definition:
```java
public final class ActiveServices {
}
```

### State dump

dump serviceMap.mServicesByInstanceName map list
```
dumpsys activity services
```

### important members

```java
    /**
     * Master service bookkeeping, keyed by user number.
     */
    final SparseArray<ServiceMap> mServiceMap = new SparseArray<>();
    /**
     * List of services that we have been asked to start,
     * but haven't yet been able to.  It is used to hold start requests
     * while waiting for their corresponding application thread to get
     * going.
     */
    final ArrayList<ServiceRecord> mPendingServices = new ArrayList<>();

    /**
     * List of services that are scheduled to restart following a crash.
     */
    final ArrayList<ServiceRecord> mRestartingServices = new ArrayList<>();

    /**
     * List of services that are in the process of being destroyed.
     */
    final ArrayList<ServiceRecord> mDestroyingServices = new ArrayList<>();
```

### inner class ServiceMap

```java
    /**
     * Information about services for a single user.
     */
    final class ServiceMap extends Handler {
        final ArrayMap<ComponentName, ServiceRecord> mServicesByInstanceName = new ArrayMap<>();
        final ArrayMap<Intent.FilterComparison, ServiceRecord> mServicesByIntent = new ArrayMap<>();
        final ArrayList<ServiceRecord> mStartingBackground = new ArrayList<>();
        final ArrayMap<String, ActiveForegroundApp> mActiveForegroundApps = new ArrayMap<>();
    }
```

#### ServiceMap constructor and handling

```java
      ServiceMap(Looper looper, int userId) {
          super(looper);
          mUserId = userId;
      }

      @Override
      public void handleMessage(Message msg) {
          switch (msg.what) {
              case MSG_BG_START_TIMEOUT: {
                  synchronized (mAm) {
                      rescheduleDelayedStartsLocked();
                  }
              } break;
              case MSG_UPDATE_FOREGROUND_APPS: {
                  updateForegroundApps(this);
              } break;
              case MSG_ENSURE_NOT_START_BG: {
                  synchronized (mAm) {
                      rescheduleDelayedStartsLocked();
                  }
              } break;
          }
      }
```


### Inner class ActiveForegroundApp

```java
    /**
     * Information about an app that is currently running one or more foreground services.
     * (This maps directly to the running apps we show in the notification.)
     */
    static final class ActiveForegroundApp {
        String mPackageName;
        int mUid;
        CharSequence mLabel;
        boolean mShownWhileScreenOn;
        boolean mAppOnTop;
        boolean mShownWhileTop;
        long mStartTime;
        long mStartVisibleTime;
        long mEndTime;
        int mNumActive;

        // Temp output of foregroundAppShownEnoughLocked
        long mHideTime;
    }

```


### Important method startServiceLocked

```java
ComponentName startServiceLocked(IApplicationThread caller, Intent service, String resolvedType,
        int callingPid, int callingUid, boolean fgRequired,
        String callingPackage, @Nullable String callingFeatureId, final int userId,
        boolean allowBackgroundActivityStarts, @Nullable IBinder backgroundActivityStartsToken){

        final boolean callerFg;
        if (caller != null) {
            final ProcessRecord callerApp = mAm.getRecordForAppLOSP(caller);
            if (callerApp == null) {
                throw new SecurityException(
                        "Unable to find app for caller " + caller
                        + " (pid=" + callingPid
                        + ") when starting service " + service);
            }
            callerFg = callerApp.mState.getSetSchedGroup() != ProcessList.SCHED_GROUP_BACKGROUND;
        } else {
            callerFg = true;
        }

        // retrieve and throw error if not found
        ServiceLookupResult res =
            retrieveServiceLocked(service, null, resolvedType, callingPackage,
                    callingPid, callingUid, userId, true, callerFg, false, false);

        if (res.record == null) {
            return new ComponentName("!", res.permission != null
                    ? res.permission : "private to package");
        }

        // get service record and check restrictions
        ServiceRecord r = res.record;
        setFgsRestrictionLocked(callingPackage, callingPid, callingUid, service, r, userId,
                allowBackgroundActivityStarts);

        if (bgLaunch && appRestrictedAnyInBackground(r.appInfo.uid, r.packageName)) {
            if (DEBUG_FOREGROUND_SERVICE) {
                Slog.d(TAG, "Forcing bg-only service start only for " + r.shortInstanceName
                        + " : bgLaunch=" + bgLaunch + " callerFg=" + callerFg);
            }
            forcedStandby = true;
        }

        if (fgRequired) {
            logFgsBackgroundStart(r);
            if (r.mAllowStartForeground == REASON_DENIED && isBgFgsRestrictionEnabled(r)) {
                String msg = "startForegroundService() not allowed due to "
                        + "mAllowStartForeground false: service "
                        + r.shortInstanceName;
                Slog.w(TAG, msg);
                showFgsBgRestrictedNotificationLocked(r);
                logFGSStateChangeLocked(r,
                        FrameworkStatsLog.FOREGROUND_SERVICE_STATE_CHANGED__STATE__DENIED,
                        0);
                if (CompatChanges.isChangeEnabled(FGS_START_EXCEPTION_CHANGE_ID, callingUid)) {
                    throw new ForegroundServiceStartNotAllowedException(msg);
                }
                return null;
            }
        }
        // If this is a direct-to-foreground start, make sure it is allowed as per the app op.
        boolean forceSilentAbort = false;
        if (fgRequired) {
            final int mode = mAm.getAppOpsManager().checkOpNoThrow(
                    AppOpsManager.OP_START_FOREGROUND, r.appInfo.uid, r.packageName);
            switch (mode) {
                case AppOpsManager.MODE_ALLOWED:
                case AppOpsManager.MODE_DEFAULT:
                    // All okay.
                    break;
                case AppOpsManager.MODE_IGNORED:
                    // Not allowed, fall back to normal start service, failing siliently
                    // if background check restricts that.
                    Slog.w(TAG, "startForegroundService not allowed due to app op: service "
                            + service + " to " + r.shortInstanceName
                            + " from pid=" + callingPid + " uid=" + callingUid
                            + " pkg=" + callingPackage);
                    fgRequired = false;
                    forceSilentAbort = true;
                    break;
                default:
                    return new ComponentName("!!", "foreground not allowed as per app op");
            }
        }

        // If this isn't a direct-to-foreground start, check our ability to kick off an
        // arbitrary service
        if (forcedStandby || (!r.startRequested && !fgRequired)) {
            // Before going further -- if this app is not allowed to start services in the
            // background, then at this point we aren't going to let it period.
            final int allowed = mAm.getAppStartModeLOSP(r.appInfo.uid, r.packageName,
                    r.appInfo.targetSdkVersion, callingPid, false, false, forcedStandby);
            if (allowed != ActivityManager.APP_START_MODE_NORMAL) {
                Slog.w(TAG, "Background start not allowed: service "
                        + service + " to " + r.shortInstanceName
                        + " from pid=" + callingPid + " uid=" + callingUid
                        + " pkg=" + callingPackage + " startFg?=" + fgRequired);
                if (allowed == ActivityManager.APP_START_MODE_DELAYED || forceSilentAbort) {
                    // In this case we are silently disabling the app, to disrupt as
                    // little as possible existing apps.
                    return null;
                }
                if (forcedStandby) {
                    // This is an O+ app, but we might be here because the user has placed
                    // it under strict background restrictions.  Don't punish the app if it's
                    // trying to do the right thing but we're denying it for that reason.
                    if (fgRequired) {
                        if (DEBUG_BACKGROUND_CHECK) {
                            Slog.v(TAG, "Silently dropping foreground service launch due to FAS");
                        }
                        return null;
                    }
                }
                // This app knows it is in the new model where this operation is not
                // allowed, so tell it what has happened.
                UidRecord uidRec = mAm.mProcessList.getUidRecordLOSP(r.appInfo.uid);
                return new ComponentName("?", "app is in background uid " + uidRec);
            }
        }

        // At this point we've applied allowed-to-start policy based on whether this was
        // an ordinary startService() or a startForegroundService().  Now, only require that
        // the app follow through on the startForegroundService() -> startForeground()
        // contract if it actually targets O+.
        if (r.appInfo.targetSdkVersion < Build.VERSION_CODES.O && fgRequired) {
            if (DEBUG_BACKGROUND_CHECK || DEBUG_FOREGROUND_SERVICE) {
                Slog.i(TAG, "startForegroundService() but host targets "
                        + r.appInfo.targetSdkVersion + " - not requiring startForeground()");
            }
            fgRequired = false;
        }
        return startServiceInnerLocked(r, service, callingUid, callingPid, fgRequired, callerFg,
                allowBackgroundActivityStarts, backgroundActivityStartsToken);
        }
```


### startServiceInnerLocked 8 args

```java
    private ComponentName startServiceInnerLocked(ServiceRecord r, Intent service,
            int callingUid, int callingPid, boolean fgRequired, boolean callerFg,
            boolean allowBackgroundActivityStarts, @Nullable IBinder backgroundActivityStartsToken)
            throws TransactionTooLargeException {
        NeededUriGrants neededGrants = mAm.mUgmInternal.checkGrantUriPermissionFromIntent(
                service, callingUid, r.packageName, r.userId);
        if (fgRequired) {
            // We are now effectively running a foreground service.
            synchronized (mAm.mProcessStats.mLock) {
                final ServiceState stracker = r.getTracker();
                if (stracker != null) {
                    stracker.setForeground(true, mAm.mProcessStats.getMemFactorLocked(),
                            r.lastActivity);
                }
            }
            mAm.mAppOpsService.startOperation(AppOpsManager.getToken(mAm.mAppOpsService),
                    AppOpsManager.OP_START_FOREGROUND, r.appInfo.uid, r.packageName, null,
                    true, false, null, false, AppOpsManager.ATTRIBUTION_FLAGS_NONE,
                    AppOpsManager.ATTRIBUTION_CHAIN_ID_NONE);
        }

        final ServiceMap smap = getServiceMapLocked(r.userId);
        boolean addToStarting = false;
        if (!callerFg && !fgRequired && r.app == null
                && mAm.mUserController.hasStartedUserState(r.userId)) {
            ProcessRecord proc = mAm.getProcessRecordLocked(r.processName, r.appInfo.uid);
            if (proc == null || proc.mState.getCurProcState() > PROCESS_STATE_RECEIVER) {
                // If this is not coming from a foreground caller, then we may want
                // to delay the start if there are already other background services
                // that are starting.  This is to avoid process start spam when lots
                // of applications are all handling things like connectivity broadcasts.
                // We only do this for cached processes, because otherwise an application
                // can have assumptions about calling startService() for a service to run
                // in its own process, and for that process to not be killed before the
                // service is started.  This is especially the case for receivers, which
                // may start a service in onReceive() to do some additional work and have
                // initialized some global state as part of that.
                if (DEBUG_DELAYED_SERVICE) Slog.v(TAG_SERVICE, "Potential start delay of "
                        + r + " in " + proc);
                if (r.delayed) {
                    // This service is already scheduled for a delayed start; just leave
                    // it still waiting.
                    if (DEBUG_DELAYED_STARTS) Slog.v(TAG_SERVICE, "Continuing to delay: " + r);
                    return r.name;
                }
                if (smap.mStartingBackground.size() >= mMaxStartingBackground) {
                    // Something else is starting, delay!
                    Slog.i(TAG_SERVICE, "Delaying start of: " + r);
                    smap.mDelayedStartList.add(r);
                    r.delayed = true;
                    return r.name;
                }
                if (DEBUG_DELAYED_STARTS) Slog.v(TAG_SERVICE, "Not delaying: " + r);
                addToStarting = true;
            } else if (proc.mState.getCurProcState() >= ActivityManager.PROCESS_STATE_SERVICE) {
                // We slightly loosen when we will enqueue this new service as a background
                // starting service we are waiting for, to also include processes that are
                // currently running other services or receivers.
                addToStarting = true;
                if (DEBUG_DELAYED_STARTS) Slog.v(TAG_SERVICE,
                        "Not delaying, but counting as bg: " + r);
            } else if (DEBUG_DELAYED_STARTS) {
                StringBuilder sb = new StringBuilder(128);
                sb.append("Not potential delay (state=").append(proc.mState.getCurProcState())
                        .append(' ').append(proc.mState.getAdjType());
                String reason = proc.mState.makeAdjReason();
                if (reason != null) {
                    sb.append(' ');
                    sb.append(reason);
                }
                sb.append("): ");
                sb.append(r.toString());
                Slog.v(TAG_SERVICE, sb.toString());
            }
        } else if (DEBUG_DELAYED_STARTS) {
            if (callerFg || fgRequired) {
                Slog.v(TAG_SERVICE, "Not potential delay (callerFg=" + callerFg + " uid="
                        + callingUid + " pid=" + callingPid + " fgRequired=" + fgRequired + "): " + r);
            } else if (r.app != null) {
                Slog.v(TAG_SERVICE, "Not potential delay (cur app=" + r.app + "): " + r);
            } else {
                Slog.v(TAG_SERVICE,
                        "Not potential delay (user " + r.userId + " not started): " + r);
            }
        }

        if (allowBackgroundActivityStarts) {
            r.allowBgActivityStartsOnServiceStart(backgroundActivityStartsToken);
        }

        ComponentName cmp = startServiceInnerLocked(smap, service, r, callerFg, addToStarting);
        return cmp;

    }
```

### startServiceInnerLocked 5 args

```java
    ComponentName startServiceInnerLocked(ServiceMap smap, Intent service, ServiceRecord r,
            boolean callerFg, boolean addToStarting) throws TransactionTooLargeException {
        
        mAm.mBatteryStatsService.noteServiceStartRunning(uid, packageName, serviceName);
        String error = bringUpServiceLocked(r, service.getFlags(), callerFg,
                false /* whileRestarting */,
                false /* permissionsReviewRequired */,
                false /* packageFrozen */,
                true /* enqueueOomAdj */);
        /* Will be a no-op if nothing pending */
        mAm.updateOomAdjPendingTargetsLocked(OomAdjuster.OOM_ADJ_REASON_START_SERVICE);
        if (error != null) {
            return new ComponentName("!!", error);
        }

        if (r.startRequested && addToStarting) {
            boolean first = smap.mStartingBackground.size() == 0;
            smap.mStartingBackground.add(r);
            r.startingBgTimeout = SystemClock.uptimeMillis() + mAm.mConstants.BG_START_TIMEOUT;
            if (DEBUG_DELAYED_SERVICE) {
                RuntimeException here = new RuntimeException("here");
                here.fillInStackTrace();
                Slog.v(TAG_SERVICE, "Starting background (first=" + first + "): " + r, here);
            } else if (DEBUG_DELAYED_STARTS) {
                Slog.v(TAG_SERVICE, "Starting background (first=" + first + "): " + r);
            }
            if (first) {
                smap.rescheduleDelayedStartsLocked();
            }
        } else if (callerFg || r.fgRequired) {
            smap.ensureNotStartingBackgroundLocked(r);
        }
        return r.name;
    }
```