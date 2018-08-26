package com.mpoznyak.casemanager.presenters;

import android.content.Context;

import com.mpoznyak.casemanager.interactors.WelcomeInteractor;
import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.domain.model.Type;

public class WelcomePresenterImpl implements WelcomePresenter {

    Context mContext;
    WelcomeInteractor mWelcomeInteractor;
    DatabaseHelper mDatabaseHelper;
    Type mType;

    public WelcomePresenterImpl(Context context) {
        mContext = context;
        mDatabaseHelper = DatabaseHelper.getInstance(mContext);
        mWelcomeInteractor = new WelcomeInteractor(mDatabaseHelper);
        mType = new Type();
    }

    public void saveType(String name, String color) {
        mType.setName(name);
        mType.setColor(color);
        mType.setLastOpened(true);
        mWelcomeInteractor.addType(mType);
    }
}
