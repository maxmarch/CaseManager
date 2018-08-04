package com.mpoznyak.domain.interactor;


import com.mpoznyak.domain.repository.Repository;
import com.mpoznyak.domain.repository.Specification;

import java.util.List;

public interface Interactor<T> {

     List<T> execute(Repository<T> repository, Specification specification);
}
