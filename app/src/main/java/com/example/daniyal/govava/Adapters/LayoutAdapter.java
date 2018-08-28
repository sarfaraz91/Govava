package com.example.daniyal.govava.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by AST on 5/2/2018.
 */

public class LayoutAdapter  extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragments;

    public LayoutAdapter(FragmentManager fm, ArrayList<Fragment> fragment) {
        super(fm);
        this.fragments = fragment;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
