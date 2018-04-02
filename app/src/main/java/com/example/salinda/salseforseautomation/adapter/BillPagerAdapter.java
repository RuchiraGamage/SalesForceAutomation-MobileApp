package com.example.salinda.salseforseautomation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.salinda.salseforseautomation.Fragment.BillingProcessTabs.PreOrderFragment;
import com.example.salinda.salseforseautomation.Fragment.BillingProcessTabs.ReturnOrderFragment;
import com.example.salinda.salseforseautomation.Fragment.BillingProcessTabs.SummeryFragment;
import com.example.salinda.salseforseautomation.Fragment.BillingProcessTabs.VanOrderFragment;

public class BillPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public BillPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                VanOrderFragment tab1 = new VanOrderFragment();
                return tab1;
            case 1:
                PreOrderFragment tab2 = new PreOrderFragment();
                return tab2;
            case 2:
                ReturnOrderFragment tab3 = new ReturnOrderFragment();
                return tab3;
            case 3:
                SummeryFragment tab4 = new SummeryFragment();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
