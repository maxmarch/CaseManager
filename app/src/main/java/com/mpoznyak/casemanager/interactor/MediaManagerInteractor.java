package com.mpoznyak.casemanager.interactor;

import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.data.repository.EntryRepository;
import com.mpoznyak.domain.model.Entry;

import java.io.File;

public class MediaManagerInteractor {

    private DatabaseHelper mDatabaseHelper;
    private EntryRepository mEntryRepository;
    private int caseId;

    public MediaManagerInteractor(DatabaseHelper db, int caseId) {
        this.caseId = caseId;
        mDatabaseHelper = db;
        mEntryRepository = new EntryRepository(db);
    }

    public void addPhoto(File file) {
        Entry entry = new Entry();
        entry.setPath(file.getPath());
        entry.setCaseId(caseId);
        mEntryRepository.add(entry);
    }


}
