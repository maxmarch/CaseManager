package com.mpoznyak.casemanager.interactor;

import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.data.repository.EntryRepository;
import com.mpoznyak.domain.model.Entry;

import java.io.File;

public class FileManagerInteractor {

    private DatabaseHelper mDatabaseHelper;
    private EntryRepository mEntryRepository;
    private int caseId;

    public FileManagerInteractor(DatabaseHelper db, int caseId) {
        this.caseId = caseId;
        mDatabaseHelper = db;
        mEntryRepository = new EntryRepository(db);
    }

    public void addDocument(File file) {
        Entry entry = new Entry();
        entry.setPath(file.getPath());
        entry.setCaseId(caseId);
        mEntryRepository.add(entry);
    }


}
