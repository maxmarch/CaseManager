package com.mpoznyak.data.specification.tasks;

import com.mpoznyak.domain.repository.Specification;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.TABLE_TASKS;

public class AllTasksSpecification implements Specification {

    public String toSqlQuery() {
        return "SELECT * " + " FROM " + TABLE_TASKS + ";";
    }
}
