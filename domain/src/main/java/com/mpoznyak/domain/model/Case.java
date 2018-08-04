package com.mpoznyak.domain.model;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
//TODO switch to jdk 7 DATE
public class Case implements Comparable<Case>{

    private String mName;
    private final Date mCreationDate;
    private final Todo mTodo;
    private List<Entry> mEntries;

    public Case() {
        mCreationDate = new Date();
        mTodo = new Todo();
        mEntries = new ArrayList<>();
    }

    public String getName() throws NullPointerException{
        if (mName == null) {
            throw new NullPointerException("mName has null reference");
        }
        return this.mName;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (name == null || name.equals("") || name.equals(" ")){
            throw new IllegalArgumentException("name should have meaningful name");
        }
        this.mName = name;
    }

    public void addEntry(Entry entry) {
        mEntries.add(entry);
    }

    public List<Entry> getEntries() {
        return mEntries;
    }

    public Date getCreationDate() {
        return mCreationDate;
    }

    public Todo getTodo() {
        return mTodo;
    }

    @Override
    public int compareTo(Case c) {
        if(this.mCreationDate.after(c.mCreationDate)) {
            return 1;
        } else if (c.mCreationDate.after(this.mCreationDate)){
            return -1;
        }
        return 0;
    }


}
