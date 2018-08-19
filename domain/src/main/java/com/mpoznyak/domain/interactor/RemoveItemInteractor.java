package com.mpoznyak.domain.interactor;

import com.mpoznyak.domain.repository.Repository;

public interface RemoveItemInteractor<T> {

    void execute(T item, Repository<T> repository);
}
