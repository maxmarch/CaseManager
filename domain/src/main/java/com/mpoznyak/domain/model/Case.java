package com.mpoznyak.domain.model;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Case implements Comparable<Case>{

    private int mId;
    private String mName;
    private String mCreationDate;
    private List<Task> mTasks;
    private List<Entry> mEntries;
    private String mType;

    public Case() {
        setCreationDate(new Date());
    }

    public String getType() {
        return mType;
    }

    public void setCreationDate(String date) {

        mCreationDate = date;

    }

    public void setCreationDate(Date date) {

        DateFormat formatter = DateFormat.getDateInstance();
        mCreationDate = formatter.format(date);
    }

    public void setType(String type) {
        mType = type;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
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

    public String getCreationDate() {
        return mCreationDate;
    }

    public List<Task> getTasks() {
        return mTasks;
    }

    public void setTasks(List<Task> tasks) {
        mTasks = tasks;
    }

    public void addTask(Task task) {
        mTasks.add(task);
    }

    @Override
    public int compareTo(Case c) {
        Date thisDate;
        Date anotherDate;
        try {
            thisDate = new SimpleDateFormat("dd.MM.yyyy").parse(this.mCreationDate);
            anotherDate = new SimpleDateFormat("dd.MM.yyyy").parse(c.mCreationDate);
            if (thisDate.after(anotherDate)) {
                return 1;
            } else if (anotherDate.after(thisDate)) {
                return -1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
