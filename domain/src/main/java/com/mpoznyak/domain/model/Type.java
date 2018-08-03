package com.mpoznyak.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Type {

    private String mName;
    private List<Case> mCases;

    public Type(String name) {
        mCases = new ArrayList<>();
        mName = name;
    }

    public List<Case> getCases() {
        return mCases;
    }

    public String getName() {
        return mName;
    }
}
