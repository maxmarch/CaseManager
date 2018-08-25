package com.mpoznyak.data.mapper.cases;

import android.content.ContentValues;

import com.mpoznyak.domain.model.Case;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ToContentValuesFromCaseTest {

    @Test
    public void testMap() {
        Case aCase = new Case();
        aCase.setCreationDate(new Date());
        aCase.setType("type1");
        aCase.setName("case1");
        ContentValues cv = ToContentValuesFromCase.map(aCase);
        assertEquals(cv.get("name"), aCase.getName());


    }

}