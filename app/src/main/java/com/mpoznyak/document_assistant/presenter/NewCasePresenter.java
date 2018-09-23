package com.mpoznyak.document_assistant.presenter;

import android.content.Context;

import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.document_assistant.interactor.NewCaseInteractor;
import com.mpoznyak.domain.model.Case;

public class NewCasePresenter {

    private final DatabaseHelper mDatabaseHelper;
    private final NewCaseInteractor mNewCaseInteractor;
    private final Case mCase;

    public NewCasePresenter(Context context) {
        mDatabaseHelper = DatabaseHelper.getInstance(context);
        mNewCaseInteractor = new NewCaseInteractor(mDatabaseHelper);
        mCase = new Case();
    }


    public void saveCase(String name, String type) {
        mCase.setName(name);
        mCase.setType(type);
        mNewCaseInteractor.addCase(mCase);

    }
}
