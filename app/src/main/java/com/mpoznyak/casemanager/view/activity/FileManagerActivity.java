package com.mpoznyak.casemanager.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.casemanager.presenter.FileManagerPresenter;

public class FileManagerActivity extends AppCompatActivity {

    private FileManagerPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private ImageButton mBackButton;
    private TextView mNameCurrentDir;
    private ImageButton mMultiSelectBtn;
    private int caseId;


    private void setPresenter(FileManagerPresenter presenter) {
        mPresenter = presenter;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        caseId = getIntent().getExtras().getInt("caseId");
        setPresenter(new FileManagerPresenter(this, caseId));
        setContentView(R.layout.activity_file_manager);
        mRecyclerView = findViewById(R.id.fileManagerRecyclerview);
        mPresenter.initRecyclerView(mRecyclerView);
        mBackButton = findViewById(R.id.backArrowFileManager);
        mNameCurrentDir = findViewById(R.id.currentDirectory);
        mMultiSelectBtn = findViewById(R.id.multiselectBtn);
        mPresenter.setOnMultiSelectBtnListener(mMultiSelectBtn, mRecyclerView);
        mPresenter.setOnBackArrowListener(mBackButton);
        mPresenter.setTvTitle(mNameCurrentDir);
    }


}
