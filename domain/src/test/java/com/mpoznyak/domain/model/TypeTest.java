package com.mpoznyak.domain.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TypeTest {

    private final Type type = new Type();

    @Before
    public void initTaskInstance() {
        type.setId(1);
        type.setName("Type1");
    }

    @Test
    public void testGetters() {
        assertEquals(1, type.getId());
        assertEquals("Type1", type.getName());
    }

    @Test
    public void testSetters() {
        type.setId(2);
        type.setName("Type2");
        assertEquals(2, type.getId());
        assertEquals("Type2", type.getName());
    }
}
