package com.mpoznyak.data.specification.cases;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_CASE_ID;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_CREATION_DATE;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_ID;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_NAME;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_PATH;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_TASK;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_TYPE;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.TABLE_CASES;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.TABLE_ENTRIES;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.TABLE_TASKS;

public class CaseQuery {

    private static final String baseQuery = "SELECT DISTINCT " +  COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_TYPE
            + ", " + COLUMN_CREATION_DATE + ", " + COLUMN_PATH + ", " + COLUMN_TASK + " "
            + "FROM " + TABLE_CASES + " INNER JOIN " + TABLE_ENTRIES + " ON " + TABLE_CASES + "." + COLUMN_ID
            + " = " + TABLE_ENTRIES + "." + COLUMN_CASE_ID
            + " INNER JOIN " + TABLE_TASKS + " ON " + TABLE_CASES + "." + COLUMN_ID + " = "
            + TABLE_TASKS + "." + COLUMN_CASE_ID;

    public static String getForId(int id) {
        return baseQuery + " WHERE " + TABLE_CASES + "." + COLUMN_ID + " = " + id + ";";
    }

    public static String getForType(String type) {
        return baseQuery + " WHERE " + TABLE_CASES + "." + COLUMN_TYPE + " = " + type + ";";
    }

}
