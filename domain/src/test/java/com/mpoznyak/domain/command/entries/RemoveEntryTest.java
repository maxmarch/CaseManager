package com.mpoznyak.domain.command.entries;

import com.mpoznyak.domain.model.Entry;
import com.mpoznyak.domain.repository.Repository;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RemoveEntryTest {
    private final Entry mEntry = mock(Entry.class);
    private final RemoveEntry interactor = mock(RemoveEntry.class);
    private final Repository repository = mock(Repository.class);

    @Test
    public void testExecute() {
        interactor.execute(mEntry, repository);
        verify(interactor).execute(mEntry, repository);

    }
}