package com.mpoznyak.domain.model;

import java.util.List;

public class Type {

    private String mColor;
    private int mId;
    private String mName;
    private List<Case> mCases;
    private boolean isLastOpened;

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }

    public boolean isLastOpened() {
        return isLastOpened;
    }

    public void setLastOpened(boolean lastOpened) {
        isLastOpened = lastOpened;
    }

    public List<Case> getCases() {
        return mCases;
    }

    public void setCases(List<Case> cases) {
        mCases = cases;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
