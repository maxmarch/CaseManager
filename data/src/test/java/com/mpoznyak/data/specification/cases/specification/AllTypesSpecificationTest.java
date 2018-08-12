package com.mpoznyak.data.specification.cases.specification;

import com.mpoznyak.data.specification.cases.AllTypesSpecification;
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