package com.mpoznyak.casemanager.interactors;

import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.data.repository.TypeRepository;
import com.mpoznyak.domain.commands.types.AddType;
import com.mpoznyak.domain.model.Type;

public class WelcomeInteractor {

    private DatabaseHelper mDatabaseHelper;
    private TypeRepository mTypeRepository;
    private AddType mAddType;

    public WelcomeInteractor(DatabaseHelper db) {
        mDatabaseHelper = db;
        mTypeRepository = new TypeRepository(mDatabaseHelper);
        mAddType = new AddType();
    }

    public void addType(Type type) {
        mAddType.execute(type, mTypeRepository);
    }
}
