package com.mpoznyak.document_assistant.presenter;

import android.support.v7.app.AppCompatActivity;

import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.data.wrapper.DocumentWrapper;
import com.mpoznyak.data.wrapper.PhotoWrapper;
import com.mpoznyak.document_assistant.interactor.CaseInteractor;
import com.mpoznyak.domain.model.Case;
import com.mpoznyak.domain.model.Entry;

import java.io.File;
import java.util.ArrayList;

public class CasePresenter {

    private final AppCompatActivity mActivity;
    private final int mCaseId;
    private final CaseInteractor mCaseInteractor;
    private final DatabaseHelper mDatabaseHelper;

    public CasePresenter(AppCompatActivity activity, int caseId) {

        mActivity = activity;
        mCaseId = caseId;
        mDatabaseHelper = DatabaseHelper.getInstance(activity);
        mCaseInteractor = new CaseInteractor(mDatabaseHelper);

    }

    public Case getCase(int caseId) {
        Case aCase = new Case();
        return mCaseInteractor.getCase(caseId);
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

    public void deleteEntry(Entry photoWrapper) {
        Entry entry = new Entry();
        entry.setId(photoWrapper.getId());
        mCaseInteractor.deleteEntry(entry);
    }


}
