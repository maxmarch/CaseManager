package com.mpoznyak.data.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.data.mapper.toCaseFromCursor;
import com.mpoznyak.data.mapper.toContentValuesFromCase;
import com.mpoznyak.domain.model.Case;
import com.mpoznyak.domain.repository.Repository;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.*;

public class CaseRepository implements Repository<Case> {

    private DatabaseHelper mDatabaseHelper;

    public CaseRepository(DatabaseHelper databaseHelper) {
        mDatabaseHelper = databaseHelper;

    }
    @Override
    public void add(Case item) {
        add(Collections.singletonList(item));
    }

    @Override
    public void add(Iterable<Case> items) {
        final SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
        database.beginTransaction();

        try {
            for (Case item : items) {
                final ContentValues contentValues = toContentValuesFromCase.map(item);
                database.insert(TABLE_CASES, null, contentValues);
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
            mDatabaseHelper.close();
        }

    }

    @Override
    public void remove(Case aCase) {
        final SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
        try {
            database.delete(TABLE_CASES, COLUMN_NAME + "= ?", new String[] {aCase.getName()});
        } finally {
            database.close();
        }
    }

    @Override
    public void update(Case item) {
        ContentValues updatedCaseCv = toContentValuesFromCase.map(item);
        final SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
        try {
            database.update(TABLE_CASES, updatedCaseCv, COLUMN_NAME + " = ?"
                    , new String[]{item.getName()});
        } finally {
            database.close();
        }
    }

    @Override
    public List<Case> query(String query) {

            final SQLiteDatabase database = mDatabaseHelper.getReadableDatabase();
            final List<Case> cases = new ArrayList<>();

            try {
                final Cursor cursor = database.rawQuery(query, new String[]{});

                for (int i = 0, size = cursor.getCount(); i < size; i++) {
                    cursor.moveToPosition(i);
                    cases.add(toCaseFromCursor.map(cursor));
                }
                cursor.close();

                return cases;
            } finally {
                database.close();
        }
    }
}
