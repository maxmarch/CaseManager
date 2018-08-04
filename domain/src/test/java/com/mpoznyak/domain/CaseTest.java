package com.mpoznyak.domain;

import com.mpoznyak.domain.model.Case;
import com.mpoznyak.domain.model.Todo;
import org.junit.Test;
import java.time.*;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
        testCase = new Case();
        assertTrue(date.before(testCase.getCreationDate())
                || date.equals(testCase.getCreationDate()));
    }

    @Test
    public void shouldReturnTodo() {
        testCase = new Case();
        assertTrue(testCase.getTodo() instanceof Todo);
    }

    @Test
    public void testComparingTwoCaseInstances() {
        assertEquals("Should return 1", 0 , case1.compareTo(case2));
    }




}
