package com.mpoznyak.domain.command.cases;

import com.mpoznyak.domain.model.Case;
import com.mpoznyak.domain.repository.Repository;
import com.mpoznyak.domain.repository.Specification;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AddCaseTest {

    private final Case mCase = mock(Case.class);
    private final AddCase interactor = mock(AddCase.class);
    Specification specification = mock(Specification.class);
    private final Repository repository = mock(Repository.class);

    @Test
    public void testExecute() {
        interactor.execute(mCase, repository);
        verify(interactor).execute(mCase, repository);

    }
}
