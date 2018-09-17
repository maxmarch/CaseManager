package com.mpoznyak.casemanager.presenter;

import android.content.Context;

import com.mpoznyak.casemanager.interactor.NewTypeInteractor;
import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.domain.model.Type;

public class NewTypePresenterImpl implements NewTypePresenter {

    Context mContext;
    NewTypeInteractor mNewTypeInteractor;
    DatabaseHelper mDatabaseHelper;
    Type mType;

    public NewTypePresenterImpl(Context context) {
        mContext = context;
        mDatabaseHelper = DatabaseHelper.getInstance(mContext);
        mNewTypeInteractor = new NewTypeInteractor(mDatabaseHelper);
        mType = new Type();
    }

    public void saveType(String name, String color) {
        mType.setName(name);
        mType.setColor(color);
        mType.setLastOpened(true);
        mNewTypeInteractor.addType(mType);
    }

    public void updateType(Type type) {
        mNewTypeInteractor.update(type);
    }
}
