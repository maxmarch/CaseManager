package com.mpoznyak.data.mapper.types;

import android.database.Cursor;

import com.mpoznyak.domain.model.Type;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_COLOR;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_ID;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_LAST_OPENED;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_NAME;

public class ToTypeFromCursor {

    public static Type map(Cursor cursor) {
        Type type = new Type();
        type.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        type.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
        type.setLastOpened(1 == cursor.getInt(cursor.getColumnIndex(COLUMN_LAST_OPENED)));
        type.setColor(cursor.getString(cursor.getColumnIndex(COLUMN_COLOR)));
        return type;
    }
}
