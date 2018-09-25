package com.mpoznyak.document_assistant.view.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mpoznyak.data.wrapper.DocumentWrapper;
import com.mpoznyak.data.wrapper.PhotoWrapper;
import com.mpoznyak.document_assistant.R;
import com.mpoznyak.document_assistant.adapter.CaseViewPagerAdapter;
import com.mpoznyak.document_assistant.presenter.CasePresenter;
import com.mpoznyak.document_assistant.util.ClickListenerOption;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CaseActivity extends AppCompatActivity {

    private static final String TAB_LAYOUT_POSITION = "tabLayoutPosition";
    private FloatingActionButton mAddFileButton;
    private ViewPager mViewPager;
    private CaseViewPagerAdapter mPagerAdapter;
    private TabLayout mTabLayout;
    private ArrayList<PhotoWrapper> mPhotoWrappers;
    private ArrayList<DocumentWrapper> mDocumentWrappers;
    private int caseId;
    private View mShareMenuDialog;
    private View mNewFilesDialog;
    private LinearLayout mAddPhotoLayout;
    private LinearLayout mTakePhotoLayout;
    private LinearLayout mAddDocumentLayout;
    private LinearLayout mShareOneFile;
    private LinearLayout mShareMultipleFilesBtn;
    private TextView mCaseName;
    private LinearLayout mShareAllFiles;
    private TextView mTextFiles, mTextSize;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private BottomSheetDialog mNewFilesBottomSheetDialog;
    private BottomSheetDialog mShareBottomSheetDialog;
    private int mViewPagerPosition;
    private CasePresenter mCasePresenter;
    private ImageButton mBackButton;
    private ImageButton mShareButton;
    private ImageButton mOkButton;
    private File photoFile;
    private Set<DocumentWrapper> mMultiSelectedDocuments;
    private Set<PhotoWrapper> mMultiSelectedPhotos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mViewPagerPosition = savedInstanceState.getInt(TAB_LAYOUT_POSITION);
        } else {
            mViewPagerPosition = 0;
        }
        caseId = Objects.requireNonNull(getIntent().getExtras()).getInt("caseId");
        mCasePresenter = new CasePresenter(this, caseId);
        setContentView(R.layout.activity_case);
        mViewPager = findViewById(R.id.viewpagerFilesCaseActivity);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mDocumentWrappers = mCasePresenter.loadDocumentsFromDb();
        mPhotoWrappers = mCasePresenter.loadPhotosFromDb();
        mPagerAdapter = new CaseViewPagerAdapter(this, fragmentManager, mDocumentWrappers, mPhotoWrappers);

        mTextFiles = findViewById(R.id.textFilesInCaseActivity);
        mTextSize = findViewById(R.id.textSizeOfCaseActivity);
        mBackButton = findViewById(R.id.buttonBackCaseActivity);
        mBackButton.setOnClickListener(v -> finish());
        mCaseName = findViewById(R.id.textNameCaseActivity);
        mPagerAdapter.setCasePresenter(mCasePresenter);
        mNewFilesBottomSheetDialog = new BottomSheetDialog(this);
        mShareBottomSheetDialog = new BottomSheetDialog(this);
        mShareMenuDialog = getLayoutInflater().inflate(R.layout.share_dialog, null);
        mNewFilesDialog = getLayoutInflater().inflate(R.layout.new_files_dialog, null);
        mNewFilesBottomSheetDialog.setContentView(mNewFilesDialog);
        mShareBottomSheetDialog.setContentView(mShareMenuDialog);

        mShareButton = findViewById(R.id.buttonShareCaseActivity);
        mOkButton = findViewById(R.id.buttonOkCaseActivity);
        mShareButton.setOnClickListener(v -> {
            if (mDocumentWrappers.size() == 0 && mPhotoWrappers.size() == 0) {
                Toast.makeText(CaseActivity.this, getString(R.string.no_data_to_share), Toast.LENGTH_SHORT).show();
            } else {
                mShareBottomSheetDialog.show();
            }
        });
        mOkButton.setOnClickListener(v -> {
            ArrayList<Uri> filesToSend = new ArrayList<>();
            Set<String> mimeTypeSet = new HashSet<>();
            for (DocumentWrapper doc : mMultiSelectedDocuments) {
                String type = null;
                String extension = MimeTypeMap.getFileExtensionFromUrl(doc.getPath().toString());
                if (extension != null) {
                    type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                }
                mimeTypeSet.add(type);
                filesToSend.add(Uri.fromFile(doc.getPath()));
            }
            for (PhotoWrapper photo : mMultiSelectedPhotos) {
                String type = null;
                String extension = MimeTypeMap.getFileExtensionFromUrl(photo.getPath().toString());
                if (extension != null) {
                    type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                }
                mimeTypeSet.add(type);
                filesToSend.add(Uri.fromFile(photo.getPath()));
            }
            Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            intent.setType("*/*");

            String[] mimeTypes = mimeTypeSet.toArray(new String[0]);
            Log.d(CaseActivity.class.getSimpleName(), mimeTypeSet.toString());
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            intent.putExtra(Intent.EXTRA_STREAM, filesToSend);
            mShareButton.setVisibility(View.VISIBLE);
            mAddFileButton.setVisibility(View.VISIBLE);
            startActivity(intent);

        });

        mAddPhotoLayout = mNewFilesDialog.findViewById(R.id.add_photo_bottom_sheet);
        mAddPhotoLayout.setOnClickListener(v -> {
            mViewPagerPosition = 1;
            mViewPager.setCurrentItem(mViewPagerPosition);
            Intent intent = new Intent(getApplicationContext(), MediaManagerActivity.class);
            intent.putExtra("caseId", caseId);
            startActivity(intent);
            mNewFilesBottomSheetDialog.cancel();
        });

        mTakePhotoLayout = mNewFilesDialog.findViewById(R.id.take_photo_bottom_sheet);
        mTakePhotoLayout.setOnClickListener(v -> {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            mViewPagerPosition = 1;
            mViewPager.setCurrentItem(mViewPagerPosition);
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            photoFile = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".jpg");
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                mNewFilesBottomSheetDialog.cancel();
            } else {
                Toast.makeText(CaseActivity.this, getString(R.string.no_camera),
                        Toast.LENGTH_SHORT).show();
            }
        });

        mAddDocumentLayout = mNewFilesDialog.findViewById(R.id.add_documents_bottom_sheet);
        mAddDocumentLayout.setOnClickListener(v -> {
            mViewPagerPosition = 0;
            mViewPager.setCurrentItem(mViewPagerPosition);
            Intent intent = new Intent(getApplicationContext(), FileManagerActivity.class);
            intent.putExtra("caseId", caseId);
            startActivity(intent);
            mNewFilesBottomSheetDialog.cancel();
        });

        mShareOneFile = mShareMenuDialog.findViewById(R.id.shareOneFile);
        mShareOneFile.setOnClickListener(v -> {

            mPagerAdapter.setSingleClickOption(ClickListenerOption.SHARE_ONE_ITEM);
            mShareBottomSheetDialog.cancel();

        });
        mShareMultipleFilesBtn = mShareMenuDialog.findViewById(R.id.shareMultipleFiles);
        mShareMultipleFilesBtn.setOnClickListener(v -> {

            mMultiSelectedDocuments = new HashSet<>();
            mMultiSelectedPhotos = new HashSet<>();
            mPagerAdapter.setMultiClickOption(ClickListenerOption.SHARE_MULTIPLE_ITEMS,
                    mMultiSelectedDocuments, mMultiSelectedPhotos);
            mOkButton.setVisibility(View.VISIBLE);
            mShareButton.setVisibility(View.GONE);
            mAddFileButton.setVisibility(View.GONE);
            mShareBottomSheetDialog.cancel();

        });
        mShareAllFiles = mShareMenuDialog.findViewById(R.id.shareAllFiles);
        mShareAllFiles.setOnClickListener(v -> {
            ArrayList<Uri> filesToSend = new ArrayList<>();
            Set<String> mimeTypeSet = new HashSet<>();
            for (DocumentWrapper doc : mDocumentWrappers) {
                String type = null;
                String extension = MimeTypeMap.getFileExtensionFromUrl(doc.getPath().toString());
                if (extension != null) {
                    type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                }
                mimeTypeSet.add(type);
                filesToSend.add(Uri.fromFile(doc.getPath()));
            }
            for (PhotoWrapper photo : mPhotoWrappers) {
                String type = null;
                String extension = MimeTypeMap.getFileExtensionFromUrl(photo.getPath().toString());
                if (extension != null) {
                    type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                }
                mimeTypeSet.add(type);
                filesToSend.add(Uri.fromFile(photo.getPath()));
            }
            Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            intent.setType("*/*");

            String[] mimeTypes = mimeTypeSet.toArray(new String[0]);
            Log.d(CaseActivity.class.getSimpleName(), mimeTypeSet.toString());
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            intent.putExtra(Intent.EXTRA_STREAM, filesToSend);
            mShareButton.setVisibility(View.VISIBLE);
            mAddFileButton.setVisibility(View.VISIBLE);
            startActivity(intent);
            mShareBottomSheetDialog.cancel();
        });

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    mPagerAdapter.unregisterForContextMenuPhotoFragment();
                    mPagerAdapter.registerForContextMenuDocumentFragment();
                } else {
                    mPagerAdapter.unregisterForContextMenuDocumentFragment();
                    mPagerAdapter.registerForContextMenuPhotoFragment();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabLayout = findViewById(R.id.tabFilesCaseActivity);
        mTabLayout.setupWithViewPager(mViewPager);

        mAddFileButton = findViewById(R.id.buttonAddFilesCaseActivity);
        mAddFileButton.setOnClickListener(v -> mNewFilesBottomSheetDialog.show());
    }

    private void clearDataIfNotExists() {
        ArrayList<DocumentWrapper> docs = mCasePresenter.loadDocumentsFromDb();
        ArrayList<PhotoWrapper> photos = mCasePresenter.loadPhotosFromDb();
        for (PhotoWrapper photoWrapper : photos) {
            if (!photoWrapper.getPath().exists()) {
                mCasePresenter.deleteEntry(photoWrapper);
            }
        }
        for (DocumentWrapper documentWrapper : docs) {
            if (!documentWrapper.getPath().exists()) {
                mCasePresenter.deleteEntry(documentWrapper);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        clearDataIfNotExists();
        String name = mCasePresenter.getCase(caseId).getName();
        if (name.length() > 16) {
            name = name.substring(0, 16) + "...";
        }
        mCaseName.setText(name);
        mDocumentWrappers.clear();
        mDocumentWrappers.addAll(mCasePresenter.loadDocumentsFromDb());
        mPhotoWrappers.clear();
        mPhotoWrappers.addAll(mCasePresenter.loadPhotosFromDb());
        mPagerAdapter.notifyDataSetChanged();
        String fileInCase = getString(R.string.files_in_case) + (mDocumentWrappers.size() + mPhotoWrappers.size());
        mTextFiles.setText(fileInCase);
        String sizeOfCase = getString(R.string.size_of_case) + getCaseSize();
        mTextSize.setText(sizeOfCase);


    }

    private String getCaseSize() {
        long size = 0;
        for (PhotoWrapper photoWrapper : mPhotoWrappers) {
            size += photoWrapper.getPath().length();
        }

        for (DocumentWrapper documentWrapper : mDocumentWrappers) {
            size += documentWrapper.getPath().length();
        }

        String sizeText = size / 1024 + " Kb";
        if (size > 600000) {
            if ((size / 1024 / 1024) < 1) {
                double sizeD = size / 1024 / 1024;
                sizeD = BigDecimal.valueOf(sizeD)
                        .setScale(2, BigDecimal.ROUND_HALF_UP)
                        .doubleValue();
                sizeText = sizeD + " Mb";
                return sizeText;
            }
            sizeText = size / 1024 / 1024 + " Mb";
        }
        if (size > 600000000) {
            sizeText = size / 1024 / 1024 / 1024 + " Gb";
        }
        return sizeText;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(TAB_LAYOUT_POSITION, mViewPagerPosition);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                mCasePresenter.savePhoto(photoFile);
                mPhotoWrappers.clear();
                mPhotoWrappers.addAll(mCasePresenter.loadPhotosFromDb());
                break;
        }
    }


}
