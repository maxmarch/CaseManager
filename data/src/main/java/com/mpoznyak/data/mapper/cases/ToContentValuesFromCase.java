package com.mpoznyak.data.mapper.cases;

import android.content.ContentValues;

import com.mpoznyak.domain.model.Case;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_CREATION_DATE;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_NAME;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_TYPE;

public class ToContentValuesFromCase {

    private static final String TYPE = COLUMN_TYPE;
    private static final String NAME = COLUMN_NAME;
    private static final String CREATION_DATE = COLUMN_CREATION_DATE;

    public static ContentValues map(Case aCase) {
        ContentValues values = new ContentValues();
        values.put(TYPE, aCase.getType());
        values.put(NAME, aCase.getName());
        values.put(CREATION_DATE, aCase.getCreationDate());
        return values;

    }
}
