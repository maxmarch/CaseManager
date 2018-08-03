package com.mpoznyak.domain;

import com.mpoznyak.domain.model.Todo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


public class TodoTest {

    private Todo testTodo;
    private String task = "Visit the dantist";


    @Test
    public void shouldReturnTaskList() {
        testTodo = mock(Todo.class);
        List<String> expected = testTodo.getTasks();
        when(testTodo.getTasks()).thenReturn(expected);
        verify(testTodo).getTasks();
        assertEquals(expected, testTodo.getTasks());
    }

    @Test
    public void testIfTaskIsAddedToList() {
        testTodo = new Todo();
        int checkListSize = testTodo.getTasks().size();
        testTodo.addTask(task);
        assertEquals("List size should increase by 1", true,
                testTodo.getTasks().size() == checkListSize + 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentWhenCallAddTask() {
        testTodo = new Todo();
        testTodo.addTask("");
    }

    @Test
    public void testRemoveTask() {
        testTodo = new Todo();
        String task = "Hey";
        testTodo.addTask(task);
        assertEquals("Should return task", task, testTodo.removeTask(0));
    }
}
