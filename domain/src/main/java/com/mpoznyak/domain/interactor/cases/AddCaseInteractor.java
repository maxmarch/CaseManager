package com.mpoznyak.domain.interactor.cases;

import com.mpoznyak.domain.interactor.AddItemInteractor;
import com.mpoznyak.domain.model.Case;
import com.mpoznyak.domain.repository.Repository;

public class AddCaseInteractor implements AddItemInteractor<Case> {

    @Override
    public void execute(Case aCase, Repository<Case> repository) {
        repository.add(aCase);
    }

}
