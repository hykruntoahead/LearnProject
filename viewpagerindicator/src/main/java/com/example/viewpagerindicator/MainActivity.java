package com.example.viewpagerindicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private ViewPagerIndicator mViewPagerIndicator;

    private List<String>  pageNames = Arrays.asList("页面1","页面2","页面3","页面4","页面5","页面6","页面7","页面8","页面9");
    private  List<SingleFragment>  mFragments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_2);

        initView();

        for (int i=0;i<pageNames.size();i++){
            mFragments.add(SingleFragment.newInstance(pageNames.get(i)));
        }

        mViewPagerIndicator.setTitlesAndVisibleCount(pageNames,4);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });

        mViewPagerIndicator.setViewPager(mViewPager,0);

    }

    private void initView(){
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mViewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.id_vpi);

    }

}
