package com.kunye.zhihu.ui.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.kunye.zhihu.R;


/**
 * Created by kunye on 2017/1/22.
 */

public class FragmentFactory {
    public static void createFtagment(FragmentManager manager, BaseFragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fl, fragment);
        transaction.commit();
    }
}