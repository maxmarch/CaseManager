package com.mpoznyak.domain.interactor.types;

import com.mpoznyak.domain.interactor.AddItemInteractor;
import com.mpoznyak.domain.model.Type;
import com.mpoznyak.domain.repository.Repository;

public class AddTypeInteractor implements AddItemInteractor<Type> {

    @Override
    public void execute(Type item, Repository<Type> repository) {
        repository.add(item);
    }
}
