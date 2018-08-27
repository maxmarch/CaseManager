package com.mpoznyak.domain.command.types;

import com.mpoznyak.domain.command.RemoveItem;
import com.mpoznyak.domain.model.Type;
import com.mpoznyak.domain.repository.Repository;

public class RemoveType implements RemoveItem<Type> {

    @Override
    public void execute(Type item, Repository<Type> repository) {
        repository.remove(item);
    }
}
