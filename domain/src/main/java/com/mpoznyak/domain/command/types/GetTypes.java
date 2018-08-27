package com.mpoznyak.domain.command.types;

import com.mpoznyak.domain.command.GetItems;
import com.mpoznyak.domain.model.Type;
import com.mpoznyak.domain.repository.Repository;
import com.mpoznyak.domain.repository.Specification;

import java.util.List;

public class GetTypes implements GetItems<Type> {

    @Override
    public List<Type> execute(Repository<Type> repository, Specification specification) {
        return repository.query(specification);
    }
}
