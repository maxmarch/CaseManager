package com.mpoznyak.domain.command.entries;

import com.mpoznyak.domain.command.AddItem;
import com.mpoznyak.domain.model.Entry;
import com.mpoznyak.domain.repository.Repository;

public class AddEntry implements AddItem<Entry> {

    @Override
    public void execute(Entry item, Repository<Entry> repository) {
        repository.add(item);
    }
}
