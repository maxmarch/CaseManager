package com.mpoznyak.document_assistant.presenter;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.document_assistant.R;
import com.mpoznyak.document_assistant.adapter.FileManagerAdapter;
import com.mpoznyak.document_assistant.interactor.FileManagerInteractor;
import com.mpoznyak.document_assistant.util.FileManagerUtil;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileManagerPresenter implements LoaderManager.LoaderCallbacks<List<File>> {

    private SparseBooleanArray mArray;
    private static final String TAG = FileManagerPresenter.class.getSimpleName();
    private boolean isBtnMultiSelectClicked = false;
    private final AppCompatActivity mActivity;
    private FileManagerAdapter mAdapter;
    private final FileManagerUtil mUtil;
    private TextView mNameDirectoryTv;
    private final List<File> mFiles;
    private final OnSelectListener mSelectListener;
    private ImageButton mMultipleFilesBtn;
    private AsyncTaskLoader<List<File>> mFileLoader;
    private RecyclerView mRecyclerView;
    private final int caseId;
    private final Set<File> mFilesMultiSelect;
    private final FileManagerInteractor mInteractor;
    private final DatabaseHelper mDatabaseHelper;

    public FileManagerPresenter(AppCompatActivity activity, int caseId) {
        mSelectListener = new OnSelectListener();
        mActivity = activity;
        mDatabaseHelper = DatabaseHelper.getInstance(activity);
        this.caseId = caseId;
        mInteractor = new FileManagerInteractor(mDatabaseHelper, caseId);
        mUtil = new FileManagerUtil();
        mFiles = new ArrayList<>();
        mFilesMultiSelect = new HashSet<>();
        mActivity.getSupportLoaderManager().initLoader(0, null, this);
        mFileLoader.forceLoad();
    }


    private class OnSelectListener {

        void onSingleSelect(File file) {
            if (isExtensionAppropriate(file.getPath())) {
                mInteractor.addDocument(file);
                mActivity.finish();
            } else {
                Toast.makeText(mActivity, mActivity.getString(R.string.not_supported_type), Toast.LENGTH_SHORT).show();
            }
        }

        void onMultiSelect(RecyclerView recyclerView, View v) {
            int position = recyclerView.getChildLayoutPosition(v);
            File fileClicked = mFiles.get(position);

            if (!fileClicked.isDirectory()) {
                if (isExtensionAppropriate(fileClicked.getPath())) {
                    if (!mFilesMultiSelect.contains(fileClicked)) {
                        mFilesMultiSelect.add(fileClicked);
                        mArray.put(position, true);
                        mAdapter.notifyDataSetChanged();
                        Log.d(TAG, "Selected:   " + fileClicked.getPath());
                    } else {
                        mFilesMultiSelect.remove(fileClicked);
                        mArray.put(position, false);
                        mAdapter.notifyDataSetChanged();
                        Log.d(TAG, "Unselected:   " + fileClicked.getPath());
                    }
                } else {
                    Toast.makeText(mActivity, mActivity.getString(R.string.not_supported_type), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mActivity, mActivity.getString(R.string.not_supported_adding_folder), Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new FileManagerAdapter(mActivity, mFiles, v -> setDefaultListItemClicked(recyclerView, v));
        recyclerView.setAdapter(mAdapter);
        mArray = mAdapter.getSparseBooleanArray();

    }

    private void setDefaultListItemClicked(RecyclerView recyclerView, View v) {
        int position = recyclerView.getChildLayoutPosition(v);
        File fileClicked = mFiles.get(position);

        if (fileClicked.isDirectory()) {
            mUtil.setPreviousDir(mUtil.getCurrentDir());

            mUtil.setCurrentDir(fileClicked);
            mFileLoader.onContentChanged();

        } else {
            mSelectListener.onSingleSelect(fileClicked);
        }
    }

    private void updateAdapter(List<File> files) {
        mFiles.clear();
        mFiles.addAll(files);
        mAdapter.notifyDataSetChanged();
        mNameDirectoryTv.setText(mUtil.getCurrentDir().getName());

    }

    private boolean isExtensionAppropriate(String path) {
        String ext = FilenameUtils.getExtension(path);
        return ext.equals("doc") || ext.equals("pdf")
                || ext.equals("txt")
                || ext.equals("xls")
                || ext.equals("docx");
    }

    public void setOnBackArrowListener(ImageButton v) {
        v.setOnClickListener(v1 -> {
            mAdapter.refreshSparseBooleanArray();
            mAdapter.notifyDataSetChanged();
            homePressed();
        });
    }

    private void addDocuments(Set<File> set) {
        for (File file : set) {
            mInteractor.addDocument(file);
            Log.d(FileManagerPresenter.class.getSimpleName(), "Get doc " + file.getPath());
        }
    }

    public void setOnMultiSelectBtnListener(ImageButton button, RecyclerView recyclerView) {
        mMultipleFilesBtn = button;
        mRecyclerView = recyclerView;
        button.setOnClickListener(v -> {
            if (!isBtnMultiSelectClicked) {

                button.setImageResource(R.drawable.ic_baseline_send_24px);
                mAdapter.removeListener();
                mAdapter.setListener(v12 -> mSelectListener.onMultiSelect(recyclerView, v12));
                Toast.makeText(mActivity, mActivity.getString(R.string.select_documents_ext), Toast.LENGTH_SHORT).show();
                Log.d(FileManagerPresenter.class.getSimpleName(), "checkbox is true");
                isBtnMultiSelectClicked = true;
            } else {
                button.setImageResource(R.drawable.ic_baseline_check_box_24px);
                addDocuments(mFilesMultiSelect);
                mAdapter.removeListener();
                mAdapter.setListener(v1 -> setDefaultListItemClicked(recyclerView, v1));
                isBtnMultiSelectClicked = false;

                Log.d(FileManagerPresenter.class.getSimpleName(), "checkbox is false");
                if (mFilesMultiSelect.size() != 0) {
                    mFilesMultiSelect.clear();
                    mActivity.finish();
                }


            }

        });

    }

    public void setTvTitle(TextView tv) {
        mNameDirectoryTv = tv;
    }


    private void homePressed() {
        if (mUtil.hasPreviousDir()) {
            mUtil.setCurrentDir(mUtil.getPreviousDir());
            mNameDirectoryTv.setText(mUtil.getCurrentDir().getName());
            mMultipleFilesBtn.setImageResource(R.drawable.ic_baseline_check_box_24px);
            addDocuments(mFilesMultiSelect);
            mAdapter.removeListener();
            mAdapter.setListener(v -> setDefaultListItemClicked(mRecyclerView, v));
            isBtnMultiSelectClicked = false;

            mFileLoader.onContentChanged();
        } else {
            mActivity.finish();
        }
    }

    @NonNull
    @Override
    public Loader<List<File>> onCreateLoader(int id, @Nullable Bundle args) {
        mFileLoader = new AsyncTaskLoader<List<File>>(mActivity) {
            @Nullable
            @Override
            public List<File> loadInBackground() {
                Log.d(FileManagerPresenter.class.getSimpleName(), mUtil.getAllFiles(mUtil.getCurrentDir()).toString());
                return mUtil.getAllFiles(mUtil.getCurrentDir());
            }
        };
        return mFileLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<File>> loader, List<File> data) {

        updateAdapter(data);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<File>> loader) {

    }
}
