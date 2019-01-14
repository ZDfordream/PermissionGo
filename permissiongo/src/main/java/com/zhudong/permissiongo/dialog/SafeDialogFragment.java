package com.zhudong.permissiongo.dialog;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * 不会抛[IllegalStateException]异常的DialogFragment
 */
public class SafeDialogFragment extends DialogFragment {

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (Exception e) {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this,tag);
            ft.commitAllowingStateLoss();
        }
    }

    @Override
    public void dismiss() {
        super.dismissAllowingStateLoss();
    }
}
