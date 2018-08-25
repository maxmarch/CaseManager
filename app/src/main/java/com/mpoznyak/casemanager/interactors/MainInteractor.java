package com.mpoznyak.casemanager.interactors;

import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.data.repository.CaseRepository;
import com.mpoznyak.data.repository.TypeRepository;
import com.mpoznyak.data.specification.cases.CasesByTypeSpecification;
import com.mpoznyak.data.specification.types.AllTypesSpecification;
import com.mpoznyak.data.specification.types.LastOpenedTypeSpecification;
import com.mpoznyak.domain.commands.cases.GetCases;
import com.mpoznyak.domain.commands.types.GetTypes;
import com.mpoznyak.domain.model.Case;
import com.mpoznyak.domain.model.Type;

import java.util.List;

public class MainInteractor {

    private DatabaseHelper mDatabaseHelper;
    private GetCases mGetCases;
    private GetTypes mGetTypes;
    private CaseRepository mCaseRepository;
    private TypeRepository mTypeRepository;

    public MainInteractor(DatabaseHelper db) {
        mDatabaseHelper = db;
        mGetCases = new GetCases();
        mGetTypes = new GetTypes();
        mTypeRepository = new TypeRepository(mDatabaseHelper);
        mCaseRepository = new CaseRepository(mDatabaseHelper);
    }

    public List<Case> getCasesByType(String type) {
        return mGetCases.execute(mCaseRepository, new CasesByTypeSpecification(type));
    }

    public List<Type> getAllTypes() {
        return mGetTypes.execute(mTypeRepository, new AllTypesSpecification());
    }

    public List<Case> getCasesByLastOpenedType() {
        return mGetCases.execute(mCaseRepository, new CasesByTypeSpecification(getLastOpenedType().getName()));
    }

    private Type getLastOpenedType() {
        return mGetTypes.execute(mTypeRepository, new LastOpenedTypeSpecification()).get(0);
    }

    public void addCase() {

    }

    public void addType() {

    }


}
