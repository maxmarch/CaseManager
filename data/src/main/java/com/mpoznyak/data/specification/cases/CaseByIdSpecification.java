package com.mpoznyak.data.specification.cases;

import com.mpoznyak.domain.repository.Specification;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.TABLE_CASES;

public class CaseByIdSpecification implements Specification {

    private final int id;

    public CaseByIdSpecification(int id) {
        this.id = id;
    }

    @Override
    public String toSqlQuery() {
        return "SELECT * FROM " + TABLE_CASES + " WHERE id = " + id + ";";
    }
}
