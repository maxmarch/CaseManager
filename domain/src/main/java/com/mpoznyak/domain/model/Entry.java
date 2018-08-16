package com.mpoznyak.domain.model;

import java.io.File;
import java.text.SimpleDateFormat;

public class Entry {

    private int mId;
    private File mPath;
    private int mCase_Id;
    private String mName;
    private long mSize;
    private String mLastModified;

    public Entry(String path) {
        mPath = new File(path);
        setNameFromPath();
        mSize = mPath.length();
        setLastModifiedDate();
    }

    public File getPath() {
        return mPath;
    }

    public int getCase_Id() {
        return mCase_Id;
    }

    public void setCase_Id(int case_Id) {
        mCase_Id = case_Id;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setPath(String path) {
        mPath = new File(path);
    }

    public String getName() {
        return mName;
    }

    private void setNameFromPath() {
        mName = mPath.getName();
    }

    public long getSize() {
        return mSize;
    }

    public String getLastModified() {
        return mLastModified;
    }

    private void setLastModifiedDate() {
        long millis = mPath.lastModified();
        SimpleDateFormat pattern = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        mLastModified = pattern.format(millis);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Entry)) {
            return false;
        }
        Entry entry = (Entry) o;
        return this.mPath.toString().equals(entry.mPath.toString());
    }


}
