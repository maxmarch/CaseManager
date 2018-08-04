package com.mpoznyak.data;

import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by Max Poznyak on 04.08.2018.
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseHeplerTest {

    @Test
    public void testDb() {
        DatabaseHepler db = DatabaseHepler.newInstance(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase data = db.getWritableDatabase();
        data.execSQL("INSERT INTO todos (name) VALUES ('ajsnd')");

        data.close();
    }



}