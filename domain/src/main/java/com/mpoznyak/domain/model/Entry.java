package com.mpoznyak.domain.model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Entry {

    protected int mId;
    private File mPath;
    protected int mCase_Id;
    protected String mName;
    protected long mSize;
    protected String mLastModified;

    public Entry() {
    }

    public File getPath() {
        return mPath;
    }

    public int getCase_Id() {
        return mCase_Id;
    }

    public void setCaseId(int case_Id) {
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

    public void setPath(File path) {
        mPath = path;
    }


    public String getName() {
        return mPath.getName();
    }

    public long getSize() {
        return mPath.length() / 1024;
    }


    public String getLastModified() {
        long millis = mPath.lastModified();
        SimpleDateFormat pattern = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        pattern.setTimeZone(TimeZone.getDefault());
        mLastModified = pattern.format(millis);
        return mLastModified;
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
