package com.mpoznyak.data.repository;

import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.domain.model.Case;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Max Poznyak on 05.08.2018.
 */
public class CaseRepositoryTest {

    @Test
    public void testAddItem() {
        DatabaseHelper helper = new DatabaseHelper(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase db = helper.getWritableDatabase();
        CaseRepository repo = new CaseRepository(helper);
        Case aCase = new Case();
        aCase.setName("Hey");
        repo.add(aCase);
    }

    @Test
    public void testAddItems() {
        DatabaseHelper helper = new DatabaseHelper(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase db = helper.getWritableDatabase();
        CaseRepository repo = new CaseRepository(helper);
        List<Case> items = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Case aCase = new Case();
            aCase.setName("case" + i);
            items.add(aCase);
            repo.add(aCase);
        }
    }

    @Test
    public void testGetItem() {

    }

}