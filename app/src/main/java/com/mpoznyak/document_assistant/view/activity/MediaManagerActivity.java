package com.mpoznyak.document_assistant.view.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mpoznyak.document_assistant.R;
import com.mpoznyak.document_assistant.presenter.MediaManagerPresenter;

import java.util.Objects;

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
        caseId = Objects.requireNonNull(getIntent().getExtras()).getInt("caseId");
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


