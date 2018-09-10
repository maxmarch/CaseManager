package com.mpoznyak.casemanager.interactor;

import com.mpoznyak.data.DatabaseHelper;
import com.mpoznyak.data.repository.EntryRepository;
import com.mpoznyak.data.specification.entries.EntriesByCaseIdSpecification;
import com.mpoznyak.data.wrapper.DocumentWrapper;
import com.mpoznyak.data.wrapper.PhotoWrapper;
import com.mpoznyak.domain.model.Entry;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CaseInteractor {

    private EntryRepository mEntryRepository;
    private DatabaseHelper mDatabaseHelper;

    public CaseInteractor(DatabaseHelper db) {
        mDatabaseHelper = db;
        mEntryRepository = new EntryRepository(mDatabaseHelper);
    }

    public ArrayList<DocumentWrapper> getDocumentsByCaseId(int caseId) {

        List<Entry> entries = mEntryRepository.query(new EntriesByCaseIdSpecification(caseId));
        return convertEntryToDocument(entries);

    }

    public ArrayList<PhotoWrapper> getPhotosByCaseId(int caseId) {
        List<Entry> entries = mEntryRepository.query(new EntriesByCaseIdSpecification(caseId));
        return getPhotosFromQuery(entries);
    }

    private ArrayList<PhotoWrapper> getPhotosFromQuery(List<Entry> entries) {
        ArrayList<PhotoWrapper> photos = new ArrayList<>();
        for (int i = 0; i < entries.size(); i++) {
            Entry entry = entries.get(i);
            if (entry.getPath().toString().endsWith(".jpg")
                    || entry.getPath().toString().endsWith(".png")) {
                PhotoWrapper photoWrapper = new PhotoWrapper();
                photoWrapper.setCaseId(entry.getCase_Id());
                photoWrapper.setPath(entry.getPath());
                photoWrapper.setId(entry.getId());
                photos.add(photoWrapper);
            }
        }

        return photos;
    }

    public void savePhoto(File file, int caseId) {
        Entry entry = new Entry();
        entry.setCaseId(caseId);
        entry.setPath(file.getPath());
        mEntryRepository.add(entry);
    }

    private ArrayList<DocumentWrapper> convertEntryToDocument(List<Entry> entries) {
        ArrayList<DocumentWrapper> docs = new ArrayList<>();
        for (Entry entry : entries) {
            if (entry.getPath().toString().endsWith(".doc")
                    || entry.getPath().toString().endsWith(".pdf")
                    || entry.getPath().toString().endsWith(".docx")
                    || entry.getPath().toString().endsWith(".xls")
                    || entry.getPath().toString().endsWith(".xlsx")) {
                DocumentWrapper doc = new DocumentWrapper();
                doc.setCaseId(entry.getCase_Id());
                doc.setId(entry.getId());
                doc.setPath(entry.getPath());
                docs.add(doc);
            }
        }
        return docs;
    }


}
