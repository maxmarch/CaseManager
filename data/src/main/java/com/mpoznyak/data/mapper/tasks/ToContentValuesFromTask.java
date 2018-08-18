package com.mpoznyak.data.mapper.tasks;

import android.content.ContentValues;

import com.mpoznyak.domain.model.Task;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_CASE_ID;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_TASK;

public class ToContentValuesFromTask {

    private static final String CASE_ID = COLUMN_CASE_ID;
    private static final String TASK = COLUMN_TASK;

    public static ContentValues map(Task item) {
        ContentValues values = new ContentValues();
        values.put(CASE_ID, item.getCaseId());
        values.put(TASK, item.getTask());
        return values;
    }
}
