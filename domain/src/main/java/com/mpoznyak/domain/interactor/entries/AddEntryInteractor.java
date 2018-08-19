package com.mpoznyak.domain.interactor.entries;

import com.mpoznyak.domain.interactor.AddItemInteractor;
import com.mpoznyak.domain.model.Entry;
import com.mpoznyak.domain.repository.Repository;

public class AddEntryInteractor implements AddItemInteractor<Entry> {

    @Override
    public void execute(Entry item, Repository<Entry> repository) {
        repository.add(item);
    }
}
