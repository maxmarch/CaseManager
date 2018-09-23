package com.mpoznyak.data.specification.types;


import com.mpoznyak.domain.repository.Specification;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.TABLE_TYPES;

public class TypeByNameSpecification implements Specification {

    private final String name;

    public TypeByNameSpecification(String name) {
        this.name = name;
    }

    @Override
    public String toSqlQuery() {
        return "SELECT * FROM " + TABLE_TYPES + " WHERE name = " + "\'" + name + "\'" + ";";
    }
}
