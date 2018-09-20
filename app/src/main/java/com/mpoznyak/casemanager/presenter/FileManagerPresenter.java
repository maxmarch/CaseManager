package com.mpoznyak.casemanager.presenter;


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

import com.mpoznyak.casemanager.R;
import com.mpoznyak.casemanager.adapter.FileManagerAdapter;
import com.mpoznyak.casemanager.interactor.FileManagerInteractor;
import com.mpoznyak.casemanager.util.FileManagerUtil;
import com.mpoznyak.data.DatabaseHelper;

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
    private AppCompatActivity mActivity;
    private FileManagerAdapter mAdapter;
    private FileManagerUtil mUtil;
    private TextView mNameDirectoryTv;
    private List<File> mFiles;
    private OnSelectListener mSelectListener;
    private ImageButton mMultipleFilesBtn;
    private AsyncTaskLoader<List<File>> mFileLoader;
    private RecyclerView mRecyclerView;
    private int caseId;
    private Set<File> mFilesMultiSelect;
    private FileManagerInteractor mInteractor;
    private DatabaseHelper mDatabaseHelper;

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

        public void onSingleSelect(File file) {
            if (isExtensionAppropriate(file.getPath())) {
                mInteractor.addDocument(file);
                mActivity.finish();
            } else {
                Toast.makeText(mActivity, "This file type is not supported", Toast.LENGTH_SHORT).show();
            }
        }

        public void onMultiSelect(RecyclerView recyclerView, View v) {
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
                    Toast.makeText(mActivity, "This file type is not supported", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mActivity, "You can't add directory to documents group", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new FileManagerAdapter(mActivity, mFiles, new FileManagerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v) {
                setDefaultListItemClicked(recyclerView, v);
            }
        });
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
        if (ext.equals("doc") || ext.equals("pdf")
                || ext.equals("txt")
                || ext.equals("xls")
                || ext.equals("docx"))
            return true;
        else
            return false;
    }

    public void setOnBackArrowListener(ImageButton v) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.refreshSparseBooleanArray();
                mAdapter.notifyDataSetChanged();
                homePressed();
            }
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isBtnMultiSelectClicked) {

                    button.setImageResource(R.drawable.ic_baseline_send_24px);
                    mAdapter.removeListener();
                    mAdapter.setListener(new FileManagerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View v) {
                            mSelectListener.onMultiSelect(recyclerView, v);
                        }
                    });
                    Log.d(FileManagerPresenter.class.getSimpleName(), "checkbox is true");
                    isBtnMultiSelectClicked = true;
                } else {
                    button.setImageResource(R.drawable.ic_baseline_check_box_24px);
                    addDocuments(mFilesMultiSelect);
                    mAdapter.removeListener();
                    mAdapter.setListener(new FileManagerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View v) {
                            setDefaultListItemClicked(recyclerView, v);
                        }
                    });
                    isBtnMultiSelectClicked = false;

                    Log.d(FileManagerPresenter.class.getSimpleName(), "checkbox is false");
                    if (mFilesMultiSelect.size() != 0) {
                        mFilesMultiSelect.clear();
                        mActivity.finish();
                    }


                }

            }
        });

    }

    public void setTvTitle(TextView tv) {
        mNameDirectoryTv = tv;
    }

    private void enableSelectionMode() {

    }

    public void listItemSelected(RecyclerView recyclerView, View view) {

    }


    private void homePressed() {
        if (mUtil.hasPreviousDir()) {
            mUtil.setCurrentDir(mUtil.getPreviousDir());
            mNameDirectoryTv.setText(mUtil.getCurrentDir().getName());
            mMultipleFilesBtn.setImageResource(R.drawable.ic_baseline_check_box_24px);
            addDocuments(mFilesMultiSelect);
            mAdapter.removeListener();
            mAdapter.setListener(new FileManagerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v) {
                    setDefaultListItemClicked(mRecyclerView, v);
                }
            });
            isBtnMultiSelectClicked = false;

            mFileLoader.onContentChanged();
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
