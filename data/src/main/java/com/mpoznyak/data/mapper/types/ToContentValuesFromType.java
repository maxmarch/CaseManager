package com.mpoznyak.data.mapper.types;

import android.content.ContentValues;

import com.mpoznyak.domain.model.Type;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_COLOR;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_ID;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_LAST_OPENED;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_NAME;

public class ToContentValuesFromType {

    private static final String ID = COLUMN_ID;
    private static final String NAME = COLUMN_NAME;
    private static final String LAST_OPENED = COLUMN_LAST_OPENED;
    private static final String COLOR = COLUMN_COLOR;



    public static ContentValues map(Type item) {
        ContentValues values = new ContentValues();
        values.put(ID, item.getId());
        values.put(NAME, item.getName());
        values.put(LAST_OPENED, item.isLastOpened() ? 1 : 0);
        values.put(COLOR, item.getColor());
        return values;
    }
}
