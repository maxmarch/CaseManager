package com.mpoznyak.domain.interactor.tasks;

import com.mpoznyak.domain.interactor.RemoveItemInteractor;
import com.mpoznyak.domain.model.Task;
import com.mpoznyak.domain.repository.Repository;

public class RemoveTaskInteractor implements RemoveItemInteractor<Task> {
    @Override
    public void execute(Task item, Repository<Task> repository) {
        repository.remove(item);
    }
}
