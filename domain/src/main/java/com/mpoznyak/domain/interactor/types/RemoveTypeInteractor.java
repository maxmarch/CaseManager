package com.mpoznyak.domain.interactor.types;

import com.mpoznyak.domain.interactor.RemoveItemInteractor;
import com.mpoznyak.domain.model.Type;
import com.mpoznyak.domain.repository.Repository;

public class RemoveTypeInteractor implements RemoveItemInteractor<Type> {

    @Override
    public void execute(Type item, Repository<Type> repository) {
        repository.remove(item);
    }
}
