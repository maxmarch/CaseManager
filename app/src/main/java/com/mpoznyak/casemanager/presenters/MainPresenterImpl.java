package com.mpoznyak.casemanager.presenters;

import android.content.Context;

import com.mpoznyak.casemanager.interactors.MainInteractor;
import com.mpoznyak.data.DatabaseHelper;

public class MainPresenterImpl implements MainPresenter {

    private MainInteractor mMainInteractor;
    private Context mContext;
    private DatabaseHelper mDatabaseHelper;

    public MainPresenterImpl(Context context) {
        mContext = context;
        mDatabaseHelper = DatabaseHelper.getInstance(mContext);
        mMainInteractor = new MainInteractor(mDatabaseHelper);
    }


    @Override
    public boolean typeDataisEmpty() {
        return 0 == mMainInteractor.getAllTypes().size();
    }

    @Override
    public void loadTypes() {

    }

    @Override
    public void loadCases() {

    }
}
