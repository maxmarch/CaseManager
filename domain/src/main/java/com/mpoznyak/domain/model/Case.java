package com.mpoznyak.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Case implements Comparable<Case>{

    private String mTitle;
    private final LocalDate mCreationDate;
    private final Todo mTodo;
    private List<Entry> mEntries;

    public Case() {
        mCreationDate = LocalDate.now();
        mTodo = new Todo();
        mEntries = new ArrayList<>();
    }

    public String getTitle() throws NullPointerException{
        if (mTitle == null) {
            throw new NullPointerException("mTitle has null reference");
        }
        return this.mTitle;
    }

    public void setTitle(String title) throws IllegalArgumentException {
        if (title == null || title.equals("") || title.equals(" ")){
            throw new IllegalArgumentException("title should have meaningful name");
        }
        this.mTitle = title;
    }

    public void addEntry(Entry entry) {
        mEntries.add(entry);
    }

    public List<Entry> getEntries() {
        return mEntries;
    }

    public LocalDate getCreationDate() {
        return mCreationDate;
    }

    public Todo getTodo() {
        return mTodo;
    }

    @Override
    public int compareTo(Case c) {
        if(this.mCreationDate.isAfter(c.mCreationDate)) {
            return 1;
        } else if (c.mCreationDate.isAfter(this.mCreationDate)){
            return -1;
        }
        return 0;
    }


}
