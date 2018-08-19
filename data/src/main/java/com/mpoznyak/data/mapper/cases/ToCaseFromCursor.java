package com.mpoznyak.data.mapper.cases;

import android.database.Cursor;
import android.util.Log;

import com.mpoznyak.domain.model.Case;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_CREATION_DATE;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_ID;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_NAME;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_TYPE;

public class ToCaseFromCursor {

    private static final String TAG = ToCaseFromCursor.class.getSimpleName();

    public static Case map (Cursor cursor) {
        Case aCase = new Case();
        aCase.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        aCase.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
        aCase.setCreationDate(cursor.getString(cursor.getColumnIndex(COLUMN_CREATION_DATE)));
        aCase.setType(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));
        Log.d(TAG,aCase.toString());
        return aCase;
    }
}
