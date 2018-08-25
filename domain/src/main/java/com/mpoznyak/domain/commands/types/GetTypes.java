package com.mpoznyak.domain.commands.types;

import com.mpoznyak.domain.commands.GetItems;
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
