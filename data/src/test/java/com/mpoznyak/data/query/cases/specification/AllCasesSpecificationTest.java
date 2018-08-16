package com.mpoznyak.data.query.cases.specification;

import com.mpoznyak.data.query.cases.AllCasesSpecification;

import org.junit.Test;

import static org.junit.Assert.*;

public class AllCasesSpecificationTest {

    @Test
    public void testToSqlQuery() {
        String query = AllCasesSpecification.toSqlQuery();
        String expectedQuery = "SELECT DISTINCT id, name, type, creation_date, path, task" +
                " FROM cases INNER JOIN entries ON cases.id = entries.case_id" +
                " INNER JOIN todos ON cases.id = todos.case_id;";
        assertEquals(expectedQuery, query);
    }

}