package com.mpoznyak.domain.command;

import com.mpoznyak.domain.repository.Repository;

public interface RemoveItem<T> {

    void execute(T item, Repository<T> repository);
}
