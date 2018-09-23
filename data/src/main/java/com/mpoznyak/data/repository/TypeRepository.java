package com.mpoznyak.data.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.data.mapper.types.ToContentValuesFromType;
import com.mpoznyak.data.mapper.types.ToTypeFromCursor;
import com.mpoznyak.domain.model.Type;
import com.mpoznyak.domain.repository.Repository;
import com.mpoznyak.domain.repository.Specification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_ID;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_NAME;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.TABLE_TYPES;


public class TypeRepository implements Repository<Type> {

    private final DatabaseHelper mDatabaseHelper;

    public TypeRepository(DatabaseHelper helper) {
        mDatabaseHelper = helper;
    }

    @Override
    public void add(Type item) {
        add(Collections.singletonList(item));
    }

    @Override
    public void add(Iterable<Type> items) {
        final SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
        database.beginTransaction();
        try {
            for (Type type : items) {
                final ContentValues cv = ToContentValuesFromType.map(type);
                database.insertOrThrow(TABLE_TYPES, null, cv);
                database.setTransactionSuccessful();
            }
        } finally {
            database.endTransaction();
            database.close();
        }
    }

    @Override
    public void remove(Type item) {
        try (SQLiteDatabase database = mDatabaseHelper.getWritableDatabase()) {
            database.delete(TABLE_TYPES, COLUMN_NAME + "= ?"
                    , new String[]{String.valueOf(item.getName())});
        }
    }

    @Override
    public void update(Type item) {
        ContentValues cv = ToContentValuesFromType.map(item);
        try (SQLiteDatabase database = mDatabaseHelper.getWritableDatabase()) {
            database.update(TABLE_TYPES, cv, COLUMN_ID + " = ?"
                    , new String[]{String.valueOf(item.getId())});
        }
    }

    @Override
    public List<Type> query(Specification specification) {
        final List<Type> types = new ArrayList<>();

        try (SQLiteDatabase database = mDatabaseHelper.getReadableDatabase()) {
            final Cursor cursor = database.rawQuery(specification.toSqlQuery(), new String[]{});

            for (int i = 0, size = cursor.getCount(); i < size; i++) {
                cursor.moveToPosition(i);
                types.add(ToTypeFromCursor.map(cursor));
            }
            cursor.close();
            return types;
        }
    }
}
