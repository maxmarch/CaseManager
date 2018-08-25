package com.mpoznyak.casemanager.presenters;

public interface MainPresenter {

    void checkIfTypeDataisEmpty();

    void onButtonAddCaseClick();

    void onButtonAddTypeClick();

    void loadTypes();

    void loadCases();

    void onDestroy();

}
