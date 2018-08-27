package com.mpoznyak.domain.command.tasks;

import com.mpoznyak.domain.command.AddItem;
import com.mpoznyak.domain.model.Task;
import com.mpoznyak.domain.repository.Repository;

public class AddTask implements AddItem<Task> {

    @Override
    public void execute(Task item, Repository<Task> repository) {
        repository.add(item);
    }
}
