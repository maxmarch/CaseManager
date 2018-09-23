package com.mpoznyak.document_assistant.util;

import android.net.Uri;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MediaManagerUtil {


    private File mCurrentDir;
    private File mPreviousDir;
    private Stack<File> mHistory;

    public MediaManagerUtil() {

        init();
    }


    private void init() {
        mHistory = new Stack<>();

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            mCurrentDir = new File(Environment.getExternalStorageDirectory()
                    + "/" + Environment.DIRECTORY_DCIM
                    + "/");
        }
    }

    public boolean hasPreviousDir() {
        return !mHistory.isEmpty();
    }

    public File getCurrentDir() {
        return mCurrentDir;
    }

    public void setCurrentDir(File currentDir) {
        mCurrentDir = currentDir;
    }


    public File getPreviousDir() {
        return mHistory.pop();
    }

    public void setPreviousDir(File mPreviousDir) {
        this.mPreviousDir = mPreviousDir;
        mHistory.add(mPreviousDir);

    }

    public List<File> getAllFiles(File f) {
        File[] allFiles = f.listFiles();

        List<File> dirs = new ArrayList<>();
        List<File> files = new ArrayList<>();

        for (File file : allFiles) {
            if (file.isDirectory())
                dirs.add(file);
            else
                files.add(file);
        }

        Collections.sort(dirs);
        Collections.sort(files);

        dirs.addAll(files);

        return dirs;

    }

    public String getMimeType(Uri uri) {
        String mimeType = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(uri.getPath());

        if (MimeTypeMap.getSingleton().hasExtension(extension)) {
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return mimeType;
    }
}





