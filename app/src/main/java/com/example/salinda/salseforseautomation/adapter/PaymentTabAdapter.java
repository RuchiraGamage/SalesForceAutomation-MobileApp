package com.example.salinda.salseforseautomation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.salinda.salseforseautomation.Fragment.PaymentTabs.PreOrderFragment;
import com.example.salinda.salseforseautomation.Fragment.PaymentTabs.VanOrderFragment;

public class PaymentTabAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PaymentTabAdapter(FragmentManager fm, int NumOfTabs) {
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
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
