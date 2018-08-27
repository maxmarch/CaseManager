package com.mpoznyak.domain.command.cases;

import com.mpoznyak.domain.command.GetItems;
import com.mpoznyak.domain.model.Case;
import com.mpoznyak.domain.repository.Repository;
import com.mpoznyak.domain.repository.Specification;

import java.util.List;

public class GetCases implements GetItems<Case> {

    @Override
    public List<Case> execute(Repository<Case> repository, Specification specification) {
        return repository.query(specification);
    }

}
