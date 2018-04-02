package com.example.salinda.salseforseautomation.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.adapter.BillPagerAdapter;
import com.example.salinda.salseforseautomation.model.PQModel;

import java.util.List;

public class  BillingTabViewActivity extends AppCompatActivity{
    public List<PQModel> pqModel;
    public static final String KEY_PREORDER = "Pre order";
    public static final String KEY_VANORDER = "Van Order";
    public static final String KEY_RETURN = "Return";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabview);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("   Van Order   "));
        tabLayout.addTab(tabLayout.newTab().setText("   Pre Order   "));
        tabLayout.addTab(tabLayout.newTab().setText("   Return   "));
        tabLayout.addTab(tabLayout.newTab().setText("   Bill Summery   "));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.getTabAt(0).setIcon(R.drawable.vanorder);
        tabLayout.getTabAt(1).setIcon(R.drawable.prorder);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final BillPagerAdapter adapter = new BillPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
