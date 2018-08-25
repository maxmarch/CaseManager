package com.mpoznyak.domain.commands.entries;

import com.mpoznyak.domain.commands.AddItem;
import com.mpoznyak.domain.model.Entry;
import com.mpoznyak.domain.repository.Repository;

public class AddEntry implements AddItem<Entry> {

    @Override
    public void execute(Entry item, Repository<Entry> repository) {
        repository.add(item);
    }
}
