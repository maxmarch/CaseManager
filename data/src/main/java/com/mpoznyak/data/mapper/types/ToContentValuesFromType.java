package com.mpoznyak.data.mapper.types;

import android.content.ContentValues;

import com.mpoznyak.domain.model.Type;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_ID;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_NAME;

public class ToContentValuesFromType {

    private static final String ID = COLUMN_ID;
    private static final String NAME = COLUMN_NAME;

    public static ContentValues map(Type item) {
        ContentValues values = new ContentValues();
        values.put(ID, item.getId());
        values.put(NAME, item.getName());
        return values;
    }
}
