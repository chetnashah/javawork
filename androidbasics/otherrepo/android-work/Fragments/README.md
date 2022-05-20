
Debug using `adb shell dumpsys activity com.example.com.MainActivity`

Fragment BackStackRecord
```
final class BackStackRecord extends FragmentTransaction implements FragmentManager.BackStackEntry, FragmentManagerImpl.OpGenerator
```

FragmentTransactions

```java
interface BackStackEntry{
    getId();
    getName();
    getBreadCrumbTitle();
}
```


`BackStackRecord.Op` Interface
```
class Op {
    int cmd;
    Fragment fragment;
    int enterAnim;
    int exitAnim;
    Op(int cmd, Fragment fragment) {
        this.cmd = cmd;
        this.fragment = fragment;
    }

}
```

`BackStackRecord` is a realization of `FragmentTransaction` if backstack is allowed:
```
class BackStackRecord extends FragmentTransaction implements FragmentManager.BackStackEntry{
    ArrayList<Op> mOps;
    String mName;
    int mIndex;

    // methods like add, replace, remove, hide, show
    // all push an Op to mOps

    executeOps();
}
```

All operations:
```java
   static final int OP_NULL = 0;
    static final int OP_ADD = 1;
    static final int OP_REPLACE = 2;
    static final int OP_REMOVE = 3;
    static final int OP_HIDE = 4;
    static final int OP_SHOW = 5;
    static final int OP_DETACH = 6;
    static final int OP_ATTACH = 7;
    static final int OP_SET_PRIMARY_NAV = 8;
    static final int OP_UNSET_PRIMARY_NAV = 9;
```

`BackStackState`:
```java
int[] mOps;
String mName;
int mIndex;

    // constructor takes a BackStackRecord and 
    // populates mOps 
    public BackStackState(BackStackRecord bse) {
        final int numOps = bse.mOps.size();
        mOps = new int[numOps * 6];

        if (!bse.mAddToBackStack) {
            throw new IllegalStateException("Not on back stack");
        }

        int pos = 0;
        for (int opNum = 0; opNum < numOps; opNum++) {
            final BackStackRecord.Op op = bse.mOps.get(opNum);
            mOps[pos++] = op.cmd;
            mOps[pos++] = op.fragment != null ? op.fragment.mIndex : -1;
            mOps[pos++] = op.enterAnim;
            mOps[pos++] = op.exitAnim;
            mOps[pos++] = op.popEnterAnim;
            mOps[pos++] = op.popExitAnim;
        }
        mName = bse.mName;
        mIndex = bse.mIndex;
        mReorderingAllowed = bse.mReorderingAllowed;
    }
```

```java
interface FragmentTransaction{
    Ft add(Id id, Fragment f);
    Ft replace(Id id, Fragment f);
    Ft attach(Fragment f);
    Ft detach(Fragment f);
    Ft remove(Fragment f);
    Ft hide(Fragment f);
    Ft show(Fragment f);

    Ft addToBackStack(String s);
    Ft setReOrderingAllowed(Boolean b);

    int commit();
    Ft runOnCommit(Runnable r);
}
```


FragmentManagerImpl:
`final class FragmentManagerImpl extends FragmentManager implements LayoutInflater.Factory2 `

Manages all the state for FragmentManager
```java
final class FragmentManagerImpl extends FragmentManager implements LayoutInflater.Factory2 {
    ArrayList<OpGenerator> mPendingActions;
    ArrayList<Fragment> mAdded;
    SparseArray<Fragment> mActive;
    int mNextFragmentIndex = 0;
    ArrayList<BackStackRecord> mBackStack;
    // Must be accessed while locked.
    ArrayList<BackStackRecord> mBackStackIndices;
    ArrayList<Integer> mAvailBackStackIndices;
    
    Fragment mParent;
    @Nullable Fragment mPrimaryNav;
}
```

In dumpsys, active fragments i.e. contents of `mActive` shown as:
```
Active Fragments in 520d462:
      #0: PhotoGridFragment{57c79f3 #0 id=0x7f070045}
        mFragmentId=#7f070045 mContainerId=#7f070045 mTag=null
        mState=4 mIndex=0 mWho=android:fragment:0 mBackStackNesting=1
        mAdded=true mRemoving=false mFromLayout=false mInLayout=false
        mHidden=false mDetached=false mMenuVisible=true mHasMenu=false
        mRetainInstance=false mRetaining=false mUserVisibleHint=true
        mFragmentManager=FragmentManager{520d462 in HostCallbacks{81a26b0}}
        mHost=android.support.v4.app.FragmentActivity$HostCallbacks@81a26b0
        mContainer=android.widget.FrameLayout{574435f V.E...... ........ 0,0-1080,1884 #7f070045 app:id/fragment_placeholder}
        mView=android.support.constraint.ConstraintLayout{981d1ac V.E...... ........ 0,0-1080,1884}
        mInnerView=android.support.constraint.ConstraintLayout{981d1ac V.E...... ........ 0,0-1080,1884}
        Child FragmentManager{70c4929 in PhotoGridFragment{57c79f3}}:
          FragmentManager misc state:
            mHost=android.support.v4.app.FragmentActivity$HostCallbacks@81a26b0
            mContainer=android.support.v4.app.Fragment$2@d3bd4ae
            mParent=PhotoGridFragment{57c79f3 #0 id=0x7f070045}
            mCurState=4 mStateSaved=false mStopped=false mDestroyed=false
```

In dumpsys, added fragments i.e. contents of `mAdded` shown as:
```
  Added Fragments:
      #0: PhotoGridFragment{57c79f3 #0 id=0x7f070045}
```

BackStack (`mBackStack`) will be shown like below:
```
    Back Stack:
      #0: BackStackEntry{9dac24f #0}
        mName=null mIndex=0 mCommitted=true
        Operations:
          Op #0: ADD PhotoGridFragment{57c79f3 #0 id=0x7f070045}
    Back Stack Indices:
      #0: BackStackEntry{9dac24f #0}
```

Similarly `Back Stack Indices:` for `mBackStackIndices`,
`mAvailBackStackIndices:` for `mAvailableBackStackIndices`
and 
`Pending Actions:` for `mPendingActions`


### Fragment Instance

```java
class Fragment {
    int mIndex; // index into active fragments array
    boolean mAdded; // true if this fragment is in list of added frags
    // If set this fragment is being removed from its activity.
    boolean mRemoving;
    // The optional identifier for this fragment -- either the container ID if it
    // was dynamically added to the view hierarchy, or the ID supplied in
    // layout.
    int mFragmentId;
    // Set to true when the app has requested that this fragment be hidden
    // from the user.
    boolean mHidden;
    // Set to true when the app has requested that this fragment be deactivated.
    boolean mDetached;
}
```
