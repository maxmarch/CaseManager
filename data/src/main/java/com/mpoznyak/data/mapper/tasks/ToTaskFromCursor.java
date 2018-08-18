package com.mpoznyak.data.mapper.tasks;


import android.database.Cursor;

import com.mpoznyak.data.mapper.entries.ToEntryFromCursor;
import com.mpoznyak.domain.model.Task;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_CASE_ID;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_ID;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_TASK;

public class ToTaskFromCursor {

    private static final String TAG = ToEntryFromCursor.class.getSimpleName();

    public static Task map(Cursor cursor) {
        Task task = new Task();
        task.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        task.setCaseId(cursor.getInt(cursor.getColumnIndex(COLUMN_CASE_ID)));
        task.setTask(cursor.getString(cursor.getColumnIndex(COLUMN_TASK)));
        return task;
    }
}
