package com.mpoznyak.domain.interactor.cases;

import com.mpoznyak.domain.interactor.GetItemsInteractor;
import com.mpoznyak.domain.model.Case;
import com.mpoznyak.domain.repository.Repository;
import com.mpoznyak.domain.repository.Specification;

import java.util.List;

public class GetCasesInteractor implements GetItemsInteractor<Case> {

    @Override
    public List<Case> execute(Repository<Case> repository, Specification specification) {
        return repository.query(specification);
    }
}
