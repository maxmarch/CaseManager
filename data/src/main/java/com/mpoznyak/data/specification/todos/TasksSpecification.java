package com.mpoznyak.data.specification.todos;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.*;

public class TasksSpecification {

    private static final String baseQuery = "SELECT DISTINCT " + COLUMN_TASK+ " FROM " + TABLE_TODOS;

    public static String forCaseId(int id) {
        return baseQuery + " WHERE " + COLUMN_CASE_ID + " = " + id + ";";
    }
}
