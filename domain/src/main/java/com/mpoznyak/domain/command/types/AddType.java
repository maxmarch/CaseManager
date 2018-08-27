package com.mpoznyak.domain.command.types;

import com.mpoznyak.domain.command.AddItem;
import com.mpoznyak.domain.model.Type;
import com.mpoznyak.domain.repository.Repository;

public class AddType implements AddItem<Type> {

    @Override
    public void execute(Type item, Repository<Type> repository) {
        repository.add(item);
    }
}
