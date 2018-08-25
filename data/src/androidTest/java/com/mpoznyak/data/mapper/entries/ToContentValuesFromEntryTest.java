package com.mpoznyak.data.mapper.entries;

import android.content.ContentValues;
import android.support.test.runner.AndroidJUnit4;

import com.mpoznyak.domain.model.Entry;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ToContentValuesFromEntryTest {

    @Test
    public void testMap() {
        Entry entry = new Entry("/Users");
        entry.setCaseId(1);
        ContentValues cv = ToContentValuesFromEntry.map(entry);
        assertEquals(cv.get("path"), entry.getPath().toString());


    }
}