package com.mpoznyak.data.query.cases;


import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.*;

public class AllTypesQuery {

    public static String get() {
        return "SELECT DISTINCT " + COLUMN_TYPE + " FROM " + TABLE_CASES +";";
    }
}
