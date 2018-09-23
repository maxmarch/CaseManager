package com.mpoznyak.domain.command.entries;

import com.mpoznyak.domain.repository.Repository;
import com.mpoznyak.domain.repository.Specification;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GetEntriesTest {
    private final GetEntries interactor = mock(GetEntries.class);
    private final Specification specification = mock(Specification.class);
    private final Repository repository = mock(Repository.class);

    @Test
    public void testExecute() {
        interactor.execute(repository, specification);
        verify(interactor).execute(repository, specification);

    }
}