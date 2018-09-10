package com.mpoznyak.casemanager.presenter;

import android.content.Context;

import com.mpoznyak.casemanager.interactor.MainInteractor;
import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.domain.model.Case;
import com.mpoznyak.domain.model.Type;

import java.util.List;

public class MainPresenter {

    private MainInteractor mMainInteractor;
    private Context mContext;
    private DatabaseHelper mDatabaseHelper;

    public MainPresenter(Context context) {
        mContext = context;
        mDatabaseHelper = DatabaseHelper.getInstance(mContext);
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

    public String loadNameForLastOpenedType() {
        return mMainInteractor.getNameLastOpenedType();
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
