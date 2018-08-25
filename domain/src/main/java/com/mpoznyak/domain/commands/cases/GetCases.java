package com.mpoznyak.domain.commands.cases;

import com.mpoznyak.domain.commands.GetItems;
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
