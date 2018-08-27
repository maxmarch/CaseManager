package com.mpoznyak.domain.command;

import com.mpoznyak.domain.repository.Repository;

public interface AddItem<T> {

    void execute(T item, Repository<T> repository);
}
