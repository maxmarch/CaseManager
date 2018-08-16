package com.mpoznyak.data.mapper;

import android.content.ContentValues;
import com.mpoznyak.domain.model.Todo;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.*;

public class toContentValuesFromTodo {

    private static final String CASE_ID = COLUMN_CASE_ID;
    private static final String TASK = COLUMN_TASK;

    public static ContentValues map(Todo item) {
        ContentValues values = new ContentValues();
        values.put(CASE_ID, item.getCaseId());
        values.put(TASK, item.getTask());
        return values;
    }
}
