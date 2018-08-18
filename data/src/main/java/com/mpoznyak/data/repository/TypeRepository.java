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
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.TABLE_ENTRIES;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.TABLE_TYPES;


public class TypeRepository implements Repository<Type> {

    DatabaseHelper mDatabaseHelper;

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
                database.insert(TABLE_TYPES, null, cv);
                database.setTransactionSuccessful();
            }
        } finally {
            database.endTransaction();
            database.close();
        }
    }

    @Override
    public void remove(Type item) {
        final SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
        try {
            database.delete(TABLE_TYPES, COLUMN_ID + "= ?"
                    , new String[]{String.valueOf(item.getId())});
        } finally {
            database.close();
        }
    }

    @Override
    public void update(Type item) {
        ContentValues cv = ToContentValuesFromType.map(item);
        final SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
        try {
            database.update(TABLE_ENTRIES, cv, COLUMN_ID + " = ?"
                    , new String[]{String.valueOf(item.getId())});
        } finally {
            database.close();
        }
    }

    @Override
    public List<Type> query(Specification specification) {
        final SQLiteDatabase database = mDatabaseHelper.getReadableDatabase();
        final List<Type> types = new ArrayList<>();

        try {
            final Cursor cursor = database.rawQuery(specification.toSqlQuery(), new String[]{});

            for (int i = 0, size = cursor.getCount(); i < size; i++) {
                cursor.moveToPosition(i);
                types.add(ToTypeFromCursor.map(cursor));
            }
            cursor.close();
            return types;
        } finally {
            database.close();
        }
    }
}
