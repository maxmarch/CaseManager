package com.mpoznyak.data.mappers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.data.mapper.cases.ToCaseFromCursor;
import com.mpoznyak.domain.model.Case;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_CREATION_DATE;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class toCaseFromCursorTest {

    private void prepareTestTable() {
        DatabaseHelper helper = DatabaseHelper.getInstance(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("INSERT INTO cases(name,creation_date,type) " +
                "VALUES('testName3', '12/12/2012', 'testtype2');", new String[] {});
        db.execSQL("INSERT INTO entries(case_id,path) " +
                "VALUES(3, '/Users');", new String[] {});
        db.execSQL("INSERT INTO entries(case_id,path) " +
                "VALUES(3, '/Users1');", new String[] {});
        db.execSQL("INSERT INTO entries(case_id,path) " +
                "VALUES(3, '/Users2');", new String[] {});
        db.execSQL("INSERT INTO todos(case_id,task) " +
                "VALUES(3, 'task1');", new String[] {});
        db.execSQL("INSERT INTO todos(case_id,task) " +
                "VALUES(3, 'task2');", new String[] {});
        helper.close();

    }

    @Test
    public void testMap() throws ParseException {
        DatabaseHelper helper = DatabaseHelper.getInstance(InstrumentationRegistry.getTargetContext());
        Cursor cursor = helper.getWritableDatabase().rawQuery("SELECT DISTINCT cases.id AS id,name,type,creation_date,path,task " +
                "FROM cases INNER JOIN entries ON cases.id = entries.case_id " +
                "INNER JOIN todos ON cases.id = todos.case_id WHERE cases.id = 3;", new String[] {});
        cursor.moveToFirst();
        Date testDate;
        testDate = new SimpleDateFormat("dd/MM/yyyy").parse(cursor.getString(cursor.getColumnIndex(COLUMN_CREATION_DATE)));
        Case aCase = ToCaseFromCursor.map(cursor);
        assertEquals(true, aCase.getCreationDate().equals(testDate));
        cursor.close();
        helper.close();
    }

}
