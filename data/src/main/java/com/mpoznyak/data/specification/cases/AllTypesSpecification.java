package com.mpoznyak.data.specification.cases;


import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.*;

public class AllTypesSpecification {

    public static String toSqlQuery() {
        return "SELECT DISTINCT " + COLUMN_TYPE + " FROM " + TABLE_CASES +";";
    }
}
