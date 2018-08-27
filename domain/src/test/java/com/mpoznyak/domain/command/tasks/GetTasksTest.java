package com.mpoznyak.domain.command.tasks;

import com.mpoznyak.domain.repository.Repository;
import com.mpoznyak.domain.repository.Specification;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GetTasksTest {

    GetTasks interactor = mock(GetTasks.class);
    Specification specification = mock(Specification.class);
    Repository repository = mock(Repository.class);

    @Test
    public void testExecute() {
        interactor.execute(repository, specification);
        verify(interactor).execute(repository, specification);

    }

}