package com.mpoznyak.data.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.data.mapper.entries.ToContentValuesFromEntry;
import com.mpoznyak.data.mapper.entries.ToEntryFromCursor;
import com.mpoznyak.domain.model.Entry;
import com.mpoznyak.domain.repository.Repository;
import com.mpoznyak.domain.repository.Specification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_ID;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.TABLE_ENTRIES;

public class EntryRepository implements Repository<Entry> {

    private final DatabaseHelper mDatabaseHelper;

    public EntryRepository(DatabaseHelper helper) {
        mDatabaseHelper = helper;
    }

    @Override
    public void add(Entry item) {
        add(Collections.singletonList(item));
    }

    @Override
    public void add(Iterable<Entry> entries) {
        final SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
        database.beginTransaction();
        try {
            for (Entry item : entries) {
                final ContentValues contentValues = ToContentValuesFromEntry.map(item);
                database.insert(TABLE_ENTRIES, null, contentValues);
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
            mDatabaseHelper.close();
        }
    }

    @Override
    public void remove(Entry item) {
        try (SQLiteDatabase database = mDatabaseHelper.getWritableDatabase()) {
            database.delete(TABLE_ENTRIES, COLUMN_ID + "= ?"
                    , new String[]{String.valueOf(item.getId())});
        }
    }

    @Override
    public void update(Entry item) {
        ContentValues updatedEntryCv = ToContentValuesFromEntry.map(item);
        try (SQLiteDatabase database = mDatabaseHelper.getWritableDatabase()) {
            database.update(TABLE_ENTRIES, updatedEntryCv, COLUMN_ID + " = ?"
                    , new String[]{String.valueOf(item.getId())});
        }
    }

    @Override
    public List<Entry> query(Specification specification) {

        final List<Entry> entries = new ArrayList<>();

        try (SQLiteDatabase database = mDatabaseHelper.getReadableDatabase()) {
            final Cursor cursor = database.rawQuery(specification.toSqlQuery(), new String[]{});

            for (int i = 0, size = cursor.getCount(); i < size; i++) {
                cursor.moveToPosition(i);
                entries.add(ToEntryFromCursor.map(cursor));
            }

            cursor.close();

            return entries;
        }
    }
}
