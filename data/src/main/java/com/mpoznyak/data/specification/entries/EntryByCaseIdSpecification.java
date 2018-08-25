package com.mpoznyak.data.specification.entries;


import com.mpoznyak.domain.repository.Specification;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.TABLE_ENTRIES;

public class EntryByCaseIdSpecification implements Specification {

    private int caseId;

    public EntryByCaseIdSpecification(int id) {
        caseId = id;
    }

    @Override
    public String toSqlQuery() {
        return "SELECT * FROM " + TABLE_ENTRIES + " WHERE case_id = " + caseId + ";";
    }
}
