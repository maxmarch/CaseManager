package com.mpoznyak.domain.interactor;

import com.mpoznyak.domain.model.Case;
import com.mpoznyak.domain.repository.Repository;
import com.mpoznyak.domain.repository.Specification;

import java.util.List;

public class GetCasesInteractor {

    List<Case> get(Repository<Case> repository, Specification specification) {
        return repository.query(specification);
    }
}
