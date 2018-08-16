package com.mpoznyak.data.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.data.mapper.toContentValuesFromTodo;
import com.mpoznyak.data.mapper.toTodoFromCursor;
import com.mpoznyak.domain.model.Todo;
import com.mpoznyak.domain.repository.Repository;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_ID;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.TABLE_TODOS;

public class TodoRepository implements Repository<Todo> {

    private DatabaseHelper mDatabaseHelper;

    public TodoRepository(DatabaseHelper helper) {
        mDatabaseHelper = helper;
    }

    @Override
    public void add(Todo item) {
        add(Collections.singletonList(item));
    }

    @Override
    public void add(Iterable<Todo> todos) {
        final SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
        try {
            for (Todo item : todos) {
                final ContentValues contentValues = toContentValuesFromTodo.map(item);
                database.insert(TABLE_TODOS, null, contentValues);
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
            mDatabaseHelper.close();
        }
    }

    @Override
    public void remove(Todo item) {
        final SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
        try {
            database.delete(TABLE_TODOS, COLUMN_ID + " = ?"
                    , new String[]{String.valueOf(item.getId())});
        } finally {
            database.close();
        }
    }

    @Override
    public void update(Todo item) {
        final SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
        try {
            database.update(TABLE_TODOS, toContentValuesFromTodo.map(item)
                    ,COLUMN_ID + " = ?", new String[]{String.valueOf(item.getId())});
        } finally {
            database.close();
        }
    }

    @Override
    public List<Todo> query(String query) {
        final SQLiteDatabase database = mDatabaseHelper.getReadableDatabase();
        final List<Todo> todos = new ArrayList<>();

        try {
            final Cursor cursor = database.rawQuery(query, new String[]{});

            for (int i = 0, size = cursor.getCount(); i < size; i++) {
                cursor.moveToPosition(i);
                todos.add(toTodoFromCursor.map(cursor));
            }

            cursor.close();

            return todos;
        } finally {
            database.close();
        }
    }
}
