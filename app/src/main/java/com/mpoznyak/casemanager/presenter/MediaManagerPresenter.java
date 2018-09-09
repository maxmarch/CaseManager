package com.mpoznyak.casemanager.presenter;


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
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.casemanager.adapter.MediaManagerAdapter;
import com.mpoznyak.casemanager.interactor.MediaManagerInteractor;
import com.mpoznyak.casemanager.util.MediaManagerUtil;
import com.mpoznyak.casemanager.view.fragment.SlideshowFragment;
import com.mpoznyak.data.DatabaseHelper;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MediaManagerPresenter implements LoaderManager.LoaderCallbacks<List<File>> {

    private static final String TAG = MediaManagerPresenter.class.getSimpleName();
    private boolean isBtnMultiSelectClicked = false;
    private AppCompatActivity mActivity;
    private MediaManagerAdapter mAdapter;
    private MediaManagerUtil mUtil;
    private TextView mNameDirectoryTv;
    private List<File> mFiles;
    private OnSelectListener mSelectListener;
    private AsyncTaskLoader<List<File>> mFileLoader;
    private int caseId;
    private Set<File> mFilesMultiSelect;
    private MediaManagerInteractor mInteractor;
    private DatabaseHelper mDatabaseHelper;

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

        public void onSingleSelect(File file, int position) {
            if (isExtensionAppropriate(file.getPath())) {
                SlideshowFragment fragment = new SlideshowFragment();
                fragment.setSelectedPositionUnsortedList(position);
                fragment.setPhotos(mFiles);
                fragment.setInteractor(mInteractor);
                FragmentManager manager = mActivity.getSupportFragmentManager();
                manager.beginTransaction()
                        .add(fragment, "slideshow")
                        .addToBackStack(null)
                        .commit();

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
                        v.setBackgroundColor(mActivity.getResources().getColor(R.color.colorSelectedItem));
                        mFilesMultiSelect.add(fileClicked);
                        Log.d(TAG, "Selected:   " + fileClicked.getPath());
                    } else {
                        v.setBackgroundColor(mActivity.getResources().getColor(R.color.colorBackground));
                        mFilesMultiSelect.remove(fileClicked);
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
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        mAdapter = new MediaManagerAdapter(mFiles, new MediaManagerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v) {
                setDefaultListItemClicked(recyclerView, v);
            }
        }, mActivity);
        recyclerView.setAdapter(mAdapter);
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
        if (ext.equals("jpg") || ext.equals("png"))
            return true;
        else
            return false;
    }

    public void setOnBackArrowListener(ImageButton v) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homePressed();
            }
        });
    }

    private void addDocuments(Set<File> set) {
        for (File file : set) {
            mInteractor.addPhoto(file);
            Log.d(MediaManagerPresenter.class.getSimpleName(), "Get doc " + file.getPath());
        }
    }

    public void setOnMultiSelectBtnListener(ImageButton button, RecyclerView recyclerView) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isBtnMultiSelectClicked) {
                    button.setImageResource(R.drawable.ic_baseline_send_24px);
                    mAdapter.removeListener();
                    mAdapter.setListener(new MediaManagerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View v) {
                            mSelectListener.onMultiSelect(recyclerView, v);
                        }
                    });
                    Log.d(MediaManagerPresenter.class.getSimpleName(), "checkbox is true");
                    isBtnMultiSelectClicked = true;
                } else {
                    button.setImageResource(R.drawable.ic_baseline_check_box_24px);
                    addDocuments(mFilesMultiSelect);
                    mAdapter.removeListener();
                    mAdapter.setListener(new MediaManagerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View v) {
                            setDefaultListItemClicked(recyclerView, v);
                        }
                    });
                    isBtnMultiSelectClicked = false;

                    Log.d(MediaManagerPresenter.class.getSimpleName(), "checkbox is false");
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
