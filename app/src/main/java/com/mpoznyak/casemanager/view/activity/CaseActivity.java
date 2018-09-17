package com.mpoznyak.casemanager.view.activity;


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
import android.widget.Toast;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.casemanager.adapter.CaseViewPagerAdapter;
import com.mpoznyak.casemanager.presenter.CasePresenter;
import com.mpoznyak.casemanager.util.ClickListenerOption;
import com.mpoznyak.data.wrapper.DocumentWrapper;
import com.mpoznyak.data.wrapper.PhotoWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CaseActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1234;
    private static final int REQUEST_FILE_MANAGER_ACTIVITY = 10;
    private static final int REQUEST_TAKE_PHOTO = 11;
    private static final int REQUEST_SEND_FILES = 12;
    private static final int RESULT_OK = 1;
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
    private LinearLayout mShareZipFile;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private BottomSheetDialog mNewFilesBottomSheetDialog;
    private BottomSheetDialog mShareBottomSheetDialog;
    private int mViewPagerPosition;
    private CasePresenter mCasePresenter;
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
        caseId = getIntent().getExtras().getInt("caseId");
        mCasePresenter = new CasePresenter(this, caseId);
        setContentView(R.layout.activity_case);
        mViewPager = findViewById(R.id.caseViewPager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mDocumentWrappers = mCasePresenter.loadDocumentsFromDb();
        mPhotoWrappers = mCasePresenter.loadPhotosFromDb();
        mPagerAdapter = new CaseViewPagerAdapter(this, fragmentManager, mDocumentWrappers, mPhotoWrappers);

        mPagerAdapter.setCasePresenter(mCasePresenter);
        mNewFilesBottomSheetDialog = new BottomSheetDialog(this);
        mShareBottomSheetDialog = new BottomSheetDialog(this);
        mShareMenuDialog = getLayoutInflater().inflate(R.layout.share_dialog, null);
        mNewFilesDialog = getLayoutInflater().inflate(R.layout.new_files_dialog, null);
        mNewFilesBottomSheetDialog.setContentView(mNewFilesDialog);
        mShareBottomSheetDialog.setContentView(mShareMenuDialog);

        mShareButton = findViewById(R.id.shareButton);
        mOkButton = findViewById(R.id.okButton);
        mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDocumentWrappers.size() == 0 && mPhotoWrappers.size() == 0) {
                    Toast.makeText(CaseActivity.this, "There is no data for share!", Toast.LENGTH_SHORT).show();
                } else {
                    mShareBottomSheetDialog.show();
                }
            }
        });
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                String[] mimetypes = (String[]) mimeTypeSet.toArray(new String[0]);
                Log.d(CaseActivity.class.getSimpleName(), mimeTypeSet.toString());
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
                intent.putExtra(Intent.EXTRA_STREAM, filesToSend);
                mShareButton.setVisibility(View.VISIBLE);
                mAddFileButton.setVisibility(View.VISIBLE);
                startActivity(intent);

            }
        });

        mAddPhotoLayout = mNewFilesDialog.findViewById(R.id.add_photo_bottom_sheet);
        mAddPhotoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPagerPosition = 1;
                mViewPager.setCurrentItem(mViewPagerPosition);
                Intent intent = new Intent(getApplicationContext(), MediaManagerActivity.class);
                intent.putExtra("caseId", caseId);
                startActivity(intent);
                mNewFilesBottomSheetDialog.cancel();
            }
        });

        mTakePhotoLayout = mNewFilesDialog.findViewById(R.id.take_photo_bottom_sheet);
        mTakePhotoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    Toast.makeText(CaseActivity.this, "There is no camera application on device",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        mAddDocumentLayout = mNewFilesDialog.findViewById(R.id.add_documents_bottom_sheet);
        mAddDocumentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPagerPosition = 0;
                mViewPager.setCurrentItem(mViewPagerPosition);
                Intent intent = new Intent(getApplicationContext(), FileManagerActivity.class);
                intent.putExtra("caseId", caseId);
                startActivity(intent);
                mNewFilesBottomSheetDialog.cancel();
            }
        });

        mShareOneFile = mShareMenuDialog.findViewById(R.id.shareOneFile);
        mShareOneFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPagerAdapter.setSingleClickOption(ClickListenerOption.SHARE_ONE_ITEM);
                mShareBottomSheetDialog.cancel();

            }
        });
        mShareMultipleFilesBtn = mShareMenuDialog.findViewById(R.id.shareMultipleFiles);
        mShareMultipleFilesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMultiSelectedDocuments = new HashSet<>();
                mMultiSelectedPhotos = new HashSet<>();
                mPagerAdapter.setMultiClickOption(ClickListenerOption.SHARE_MULTIPLE_ITEMS,
                        mMultiSelectedDocuments, mMultiSelectedPhotos);
                mOkButton.setVisibility(View.VISIBLE);
                mShareButton.setVisibility(View.GONE);
                mAddFileButton.setVisibility(View.GONE);
                mShareBottomSheetDialog.cancel();

            }
        });
        mShareZipFile = mShareMenuDialog.findViewById(R.id.shareZipFile);

        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout = findViewById(R.id.tablayout);
        mTabLayout.setupWithViewPager(mViewPager);

        mAddFileButton = findViewById(R.id.openFileManager);
        mAddFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewFilesBottomSheetDialog.show();

            }
        });
    }

    private void checkFilesExistence() {
        ArrayList<DocumentWrapper> docs = mCasePresenter.loadDocumentsFromDb();
        ArrayList<PhotoWrapper> photos = mCasePresenter.loadPhotosFromDb();
        for (PhotoWrapper photo : photos) {
            if (!photo.getPath().exists()) {

            }
        }

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
