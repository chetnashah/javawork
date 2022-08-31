The Android OS runs with limited hardware resources, i.e. CPU/RAM/Power. To strive for the better performance, Oom Ajuster is introduced to tweak the following 3 major factors:

https://cs.android.com/android/platform/superproject/+/master:frameworks/base/services/core/java/com/android/server/am/OomAdjuster.md

## Process State
Wildly used by the System Server, i.e., determine if it's foreground or not, change the GC behavior, etc.
Defined in `ActivityManager#PROCESS_STATE_*`

## Oom Adj score
Used by the lmkd to determine which process should be expunged on memory pressure.
Defined in `ProcessList#*_ADJ`

## Scheduler Group
Used to tweak the process group, thread priorities.
Top process is scheduled to be running on a dedicated big core, while foreground processes take the other big cores; background processes stay with LITTLE cores instead.
