package com.mpoznyak.casemanager.view.activity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.casemanager.adapter.CaseViewPagerAdapter;
import com.mpoznyak.casemanager.presenter.CasePresenter;
import com.mpoznyak.data.wrapper.DocumentWrapper;
import com.mpoznyak.data.wrapper.PhotoWrapper;

import java.util.ArrayList;

public class CaseActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1234;
    private static final int REQUEST_FILE_MANAGER_ACTIVITY = 1;
    private FloatingActionButton mBtnOpenFileManager;
    private ViewPager mViewPager;
    private CaseViewPagerAdapter mPagerAdapter;
    private TabLayout mTabLayout;
    private ArrayList<PhotoWrapper> mPhotoWrappers;
    private ArrayList<DocumentWrapper> mDocumentWrappers;
    private int caseId;
    private CasePresenter mCasePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        caseId = getIntent().getExtras().getInt("caseId");
        mCasePresenter = new CasePresenter(this, caseId);
        setContentView(R.layout.activity_case);
        mViewPager = findViewById(R.id.caseViewPager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mDocumentWrappers = mCasePresenter.loadDocumentsFromDb();
        mPhotoWrappers = mCasePresenter.loadPhotosFromDb();
        mPagerAdapter = new CaseViewPagerAdapter(this, fragmentManager, mDocumentWrappers, mPhotoWrappers);

        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout = findViewById(R.id.tablayout);
        mTabLayout.setupWithViewPager(mViewPager);

        mBtnOpenFileManager = findViewById(R.id.openFileManager);
        mBtnOpenFileManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MediaManagerActivity.class);
                intent.putExtra("caseId", caseId);
                verifyPermissions();
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mDocumentWrappers.clear();
        mDocumentWrappers.addAll(mCasePresenter.loadDocumentsFromDb());
        mPhotoWrappers.clear();
        mPhotoWrappers.addAll(mCasePresenter.loadPhotosFromDb());
        mPagerAdapter.notifyDataSetChanged();
    }

    private void verifyPermissions() {
        String[] permissions = {
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    permissions,
                    REQUEST_CODE
            );
        }
    }


}
