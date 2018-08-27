package com.mpoznyak.domain.command.tasks;

import com.mpoznyak.domain.model.Task;
import com.mpoznyak.domain.repository.Repository;
import com.mpoznyak.domain.repository.Specification;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AddTaskTest {

    Task task = mock(Task.class);
    AddTask interactor = mock(AddTask.class);
    Specification specification = mock(Specification.class);
    Repository repository = mock(Repository.class);

    @Test
    public void testExecute() {
        interactor.execute(task, repository);
        verify(interactor).execute(task, repository);

    }
}