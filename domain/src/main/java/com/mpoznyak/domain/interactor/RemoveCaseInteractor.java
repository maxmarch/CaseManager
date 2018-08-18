package com.mpoznyak.domain.interactor;

import com.mpoznyak.domain.model.Case;
import com.mpoznyak.domain.repository.Repository;

public class RemoveCaseInteractor {

    void remove(Case aCase, Repository<Case> repository) {
        repository.remove(aCase);
    }

}
