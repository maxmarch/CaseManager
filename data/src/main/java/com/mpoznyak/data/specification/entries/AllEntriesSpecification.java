package com.mpoznyak.data.specification.entries;

import com.mpoznyak.domain.repository.Specification;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.TABLE_ENTRIES;


public class AllEntriesSpecification implements Specification {

    public String toSqlQuery() {
        return "SELECT * " + " FROM " + TABLE_ENTRIES + ";";
    }
}
