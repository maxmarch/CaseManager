package com.mpoznyak.data.query.todos;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.*;

public class TasksQuery {

    private static final String baseQuery = "SELECT DISTINCT " + COLUMN_TASK+ " FROM " + TABLE_TODOS;

    public static String getForCaseId(int id) {
        return baseQuery + " WHERE " + COLUMN_CASE_ID + " = " + id + ";";
    }
}
