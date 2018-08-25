package com.mpoznyak.domain.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TaskTest {

    private Task task = new Task();

    @Before
    public void initTaskInstance() {
        task.setCaseId(1);
        task.setId(1);
        task.setTask("Do right now");
    }

    @Test
    public void testGetters() {
        assertEquals(1, task.getId());
        assertEquals(1, task.getCaseId());
        assertEquals("Do right now", task.getTask());
    }

    @Test
    public void testSetters() {
        task.setCaseId(2);
        task.setId(2);
        task.setTask("Do right now1");
        assertEquals(2, task.getId());
        assertEquals(2, task.getCaseId());
        assertEquals("Do right now1", task.getTask());
    }

}
