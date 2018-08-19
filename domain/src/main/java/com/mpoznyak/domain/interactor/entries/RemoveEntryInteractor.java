package com.mpoznyak.domain.interactor.entries;

import com.mpoznyak.domain.interactor.RemoveItemInteractor;
import com.mpoznyak.domain.model.Entry;
import com.mpoznyak.domain.repository.Repository;

public class RemoveEntryInteractor implements RemoveItemInteractor<Entry> {

    @Override
    public void execute(Entry item, Repository<Entry> repository) {
        repository.remove(item);
    }
}
