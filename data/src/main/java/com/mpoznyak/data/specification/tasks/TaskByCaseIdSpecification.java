package com.mpoznyak.data.specification.tasks;

import com.mpoznyak.domain.repository.Specification;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.TABLE_TASKS;

public class TaskByCaseIdSpecification implements Specification {

    private int caseId;

    public TaskByCaseIdSpecification(int id) {
        caseId = id;
    }

    @Override
    public String toSqlQuery() {
        return "SELECT * FROM " + TABLE_TASKS + " WHERE case_id = " + caseId + ";";
    }
}
