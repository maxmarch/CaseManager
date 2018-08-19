package com.mpoznyak.domain.interactor.types;

import com.mpoznyak.domain.interactor.GetItemsInteractor;
import com.mpoznyak.domain.model.Type;
import com.mpoznyak.domain.repository.Repository;
import com.mpoznyak.domain.repository.Specification;

import java.util.List;

public class GetTypesInteractor implements GetItemsInteractor<Type> {

    @Override
    public List<Type> execute(Repository<Type> repository, Specification specification) {
        return repository.query(specification);
    }
}
