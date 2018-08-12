package com.mpoznyak.data.specification.entries;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.*;


public class PathsSpecification {

    private static final String baseQuery = "SELECT DISTINCT " + COLUMN_PATH + " FROM " + TABLE_ENTRIES;

    public static String forCaseId(int id) {
        return baseQuery + " WHERE " + COLUMN_CASE_ID + " = " + id + ";";
    }
}
