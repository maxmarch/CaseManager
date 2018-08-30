package com.mpoznyak.casemanager.presenter;

import android.content.Context;

import com.mpoznyak.casemanager.interactor.MainInteractor;
import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.domain.model.Case;
import com.mpoznyak.domain.model.Type;

import java.util.List;

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
    public List<Type> loadTypes() {
        return mMainInteractor.getAllTypes();
    }

    @Override
    public List<Case> loadCasesByLastOpenedType() {
        return mMainInteractor.getCasesByLastOpenedType();
    }

    @Override
    public String loadNameForLastOpenedType() {
        return mMainInteractor.getNameLastOpenedType();
    }

    @Override
    public List<Case> loadCasesBySelectedType(String type) {
        return mMainInteractor.getCasesByType(type);
    }

    @Override
    public void updateType(Type type) {
        mMainInteractor.updateType(type);
    }

}
