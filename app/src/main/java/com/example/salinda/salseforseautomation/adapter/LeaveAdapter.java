package com.example.salinda.salseforseautomation.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.salinda.salseforseautomation.Fragment.LeaveTab.AddLeaves;
import com.example.salinda.salseforseautomation.Fragment.LeaveTab.MyLeaves;

public class LeaveAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public LeaveAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AddLeaves tab1 = new AddLeaves();
                return tab1;
            case 1:
                MyLeaves tab2 = new MyLeaves();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
