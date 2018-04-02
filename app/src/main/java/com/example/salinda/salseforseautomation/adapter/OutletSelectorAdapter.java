package com.example.salinda.salseforseautomation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.salinda.salseforseautomation.Fragment.OutletSelectorTabs.AllOutletsFragment;
import com.example.salinda.salseforseautomation.Fragment.OutletSelectorTabs.SuggestOutletFragment;
import com.example.salinda.salseforseautomation.Fragment.OutletSelectorTabs.TodayOutletsFragment;

public class OutletSelectorAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public OutletSelectorAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TodayOutletsFragment tab1 = new TodayOutletsFragment();
                return tab1;
            case 1:
                AllOutletsFragment tab2 = new AllOutletsFragment();
                return tab2;
            case 2:
                SuggestOutletFragment tab3 = new SuggestOutletFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
