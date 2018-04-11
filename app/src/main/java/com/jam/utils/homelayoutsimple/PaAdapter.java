package com.jam.utils.homelayoutsimple;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by hejiaming on 2018/3/24.
 *
 * @desciption:
 */

public class PaAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragments;


    public PaAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
