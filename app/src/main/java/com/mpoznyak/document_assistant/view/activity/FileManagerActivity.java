package com.mpoznyak.document_assistant.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mpoznyak.document_assistant.R;
import com.mpoznyak.document_assistant.presenter.FileManagerPresenter;

import java.util.Objects;

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
        caseId = Objects.requireNonNull(getIntent().getExtras()).getInt("caseId");
        setPresenter(new FileManagerPresenter(this, caseId));
        setContentView(R.layout.activity_file_manager);
        mRecyclerView = findViewById(R.id.fileManagerRecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this
                , linearLayoutManager.getOrientation()));
        mPresenter.initRecyclerView(mRecyclerView);
        mBackButton = findViewById(R.id.backArrowFileManager);
        mNameCurrentDir = findViewById(R.id.currentDirectory);
        mMultiSelectBtn = findViewById(R.id.multiselectBtn);
        mPresenter.setOnMultiSelectBtnListener(mMultiSelectBtn, mRecyclerView);
        mPresenter.setOnBackArrowListener(mBackButton);
        mPresenter.setTvTitle(mNameCurrentDir);
    }


}
