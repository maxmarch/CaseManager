package com.mpoznyak.casemanager.presenter;

import android.support.v7.app.AppCompatActivity;

import com.mpoznyak.casemanager.interactor.CaseInteractor;
import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.data.wrapper.DocumentWrapper;
import com.mpoznyak.data.wrapper.PhotoWrapper;

import java.io.File;
import java.util.ArrayList;

public class CasePresenter {

    private AppCompatActivity mActivity;
    private int mCaseId;
    private CaseInteractor mCaseInteractor;
    private DatabaseHelper mDatabaseHelper;

    public CasePresenter(AppCompatActivity activity, int caseId) {

        mActivity = activity;
        mCaseId = caseId;
        mDatabaseHelper = DatabaseHelper.getInstance(activity);
        mCaseInteractor = new CaseInteractor(mDatabaseHelper);

    }

    public ArrayList<DocumentWrapper> loadDocumentsFromDb() {
        return mCaseInteractor.getDocumentsByCaseId(mCaseId);
    }

    public ArrayList<PhotoWrapper> loadPhotosFromDb() {
        return mCaseInteractor.getPhotosByCaseId(mCaseId);
    }

    public void savePhoto(File file) {
        mCaseInteractor.savePhoto(file, mCaseId);
    }


}
