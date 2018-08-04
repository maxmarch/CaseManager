package com.mpoznyak.domain.interactor;

import com.mpoznyak.domain.model.Case;
import com.mpoznyak.domain.repository.Repository;
import com.mpoznyak.domain.repository.Specification;

import java.util.List;

public class GetCases implements Interactor<Case> {

    public List<Case> execute(Repository<Case> repository, Specification specification) {
        return repository.query(specification);
    }
}
