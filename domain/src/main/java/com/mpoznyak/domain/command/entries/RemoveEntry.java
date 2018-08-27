package com.mpoznyak.domain.command.entries;

import com.mpoznyak.domain.command.RemoveItem;
import com.mpoznyak.domain.model.Entry;
import com.mpoznyak.domain.repository.Repository;

public class RemoveEntry implements RemoveItem<Entry> {

    @Override
    public void execute(Entry item, Repository<Entry> repository) {
        repository.remove(item);
    }
}
