package com.mpoznyak.casemanager.presenter;

import android.content.Context;

import com.mpoznyak.casemanager.interactor.NewCaseInteractor;
import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.domain.model.Case;

public class NewCasePresenterImpl implements NewCasePresenter {

    private DatabaseHelper mDatabaseHelper;
    private NewCaseInteractor mNewCaseInteractor;
    private Case mCase;

    public NewCasePresenterImpl(Context context) {
        mDatabaseHelper = DatabaseHelper.getInstance(context);
        mNewCaseInteractor = new NewCaseInteractor(mDatabaseHelper);
        mCase = new Case();
    }


    @Override
    public void saveCase(String name, String type) {
        mCase.setName(name);
        mCase.setType(type);
        mNewCaseInteractor.addCase(mCase);

    }
}
