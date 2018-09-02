package com.mpoznyak.casemanager.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.casemanager.adapter.CasePagerAdapter;

import java.util.ArrayList;

public class CaseActivity extends AppCompatActivity {

    private FloatingActionButton mBtnOpenFileManager;
    private ViewPager mViewPager;
    private CasePagerAdapter mPagerAdapter;
    private TabLayout mTabLayout;
    private int caseId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case);
        caseId = getIntent().getExtras().getInt("caseId");
        mViewPager = findViewById(R.id.caseViewPager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mPagerAdapter = new CasePagerAdapter(this, fragmentManager, new ArrayList<>(), new ArrayList<>());
        mViewPager.setAdapter(mPagerAdapter);

        mTabLayout = findViewById(R.id.tablayout);
        mTabLayout.setupWithViewPager(mViewPager);

        mBtnOpenFileManager = findViewById(R.id.openFileManager);
        mBtnOpenFileManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FileManagerActivity.class);
                intent.putExtra("caseId", caseId);
                startActivity(intent);
            }
        });

    }


}
