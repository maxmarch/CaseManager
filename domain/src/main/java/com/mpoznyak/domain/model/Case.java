package com.mpoznyak.domain.model;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Case implements Comparable<Case>{

    private String mName;
    private Date mCreationDate;
    private Todo mTodo;
    private List<Entry> mEntries;
    private String mType;

    public Case() {
    }

    public String getType() {
        return mType;
    }

    public void setCreationDate(String date) throws ParseException{

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        mCreationDate = formatter.parse(date);
    }

    public void setType(String type) {
        mType = type;
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
        if (!mEntries.contains(entry)) {
            mEntries.add(entry);
        }
    }

    public List<Entry> getEntries() {
        return mEntries;
    }

    public void setEntries(List<Entry> e) {
        this.mEntries = e;
    }

    public Date getCreationDate() {
        return mCreationDate;
    }

    public Todo getTodo() {
        return mTodo;
    }

    public void setTodo(Todo todo) {
        mTodo = todo;
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
