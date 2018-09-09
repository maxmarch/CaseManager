package com.mpoznyak.casemanager.util;

import java.util.Observable;

public class FragmentObserver extends Observable {

    @Override
    public void notifyObservers() {
        setChanged();
        super.notifyObservers();
    }
}
