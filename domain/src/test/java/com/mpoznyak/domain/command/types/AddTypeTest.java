package com.mpoznyak.domain.command.types;

import com.mpoznyak.domain.model.Type;
import com.mpoznyak.domain.repository.Repository;
import com.mpoznyak.domain.repository.Specification;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AddTypeTest {

    private final Type type = mock(Type.class);
    private final AddType interactor = mock(AddType.class);
    Specification specification = mock(Specification.class);
    private final Repository repository = mock(Repository.class);

    @Test
    public void testExecute() {
        interactor.execute(type, repository);
        verify(interactor).execute(type, repository);

    }
}