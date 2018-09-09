package com.mpoznyak.casemanager.view.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.casemanager.presenter.MediaManagerPresenter;

public class MediaManagerActivity extends AppCompatActivity {


    private MediaManagerPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private ImageButton mBackButton;
    private TextView mNameCurrentDir;
    private ImageButton mMultiSelectBtn;
    private int caseId;


    private void setPresenter(MediaManagerPresenter presenter) {
        mPresenter = presenter;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        caseId = getIntent().getExtras().getInt("caseId");
        setPresenter(new MediaManagerPresenter(this, caseId));
        setContentView(R.layout.activity_media_manager);
        mRecyclerView = findViewById(R.id.mediaManagerRecyclerView);
        mPresenter.initRecyclerView(mRecyclerView);
        mBackButton = findViewById(R.id.backArrowMediaManager);
        mNameCurrentDir = findViewById(R.id.currentDirectoryMediaManager);
        mMultiSelectBtn = findViewById(R.id.multiselectBtnMediaManager);
        mPresenter.setOnMultiSelectBtnListener(mMultiSelectBtn, mRecyclerView);
        mPresenter.setOnBackArrowListener(mBackButton);
        mPresenter.setTvTitle(mNameCurrentDir);
    }


}


