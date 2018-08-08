package com.mpoznyak.data.mapper;

import android.content.ContentValues;
import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.domain.model.Case;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.*;

public class toContentValuesFromCase {

    private static final String TYPE = COLUMN_TYPE;
    private static final String NAME = COLUMN_NAME;
    private static final String CREATION_DATE = COLUMN_CREATION_DATE;
    private static final String TODO_ID = COLUMN_TODO_ID;

    public static ContentValues map(Case aCase) {
        ContentValues values = new ContentValues();
        values.put(TYPE, aCase.getType());
        values.put(NAME, aCase.getName());
        values.put(CREATION_DATE, aCase.getCreationDate().toString());
        values.put(TODO_ID, aCase.getTodo().getId());
        return values;

    }
}
