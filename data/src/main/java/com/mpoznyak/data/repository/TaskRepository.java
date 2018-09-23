package com.mpoznyak.data.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.data.mapper.tasks.ToContentValuesFromTask;
import com.mpoznyak.data.mapper.tasks.ToTaskFromCursor;
import com.mpoznyak.domain.model.Task;
import com.mpoznyak.domain.repository.Repository;
import com.mpoznyak.domain.repository.Specification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_ID;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.TABLE_TASKS;

public class TaskRepository implements Repository<Task> {

    private final DatabaseHelper mDatabaseHelper;

    public TaskRepository(DatabaseHelper helper) {
        mDatabaseHelper = helper;
    }

    @Override
    public void add(Task item) {
        add(Collections.singletonList(item));
    }

    @Override
    public void add(Iterable<Task> tasks) {
        final SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
        database.beginTransaction();
        try {
            for (Task item : tasks) {
                final ContentValues contentValues = ToContentValuesFromTask.map(item);
                database.insert(TABLE_TASKS, null, contentValues);
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
            mDatabaseHelper.close();
        }
    }

    @Override
    public void remove(Task item) {
        try (SQLiteDatabase database = mDatabaseHelper.getWritableDatabase()) {
            database.delete(TABLE_TASKS, COLUMN_ID + " = ?"
                    , new String[]{String.valueOf(item.getId())});
        }
    }

    @Override
    public void update(Task item) {
        try (SQLiteDatabase database = mDatabaseHelper.getWritableDatabase()) {
            database.update(TABLE_TASKS, ToContentValuesFromTask.map(item)
                    , COLUMN_ID + " = ?", new String[]{String.valueOf(item.getId())});
        }
    }

    @Override
    public List<Task> query(Specification specification) {
        final List<Task> tasks = new ArrayList<>();

        try (SQLiteDatabase database = mDatabaseHelper.getReadableDatabase()) {
            final Cursor cursor = database.rawQuery(specification.toSqlQuery(), new String[]{});

            for (int i = 0, size = cursor.getCount(); i < size; i++) {
                cursor.moveToPosition(i);
                tasks.add(ToTaskFromCursor.map(cursor));
            }

            cursor.close();

            return tasks;
        }
    }
}
