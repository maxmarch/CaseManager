package com.mpoznyak.domain.commands.entries;

import com.mpoznyak.domain.commands.GetItems;
import com.mpoznyak.domain.model.Entry;
import com.mpoznyak.domain.repository.Repository;
import com.mpoznyak.domain.repository.Specification;

import java.util.List;

public class GetEntries implements GetItems<Entry> {

    @Override
    public List<Entry> execute(Repository<Entry> repository, Specification specification) {
        return repository.query(specification);
    }
}
