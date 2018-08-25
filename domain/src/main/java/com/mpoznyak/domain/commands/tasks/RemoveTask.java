package com.mpoznyak.domain.commands.tasks;

import com.mpoznyak.domain.commands.RemoveItem;
import com.mpoznyak.domain.model.Task;
import com.mpoznyak.domain.repository.Repository;

public class RemoveTask implements RemoveItem<Task> {
    @Override
    public void execute(Task item, Repository<Task> repository) {
        repository.remove(item);
    }
}
