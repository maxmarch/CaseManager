package com.mpoznyak.domain.interactor.tasks;

import com.mpoznyak.domain.interactor.GetItemsInteractor;
import com.mpoznyak.domain.model.Task;
import com.mpoznyak.domain.repository.Repository;
import com.mpoznyak.domain.repository.Specification;

import java.util.List;

public class GetTasksInteractor implements GetItemsInteractor<Task> {

    @Override
    public List<Task> execute(Repository<Task> repository, Specification specification) {
        return repository.query(specification);
    }
}
