package com.mpoznyak.casemanager;

import android.os.Bundle;

import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class MainActivityTest {

    @Test
    public void reate() {
        MainActivity activity = mock(MainActivity.class);
        Bundle bundle = mock(Bundle.class);
        activity.onCreate(bundle);
    }
}