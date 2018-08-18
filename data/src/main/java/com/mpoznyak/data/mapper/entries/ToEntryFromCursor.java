package com.mpoznyak.data.mapper.entries;

import android.database.Cursor;

import com.mpoznyak.domain.model.Entry;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_CASE_ID;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_ID;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_PATH;

public class ToEntryFromCursor {

    private static final String TAG = ToEntryFromCursor.class.getSimpleName();

    public static Entry map (Cursor cursor) {
        Entry entry = new Entry(cursor.getString(cursor.getColumnIndex(COLUMN_PATH)));
        entry.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        entry.setCase_Id(cursor.getInt(cursor.getColumnIndex(COLUMN_CASE_ID)));
        return entry;
    }

}
