package com.mpoznyak.casemanager.view.activity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.casemanager.adapter.CaseViewPagerAdapter;
import com.mpoznyak.casemanager.presenter.CasePresenter;
import com.mpoznyak.data.wrapper.DocumentWrapper;
import com.mpoznyak.data.wrapper.PhotoWrapper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CaseActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1234;
    private static final int REQUEST_FILE_MANAGER_ACTIVITY = 10;
    private static final int REQUEST_TAKE_PHOTO = 11;
    private static final int RESULT_OK = 1;
    private static final String TAB_LAYOUT_POSITION = "tabLayoutPosition";
    private FloatingActionButton mAddMenu;
    private ViewPager mViewPager;
    private CaseViewPagerAdapter mPagerAdapter;
    private TabLayout mTabLayout;
    private ArrayList<PhotoWrapper> mPhotoWrappers;
    private ArrayList<DocumentWrapper> mDocumentWrappers;
    private int caseId;
    private View sheetDialog;
    private LinearLayout mAddPhoto;
    private LinearLayout mTakePhoto;
    private LinearLayout mAddDocument;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private BottomSheetDialog mBottomSheetDialog;
    private int mViewPagerPosition;
    private CasePresenter mCasePresenter;

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

        mBottomSheetDialog = new BottomSheetDialog(this);
        sheetDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);
        mBottomSheetDialog.setContentView(sheetDialog);

        mAddPhoto = sheetDialog.findViewById(R.id.add_photo_bottom_sheet);
        mAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPagerPosition = 1;
                mViewPager.setCurrentItem(mViewPagerPosition);
                Intent intent = new Intent(getApplicationContext(), MediaManagerActivity.class);
                intent.putExtra("caseId", caseId);
                verifyPermissions();
                startActivity(intent);
                mBottomSheetDialog.cancel();
            }
        });
        mTakePhoto = sheetDialog.findViewById(R.id.take_photo_bottom_sheet);
        mTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPagerPosition = 1;
                mViewPager.setCurrentItem(mViewPagerPosition);
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    mBottomSheetDialog.cancel();
                }
            }
        });
        mAddDocument = sheetDialog.findViewById(R.id.add_documents_bottom_sheet);
        mAddDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPagerPosition = 0;
                mViewPager.setCurrentItem(mViewPagerPosition);
                Intent intent = new Intent(getApplicationContext(), FileManagerActivity.class);
                intent.putExtra("caseId", caseId);
                verifyPermissions();
                startActivity(intent);
                mBottomSheetDialog.cancel();
            }
        });

        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout = findViewById(R.id.tablayout);
        mTabLayout.setupWithViewPager(mViewPager);

        mAddMenu = findViewById(R.id.openFileManager);
        mAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.show();

            }
        });
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
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                    mCasePresenter.savePhoto(destination);
                    mPhotoWrappers.clear();
                    mPhotoWrappers.addAll(mCasePresenter.loadPhotosFromDb());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    private void verifyPermissions() {
        String[] permissions = {
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    permissions,
                    REQUEST_CODE
            );
        }
    }


}
