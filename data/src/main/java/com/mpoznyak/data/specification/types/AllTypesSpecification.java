package com.mpoznyak.data.specification.types;


import com.mpoznyak.domain.repository.Specification;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.TABLE_TYPES;

public class AllTypesSpecification implements Specification {

    @Override
    public String toSqlQuery() {
        return "SELECT * FROM " + TABLE_TYPES + ";";
    }
}
