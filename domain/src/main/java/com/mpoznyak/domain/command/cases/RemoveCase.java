package com.mpoznyak.domain.command.cases;

import com.mpoznyak.domain.command.RemoveItem;
import com.mpoznyak.domain.model.Case;
import com.mpoznyak.domain.repository.Repository;

public class RemoveCase implements RemoveItem<Case> {

    @Override
    public void execute(Case aCase, Repository<Case> repository) {
        repository.remove(aCase);
    }

}
