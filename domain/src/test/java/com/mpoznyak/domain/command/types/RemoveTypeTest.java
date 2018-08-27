package com.mpoznyak.domain.command.types;

import com.mpoznyak.domain.model.Type;
import com.mpoznyak.domain.repository.Repository;
import com.mpoznyak.domain.repository.Specification;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RemoveTypeTest {

    Type type = mock(Type.class);
    RemoveType interactor = mock(RemoveType.class);
    Specification specification = mock(Specification.class);
    Repository repository = mock(Repository.class);

    @Test
    public void testExecute() {
        interactor.execute(type, repository);
        verify(interactor).execute(type, repository);

    }

}