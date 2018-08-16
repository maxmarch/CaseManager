package com.mpoznyak.data.query.cases.specification;

import com.mpoznyak.data.query.cases.AllTypesSpecification;
import org.junit.Test;

import static org.junit.Assert.*;


public class AllTypesSpecificationTest {

    @Test
    public void testToSqlQuery() {
        String query = AllTypesSpecification.toSqlQuery();
        String expectedQuery = "SELECT DISTINCT type FROM cases;";
        assertEquals(expectedQuery, query);
    }


}