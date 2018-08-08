package com.mpoznyak.data.specification.cases;

import com.mpoznyak.domain.repository.Specification;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.*;

public class GetCase implements Specification {

    private static final String baseQuery = "SELECT DISTINCT " +  COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_TYPE + ", "
            + ", " + COLUMN_CREATION_DATE + ", " + COLUMN_PATH + ", " + COLUMN_TASK + " "
            + "FROM " + TABLE_CASES + " INNER JOIN " + TABLE_ENTRIES + " ON " + TABLE_CASES + "." + COLUMN_ID
            + " = " + TABLE_ENTRIES + "." + COLUMN_CASE_ID
            + " INNER JOIN " + TABLE_TODOS + " ON " + TABLE_CASES + "." + COLUMN_ID + " = "
            + TABLE_TODOS + "." + COLUMN_CASE_ID + " ";
    public static String tempQuery;

    public static void withId(int id) throws IllegalStateException{
        if (tempQuery != "") {
            throw new IllegalStateException("GetCase class can call " +
                    "only one condition method followed by execute()");
        }
        tempQuery = baseQuery + " WHERE " + TABLE_CASES + "." + COLUMN_ID + " = " + id + ";";
    }

    public static void ofType(String type) throws IllegalStateException{
        if (tempQuery != "") {
            throw new IllegalStateException("GetCase class can call " +
                    "only one condition method followed by execute()");
        }
        tempQuery = baseQuery + " WHERE " + TABLE_CASES + "." + COLUMN_TYPE + " = " + type + ";";
    }

    public static String execute() {
        String requiredQuery = tempQuery;
        tempQuery = "";
        return requiredQuery;
    }
}
