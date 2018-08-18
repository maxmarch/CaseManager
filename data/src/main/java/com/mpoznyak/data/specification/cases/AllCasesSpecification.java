package com.mpoznyak.data.specification.cases;

import com.mpoznyak.domain.repository.Specification;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.TABLE_CASES;

public class AllCasesSpecification implements Specification {

    public String toSqlQuery() {
        return "SELECT *" + " FROM " + TABLE_CASES + ";";
    }
}
