package com.mpoznyak.domain.model;

import java.io.File;
import java.text.SimpleDateFormat;

public class Entry {

    protected int mId;
    protected File mPath;
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

    public String getName() {
        return mName;
    }

    public void setNameFromPath() {
        mName = mPath.getName();
    }

    public long getSize() {
        return mSize;
    }

    public String getLastModified() {
        return mLastModified;
    }

    public void setLastModifiedDate() {
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
