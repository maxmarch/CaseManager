package com.mpoznyak.domain.model;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

public class Entry {

    private File mPath;
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


}
