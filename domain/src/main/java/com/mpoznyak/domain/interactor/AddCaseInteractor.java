package com.mpoznyak.domain.interactor;

import com.mpoznyak.domain.model.Case;
import com.mpoznyak.domain.repository.Repository;

public class AddCaseInteractor {

    public void add(Case aCase, Repository<Case> repository) {
        repository.add(aCase);
    }

}
