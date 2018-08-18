package com.mpoznyak.domain;

import com.mpoznyak.domain.model.Case;

import org.junit.Test;

import java.text.DateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CaseTest {

    private static final String TITLE = "Criminal Case";
    private Case testCase;
    private Case case1 = new Case();
    private Case case2 = new Case();

    @Test
    public void shouldSetTitle(){
        testCase = mock(Case.class);
        testCase.setName(TITLE);
        verify(testCase).setName(TITLE);
    }

    @Test
    public void shouldReturnTitle() {
        testCase = new Case();
        testCase.setName(TITLE);
        assertEquals("Case instance should return " + TITLE +
                " but it returns " + testCase.getName(), TITLE, testCase.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalExcWhenPassNullArgToSetTitle() {
        testCase = new Case();
        testCase.setName("");
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullExcWhenCallGetTitle() {
        testCase = new Case();
        testCase.getName();
    }

    @Test
    public void shouldReturnCreationDate() {
        Date date = new Date();
        DateFormat df = DateFormat.getDateInstance();
        String strDate = df.format(date);
        testCase = new Case();
        Case testCase1 = new Case();
        testCase1.setCreationDate(date);
        testCase.setCreationDate(strDate);
        assertEquals(0, testCase1.compareTo(testCase1));
    }

    @Test
    public void testComparingTwoCaseInstances() {
        assertEquals("Should return 1", 0 , case1.compareTo(case2));
    }




}
