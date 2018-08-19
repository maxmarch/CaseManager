package com.mpoznyak.domain.interactor.cases;

import com.mpoznyak.domain.interactor.RemoveItemInteractor;
import com.mpoznyak.domain.model.Case;
import com.mpoznyak.domain.repository.Repository;

public class RemoveCaseInteractor implements RemoveItemInteractor<Case> {

    @Override
    public void execute(Case aCase, Repository<Case> repository) {
        repository.remove(aCase);
    }

}
