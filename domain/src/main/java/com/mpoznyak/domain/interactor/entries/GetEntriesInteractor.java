package com.mpoznyak.domain.interactor.entries;

import com.mpoznyak.domain.interactor.GetItemsInteractor;
import com.mpoznyak.domain.model.Entry;
import com.mpoznyak.domain.repository.Repository;
import com.mpoznyak.domain.repository.Specification;

import java.util.List;

public class GetEntriesInteractor implements GetItemsInteractor<Entry> {

    @Override
    public List<Entry> execute(Repository<Entry> repository, Specification specification) {
        return repository.query(specification);
    }
}
