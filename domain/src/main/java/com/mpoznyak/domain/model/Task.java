package com.mpoznyak.domain.model;

public class Task {

    private int mId;
    private int mCaseId;
    private String mTask;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public void setTask(String task) {
        mTask = task;
    }

    public int getCaseId() {
        return mCaseId;
    }

    public void setCaseId(int caseId) {
        mCaseId = caseId;
    }

    public String getTask() {
        return mTask;
    }


}
