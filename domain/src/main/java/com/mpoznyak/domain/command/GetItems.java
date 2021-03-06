package com.mpoznyak.domain.command;

import com.mpoznyak.domain.repository.Repository;
import com.mpoznyak.domain.repository.Specification;

import java.util.List;

public interface GetItems<T> {

    List<T> execute(Repository<T> repository, Specification specification);
}
