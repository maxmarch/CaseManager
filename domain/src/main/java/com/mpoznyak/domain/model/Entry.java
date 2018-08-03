package com.mpoznyak.domain.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

public class Entry {

    private Path mPath;
    private String mName;
    private long mSize;
    private String mLastModified;

    public Entry(String path) {
        mPath = Paths.get(path);
        setNameFromPath();
        mSize = mPath.toFile().length();
        setLastModifiedDate();
    }

    public Entry(Path path) {
        mPath = path;
        setNameFromPath();
    }

    public Path getPath() {
        return mPath;
    }

    public void setPath(Path path) {
        mPath = path;
    }

    public String getName() {
        return mName;
    }

    public void setNameFromPath() {
        mName = mPath.toFile().getName();
    }

    public long getSize() {
        return mSize;
    }

    public String getLastModified() {
        return mLastModified;
    }

    public void setLastModifiedDate() {
        long millis = mPath.toFile()
                .lastModified();
        SimpleDateFormat pattern = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        mLastModified = pattern.format(millis);
    }


}
