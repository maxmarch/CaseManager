package com.mpoznyak.data.repository;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.data.mapper.toContentValuesFromCase;
import com.mpoznyak.domain.repository.Specification;
import com.mpoznyak.domain.model.Case;
import com.mpoznyak.domain.repository.Repository;
import java.util.Collections;
import java.util.List;

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
    public void remove(Case item) {

    }

    @Override
    public void update(Case item) {

    }

    @Override
    public List<Case> query(Specification specification) {
        return null;
    }
}
