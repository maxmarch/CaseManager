package com.mpoznyak.domain.interactor;

import com.mpoznyak.domain.repository.Repository;

public interface AddItemInteractor<T> {

    void execute(T item, Repository<T> repository);
}
