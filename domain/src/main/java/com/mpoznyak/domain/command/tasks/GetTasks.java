package com.mpoznyak.domain.command.tasks;

import com.mpoznyak.domain.command.GetItems;
import com.mpoznyak.domain.model.Task;
import com.mpoznyak.domain.repository.Repository;
import com.mpoznyak.domain.repository.Specification;

import java.util.List;

public class GetTasks implements GetItems<Task> {

    @Override
    public List<Task> execute(Repository<Task> repository, Specification specification) {
        return repository.query(specification);
    }
}
