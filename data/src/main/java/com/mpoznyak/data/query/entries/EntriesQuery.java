package com.mpoznyak.data.query.entries;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.*;


public class EntriesQuery {

    private static final String baseQuery = "SELECT DISTINCT " + COLUMN_PATH + " FROM " + TABLE_ENTRIES;

    public static String getForCaseId(int id) {
        return baseQuery + " WHERE " + COLUMN_CASE_ID + " = " + id + ";";
    }
}
