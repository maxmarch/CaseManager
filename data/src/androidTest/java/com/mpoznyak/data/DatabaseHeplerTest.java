package com.mpoznyak.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.*;


@RunWith(AndroidJUnit4.class)
public class DatabaseHeplerTest {

    private String path = "mypath";

    @Test
    public void testDatabaseInitialization() {
        DatabaseHelper db = DatabaseHelper.newInstance(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase data = db.getWritableDatabase();
        data.execSQL("INSERT INTO " + TABLE_ENTRIES + "(" + COLUMN_PATH + ")" + " VALUES "
                + "(" + "\'" + "mypath"+ "\'" + ");");
        Cursor cursor = data.rawQuery("SELECT " + COLUMN_PATH + " FROM " + TABLE_ENTRIES + " WHERE " + COLUMN_PATH
                + " = " + "\'" + path + "\'" +";", new String[] {});
        int index = cursor.getColumnIndex(COLUMN_PATH);
        for (int i = 1; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String pathFromCursor = cursor.getString(index);
            System.out.println(pathFromCursor);
            if (path == pathFromCursor)
                assertTrue("Path should equals a value from database", path.equals(pathFromCursor));
        }
        data.close();
    }



}