package com.mpoznyak.domain.interactor.tasks;

import com.mpoznyak.domain.interactor.AddItemInteractor;
import com.mpoznyak.domain.model.Task;
import com.mpoznyak.domain.repository.Repository;

public class AddTaskInteractor implements AddItemInteractor<Task> {

    @Override
    public void execute(Task item, Repository<Task> repository) {
        repository.add(item);
    }
}
