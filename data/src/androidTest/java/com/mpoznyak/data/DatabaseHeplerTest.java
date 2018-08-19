package com.mpoznyak.data;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.mpoznyak.data.DatabaseHelper.DATABASE_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class DatabaseHeplerTest {

    private DatabaseHelper mDatabaseHelper;

    @Before
    public void setUp() {
        InstrumentationRegistry.getContext().deleteDatabase(DATABASE_NAME);
        mDatabaseHelper = DatabaseHelper.getInstance(InstrumentationRegistry.getTargetContext());
    }

    @After
    public void disconnectDb() {
        mDatabaseHelper.close();
    }

    @Test
    public void onCreate() {
        String expected = "type1";
        mDatabaseHelper.onCreate(mDatabaseHelper.getWritableDatabase());
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        db.execSQL("insert into " + DatabaseHelper.DatabaseContract.TABLE_TYPES
                + " (name) " + " values " + "(\'" + expected + "\');");
        Cursor cursor = db.rawQuery("SELECT name FROM " + DatabaseHelper.DatabaseContract.TABLE_TYPES + ";",
                new String[]{}, null);
        cursor.moveToFirst();
        assertEquals(expected, cursor.getString(cursor.getColumnIndex(DatabaseHelper.DatabaseContract.COLUMN_NAME)));
        assertTrue(db.isOpen());
        assertFalse(db.isReadOnly());
    }

    @Test
    public void testGetInstance() {
        DatabaseHelper dh = DatabaseHelper.getInstance(InstrumentationRegistry.getTargetContext());
        assertEquals(mDatabaseHelper.DATABASE_NAME, dh.DATABASE_NAME);
    }



}