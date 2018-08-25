package com.mpoznyak.domain.commands.tasks;

import com.mpoznyak.domain.commands.GetItems;
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
