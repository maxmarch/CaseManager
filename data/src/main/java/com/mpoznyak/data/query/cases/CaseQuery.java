package com.mpoznyak.data.query.cases;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.*;

public class CaseQuery {

    private static final String baseQuery = "SELECT DISTINCT " +  COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_TYPE
            + ", " + COLUMN_CREATION_DATE + ", " + COLUMN_PATH + ", " + COLUMN_TASK + " "
            + "FROM " + TABLE_CASES + " INNER JOIN " + TABLE_ENTRIES + " ON " + TABLE_CASES + "." + COLUMN_ID
            + " = " + TABLE_ENTRIES + "." + COLUMN_CASE_ID
            + " INNER JOIN " + TABLE_TODOS + " ON " + TABLE_CASES + "." + COLUMN_ID + " = "
            + TABLE_TODOS + "." + COLUMN_CASE_ID;

    public static String getForId(int id) {
        return baseQuery + " WHERE " + TABLE_CASES + "." + COLUMN_ID + " = " + id + ";";
    }

    public static String getForType(String type) {
        return baseQuery + " WHERE " + TABLE_CASES + "." + COLUMN_TYPE + " = " + type + ";";
    }

}
