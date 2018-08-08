package com.mpoznyak.data.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.domain.model.Case;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.*;
import static org.junit.Assert.*;

/**
 * Created by Max Poznyak on 05.08.2018.
 */
public class CaseRepositoryTest {

    @Test
    public void testAddItem() {
        DatabaseHelper helper = DatabaseHelper.getInstance(InstrumentationRegistry.getTargetContext());
        CaseRepository repo = new CaseRepository(helper);
        Case aCase = new Case();
        String name = "name";
        aCase.setName(name);
        repo.add(aCase);
        Cursor cursor = helper.getWritableDatabase().rawQuery("SELECT " + COLUMN_NAME + " FROM " + TABLE_CASES + " WHERE " + COLUMN_NAME
                + " = " + "\'" + name + "\'" +";", new String[] {});
        int index = cursor.getColumnIndex(COLUMN_NAME);
        for (int i = 1; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String nameFromCursor = cursor.getString(index);
            System.out.println(nameFromCursor);
            if (name == nameFromCursor)
                assertTrue("Name should equals a value from database", name.equals(nameFromCursor));
        }
        cursor.close();
        helper.close();
    }

    @Test
    public void testAddItems() {
        DatabaseHelper helper = DatabaseHelper.getInstance(InstrumentationRegistry.getTargetContext());
        CaseRepository repo = new CaseRepository(helper);
        List<Case> items = new ArrayList<>();
        String name = "name";
        for (int i = 0; i < 5; i++) {
            Case aCase = new Case();
            aCase.setName(name + i);
            items.add(aCase);
            repo.add(aCase);
        }
        Cursor cursor = helper.getWritableDatabase().rawQuery("SELECT " + COLUMN_NAME + " FROM " + TABLE_CASES + " WHERE " + COLUMN_NAME
                + " LIKE " + "\'" + name + "%" + "\'" +";", new String[] {});
        int index = cursor.getColumnIndex(COLUMN_NAME);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String nameFromCursor = cursor.getString(index);
            System.out.println(nameFromCursor);
            if (name == nameFromCursor)
                assertTrue("Name should equals a value from database", name.contains(nameFromCursor));
        }
        cursor.close();
        helper.close();
    }

    @Test
    public void testGetItem() {

    }

}