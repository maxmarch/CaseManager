package com.mpoznyak.casemanager.interactor;

import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.data.repository.CaseRepository;
import com.mpoznyak.domain.command.cases.AddCase;
import com.mpoznyak.domain.model.Case;

public class NewCaseInteractor {

    private DatabaseHelper mDatabaseHelper;
    private CaseRepository mCaseRepository;
    private AddCase mAddCase;

    public NewCaseInteractor(DatabaseHelper db) {
        mDatabaseHelper = db;
        mCaseRepository = new CaseRepository(mDatabaseHelper);
        mAddCase = new AddCase();
    }

    public void addCase(Case aCase) {
        mAddCase.execute(aCase, mCaseRepository);
    }
}
