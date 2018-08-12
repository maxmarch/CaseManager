package com.mpoznyak.data.mapper;

import android.database.Cursor;
import android.util.Log;

import com.mpoznyak.domain.model.Case;
import com.mpoznyak.domain.model.Entry;
import com.mpoznyak.domain.model.Todo;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.*;

public class toCaseFromCursor {

    private static final String TAG = toCaseFromCursor.class.getSimpleName();

    public static Case map (Cursor cursor) {
        Case aCase = new Case();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            aCase.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            try {
                aCase.setCreationDate(cursor.getString(cursor.getColumnIndex(COLUMN_CREATION_DATE)));
            } catch (ParseException e) {
                Log.e(TAG, e.getMessage());
            }
            aCase.setType(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));
            aCase.setTodo(getTodoFromCursor(cursor));
            aCase.setEntries(getEntriesFromCursor(cursor));
        }
        Log.d(TAG,aCase.toString());
        return aCase;
    }

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


}
