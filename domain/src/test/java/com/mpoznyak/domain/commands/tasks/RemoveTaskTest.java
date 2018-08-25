package com.mpoznyak.domain.commands.tasks;

import com.mpoznyak.domain.model.Task;
import com.mpoznyak.domain.repository.Repository;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RemoveTaskTest {
    Task task = mock(Task.class);
    RemoveTask interactor = mock(RemoveTask.class);
    Repository repository = mock(Repository.class);

    @Test
    public void testExecute() {
        interactor.execute(task, repository);
        verify(interactor).execute(task, repository);

    }
}