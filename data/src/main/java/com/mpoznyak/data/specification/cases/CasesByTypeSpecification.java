package com.mpoznyak.data.specification.cases;

import com.mpoznyak.domain.repository.Specification;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.TABLE_CASES;

public class CasesByTypeSpecification implements Specification {

    private final String type;

    public CasesByTypeSpecification(String type) {
        this.type = type;
    }

    @Override
    public String toSqlQuery() {
        return "SELECT * FROM " + TABLE_CASES + " WHERE type = " + "\'" + type + "\'" + ";";
    }
}
