package com.mpoznyak.casemanager;

import android.os.Bundle;

import com.mpoznyak.casemanager.view.MainActivity;

import org.junit.Test;

import static org.mockito.Mockito.mock;

public class MainActivityTest {

    @Test
    public void create() {
        MainActivity activity = mock(MainActivity.class);
        Bundle bundle = mock(Bundle.class);
    }
}