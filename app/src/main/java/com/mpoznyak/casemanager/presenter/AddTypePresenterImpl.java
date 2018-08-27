package com.mpoznyak.casemanager.presenter;

import android.content.Context;

import com.mpoznyak.casemanager.interactor.WelcomeInteractor;
import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.domain.model.Type;

public class AddTypePresenterImpl implements AddTypePresenter {

    Context mContext;
    WelcomeInteractor mWelcomeInteractor;
    DatabaseHelper mDatabaseHelper;
    Type mType;

    public AddTypePresenterImpl(Context context) {
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
