package com.mpoznyak.casemanager.view.activity;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.casemanager.adapter.CasePagerAdapter;

import java.util.ArrayList;

public class CaseActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private CasePagerAdapter mPagerAdapter;
    private TabLayout mTabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case);
        mViewPager = findViewById(R.id.caseViewPager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mPagerAdapter = new CasePagerAdapter(this, fragmentManager, new ArrayList<>(), new ArrayList<>());
        mViewPager.setAdapter(mPagerAdapter);

        mTabLayout = findViewById(R.id.tablayout);
        mTabLayout.setupWithViewPager(mViewPager);

    }
}
