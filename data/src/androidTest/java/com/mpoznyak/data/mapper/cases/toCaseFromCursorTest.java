package com.mpoznyak.data.mapper.cases;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.domain.model.Case;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static com.mpoznyak.data.DatabaseHelper.DATABASE_NAME;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class toCaseFromCursorTest {

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
        mDatabaseHelper.onCreate(mDatabaseHelper.getWritableDatabase());
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        db.beginTransaction();
        db.execSQL("insert into types (name) Values (\'type1\')");
        db.execSQL("insert into " + DatabaseHelper.DatabaseContract.TABLE_CASES
                + " (name, type, creation_date) " + " values " + "(\'" + aCase.getName() + "\'"
                + ", " + "\'" + aCase.getType() + "\'" + ", " + "\'" + aCase.getCreationDate() + "\'"
                + ");");
        db.setTransactionSuccessful();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.DatabaseContract.TABLE_CASES + ";",
                new String[]{}, null);
        cursor.moveToFirst();
        Case dbCase = ToCaseFromCursor.map(cursor);
        assertEquals(aCase.getName(), dbCase.getName());

    }

}
