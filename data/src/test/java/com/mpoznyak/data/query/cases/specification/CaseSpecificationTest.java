package com.mpoznyak.data.query.cases.specification;

import com.mpoznyak.data.query.cases.CaseSpecification;

import org.junit.Test;

import static org.junit.Assert.*;

public class CaseSpecificationTest {


    @Test
    public void testWithId() {
        int case_id = 2;
        String query = CaseSpecification.withId(case_id);
        String expected = "SELECT DISTINCT id, name, type, creation_date, path, task" +
                " FROM cases INNER JOIN entries ON cases.id = entries.case_id" +
                " INNER JOIN todos ON cases.id = todos.case_id WHERE cases.id = " + case_id +";";
        assertEquals(expected, query);
    }

    @Test
    public void testToSqlQueryOfType() {
        String type = "type";
        String query = CaseSpecification.ofType(type);
        String expected = "SELECT DISTINCT id, name, type, creation_date, path, task" +
                " FROM cases INNER JOIN entries ON cases.id = entries.case_id" +
                " INNER JOIN todos ON cases.id = todos.case_id WHERE cases.type = " + type +";";
        assertEquals(expected, query);
    }

}