package com.mpoznyak.domain.command.entries;

import com.mpoznyak.domain.model.Entry;
import com.mpoznyak.domain.repository.Repository;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RemoveEntryTest {
    Entry mEntry = mock(Entry.class);
    RemoveEntry interactor = mock(RemoveEntry.class);
    Repository repository = mock(Repository.class);

    @Test
    public void testExecute() {
        interactor.execute(mEntry, repository);
        verify(interactor).execute(mEntry, repository);

    }
}