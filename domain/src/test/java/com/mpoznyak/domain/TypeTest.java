package com.mpoznyak.domain;

import com.mpoznyak.domain.model.Case;
import com.mpoznyak.domain.model.Type;

import org.junit.Test;
import static org.mockito.Mockito.*;

public class TypeTest {

    Type type = mock(Type.class);

    @Test
    public void shouldReturnListOfCases() {
        type.getCases();
        verify(type).getCases();
    }

    @Test
    public void shouldReturnNameOfType() {
        type.getName();
        verify(type).getName();
    }

}
