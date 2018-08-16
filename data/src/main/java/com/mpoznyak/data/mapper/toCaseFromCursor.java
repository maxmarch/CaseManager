package com.mpoznyak.data.mapper;

import android.database.Cursor;
import android.util.Log;
import com.mpoznyak.domain.model.Case;
import java.text.ParseException;


import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.*;

public class toCaseFromCursor {

    private static final String TAG = toCaseFromCursor.class.getSimpleName();

    public static Case map (Cursor cursor) {
        Case aCase = new Case();
        aCase.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        aCase.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
        try {
            aCase.setCreationDate(cursor.getString(cursor.getColumnIndex(COLUMN_CREATION_DATE)));
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
        }
        aCase.setType(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));
        Log.d(TAG,aCase.toString());
        return aCase;
    }

    /*
    private static Todo getTodoFromCursor(Cursor cursor) {
        Todo todo = new Todo();
        List<String> taskList = new ArrayList<>();
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String task = cursor.getString(cursor.getColumnIndex(COLUMN_TASK));
            if (!taskList.contains(task)) {
                taskList.add(task);
            }
        }
        todo.setTasks(taskList);
        return todo;
    }

    private static List<Entry> getEntriesFromCursor(Cursor cursor) {
        List<Entry> entries = new ArrayList<>();
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String path = cursor.getString(cursor.getColumnIndex(COLUMN_PATH));
            Entry en = new Entry(path);
            if (!entries.contains(en)) {
                entries.add(en);
            }
        }
        return entries;
    }
     */
}
