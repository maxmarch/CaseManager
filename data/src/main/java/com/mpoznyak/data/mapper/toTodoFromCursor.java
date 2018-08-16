package com.mpoznyak.data.mapper;


import android.database.Cursor;
import com.mpoznyak.domain.model.Todo;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.*;

public class toTodoFromCursor {

    private static final String TAG = toEntryFromCursor.class.getSimpleName();

    public static Todo map (Cursor cursor) {
        Todo todo = new Todo();
        todo.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        todo.setCaseId(cursor.getInt(cursor.getColumnIndex(COLUMN_CASE_ID)));
        todo.setTask(cursor.getString(cursor.getColumnIndex(COLUMN_TASK)));
        return todo;
    }
}
