package com.mpoznyak.document_assistant.presenter;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.document_assistant.R;
import com.mpoznyak.document_assistant.adapter.MediaManagerAdapter;
import com.mpoznyak.document_assistant.interactor.MediaManagerInteractor;
import com.mpoznyak.document_assistant.util.MediaManagerUtil;
import com.mpoznyak.document_assistant.view.fragment.SlideshowFragment;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MediaManagerPresenter implements LoaderManager.LoaderCallbacks<List<File>> {

    private ImageButton mSelectButton;
    private RecyclerView mRecyclerView;
    private SparseBooleanArray mArray;
    private static final String TAG = MediaManagerPresenter.class.getSimpleName();
    private boolean isBtnMultiSelectClicked = false;
    private final AppCompatActivity mActivity;
    private MediaManagerAdapter mAdapter;
    private final MediaManagerUtil mUtil;
    private TextView mNameDirectoryTv;
    private final List<File> mFiles;
    private final OnSelectListener mSelectListener;
    private AsyncTaskLoader<List<File>> mFileLoader;
    private final int caseId;
    private final Set<File> mFilesMultiSelect;
    private final MediaManagerInteractor mInteractor;
    private final DatabaseHelper mDatabaseHelper;

    public MediaManagerPresenter(AppCompatActivity activity, int caseId) {
        mSelectListener = new OnSelectListener();
        mActivity = activity;
        mDatabaseHelper = DatabaseHelper.getInstance(activity);
        this.caseId = caseId;
        mInteractor = new MediaManagerInteractor(mDatabaseHelper, caseId);
        mUtil = new MediaManagerUtil();
        mFiles = new ArrayList<>();
        mFilesMultiSelect = new HashSet<>();
        mActivity.getSupportLoaderManager().initLoader(0, null, this);
        mFileLoader.forceLoad();
    }


    private class OnSelectListener {

        void onSingleSelect(File file, int position) {
            if (isExtensionAppropriate(file.getPath())) {
                SlideshowFragment fragment = SlideshowFragment.newInstance(true);
                fragment.setSelectedPositionUnsortedList(position);
                fragment.setPhotos(mFiles);
                fragment.setInteractor(mInteractor);
                FragmentManager manager = mActivity.getSupportFragmentManager();
                manager.beginTransaction()
                        .add(fragment, "slideshow")
                        .addToBackStack(null)
                        .commit();

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
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        mAdapter = new MediaManagerAdapter(mFiles, v -> setDefaultListItemClicked(recyclerView, v), mActivity);
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
            mSelectListener.onSingleSelect(fileClicked, position);
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
        return ext.equals("jpg") || ext.equals("png");
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
            mInteractor.addPhoto(file);
            Log.d(MediaManagerPresenter.class.getSimpleName(), "Get doc " + file.getPath());
        }
    }

    public void setOnMultiSelectBtnListener(ImageButton button, RecyclerView recyclerView) {
        mSelectButton = button;
        mRecyclerView = recyclerView;
        button.setOnClickListener(v -> {
            if (!isBtnMultiSelectClicked) {
                button.setImageResource(R.drawable.ic_baseline_send_24px);
                mAdapter.removeListener();
                mAdapter.setListener(v12 -> mSelectListener.onMultiSelect(recyclerView, v12));
                Log.d(MediaManagerPresenter.class.getSimpleName(), "checkbox is true");
                isBtnMultiSelectClicked = true;
            } else {
                button.setImageResource(R.drawable.ic_baseline_check_box_24px);
                addDocuments(mFilesMultiSelect);
                mAdapter.removeListener();
                mAdapter.setListener(v1 -> setDefaultListItemClicked(recyclerView, v1));
                isBtnMultiSelectClicked = false;

                Log.d(MediaManagerPresenter.class.getSimpleName(), "checkbox is false");
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

            mSelectButton.setImageResource(R.drawable.ic_baseline_check_box_24px);
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
                Log.d(MediaManagerPresenter.class.getSimpleName(), mUtil.getAllFiles(mUtil.getCurrentDir()).toString());
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
