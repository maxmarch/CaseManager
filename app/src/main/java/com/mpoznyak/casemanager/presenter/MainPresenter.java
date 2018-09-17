package com.mpoznyak.casemanager.presenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mpoznyak.casemanager.interactor.MainInteractor;
import com.mpoznyak.casemanager.view.activity.NewTypeActivity;
import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.domain.model.Case;
import com.mpoznyak.domain.model.Type;

import java.util.List;

public class MainPresenter {

    private static final String TAG = MainPresenter.class.getSimpleName();
    private MainInteractor mMainInteractor;
    private AppCompatActivity mActivity;
    private DatabaseHelper mDatabaseHelper;

    public MainPresenter(AppCompatActivity appCompatActivity) {
        mActivity = appCompatActivity;
        mDatabaseHelper = DatabaseHelper.getInstance(mActivity);
        mMainInteractor = new MainInteractor(mDatabaseHelper);
    }


    public boolean typeDataisEmpty() {
        return 0 == mMainInteractor.getAllTypes().size();
    }

    public List<Type> loadTypes() {
        return mMainInteractor.getAllTypes();
    }

    public List<Case> loadCasesByLastOpenedType() {
        return mMainInteractor.getCasesByLastOpenedType();
    }

    public Type loadLastOpenedType() {
        try {
            return mMainInteractor.getLastOpenedType();

        } catch (IndexOutOfBoundsException e) {
            Log.e(TAG, e.getMessage());
            if (e.getMessage().contains("Size: 0")) {
                Intent intent = new Intent(mActivity, NewTypeActivity.class);
                mActivity.startActivity(intent);
            }
        }
        return null;
    }

    public List<Case> loadCasesBySelectedType(String type) {
        return mMainInteractor.getCasesByType(type);
    }

    public void updateType(Type type) {
        mMainInteractor.updateType(type);
    }

    public void deleteCase(Case aCase) {
        mMainInteractor.deleteCase(aCase);
    }

    public void deleteType(Type type) {
        mMainInteractor.deleteType(type);
    }

}
