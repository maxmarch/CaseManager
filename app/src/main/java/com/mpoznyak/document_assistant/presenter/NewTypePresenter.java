package com.mpoznyak.document_assistant.presenter;

import android.content.Context;

import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.document_assistant.interactor.NewTypeInteractor;
import com.mpoznyak.domain.model.Type;

public class NewTypePresenter {

    private final Context mContext;
    private final NewTypeInteractor mNewTypeInteractor;
    private final DatabaseHelper mDatabaseHelper;
    private final Type mType;

    public NewTypePresenter(Context context) {
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
