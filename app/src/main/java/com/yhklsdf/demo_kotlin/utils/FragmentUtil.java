package com.yhklsdf.demo_kotlin.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.yhklsdf.demo_kotlin.R;

public class FragmentUtil {
    public static void replaceFragment(FragmentManager manager,Fragment fragment) {
        if (!fragment.isAdded()) {
            manager.beginTransaction().replace(R.id.fl_main_home_container, fragment).commitAllowingStateLoss();
        }
    }
}
