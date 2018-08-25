package com.mpoznyak.data.mapper.entries;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.domain.model.Case;
import com.mpoznyak.domain.model.Entry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static com.mpoznyak.data.DatabaseHelper.DATABASE_NAME;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class toEntryFromCursorTest {

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
    public void testMap() {
        Case aCase = new Case();
        aCase.setCreationDate(new Date());
        aCase.setType("type1");
        aCase.setName("case1");
        Entry entry = new Entry("/Users");
        entry.setCaseId(1);
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        db.beginTransaction();
        db.execSQL("insert into types (name) Values (\'type1\')");
        db.execSQL("insert into " + DatabaseHelper.DatabaseContract.TABLE_CASES
                + " (name, type, creation_date) " + " values " + "(\'" + aCase.getName() + "\'"
                + ", " + "\'" + aCase.getType() + "\'" + ", " + "\'" + aCase.getCreationDate() + "\'"
                + ");");
        db.execSQL("insert into entries (case_id, path) values (" + entry.getCase_Id() + " , \'"
                + entry.getPath().toString() + "\')");
        db.setTransactionSuccessful();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.DatabaseContract.TABLE_ENTRIES + ";",
                new String[]{}, null);
        cursor.moveToFirst();
        Entry dbEntry = ToEntryFromCursor.map(cursor);
        assertEquals(entry.getPath().toString(), dbEntry.getPath().toString());

    }

}
