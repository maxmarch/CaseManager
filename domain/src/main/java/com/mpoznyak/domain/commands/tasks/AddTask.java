package com.mpoznyak.domain.commands.tasks;

import com.mpoznyak.domain.commands.AddItem;
import com.mpoznyak.domain.model.Task;
import com.mpoznyak.domain.repository.Repository;

public class AddTask implements AddItem<Task> {

    @Override
    public void execute(Task item, Repository<Task> repository) {
        repository.add(item);
    }
}
