package com.mpoznyak.data.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.data.mapper.cases.ToCaseFromCursor;
import com.mpoznyak.data.mapper.cases.ToContentValuesFromCase;
import com.mpoznyak.domain.model.Case;
import com.mpoznyak.domain.repository.Repository;
import com.mpoznyak.domain.repository.Specification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_NAME;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.TABLE_CASES;

public class CaseRepository implements Repository<Case> {

    private final DatabaseHelper mDatabaseHelper;

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
                final ContentValues contentValues = ToContentValuesFromCase.map(item);
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
        try (SQLiteDatabase database = mDatabaseHelper.getWritableDatabase()) {
            database.delete(TABLE_CASES, COLUMN_NAME + "= ?", new String[]{aCase.getName()});
        }
    }

    @Override
    public void update(Case item) {
        ContentValues updatedCaseCv = ToContentValuesFromCase.map(item);
        try (SQLiteDatabase database = mDatabaseHelper.getWritableDatabase()) {
            database.update(TABLE_CASES, updatedCaseCv, COLUMN_NAME + " = ?"
                    , new String[]{item.getName()});
        }
    }

    @Override
    public List<Case> query(Specification specification) {

        final List<Case> cases = new ArrayList<>();

        try (SQLiteDatabase database = mDatabaseHelper.getReadableDatabase()) {
            final Cursor cursor = database.rawQuery(specification.toSqlQuery(), new String[]{});

            for (int i = 0, size = cursor.getCount(); i < size; i++) {
                cursor.moveToPosition(i);
                cases.add(ToCaseFromCursor.map(cursor));
            }
            cursor.close();

            return cases;
        }
    }
}
