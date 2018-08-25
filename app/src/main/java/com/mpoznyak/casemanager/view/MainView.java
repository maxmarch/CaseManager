package com.mpoznyak.casemanager.view;

import com.mpoznyak.domain.model.Case;
import com.mpoznyak.domain.model.Type;

import java.util.List;


public interface MainView {

    void onButtonAddCaseClick();

    void onButtonAddTypeClick();

    List<Type> loadTypes();

    List<Case> loadCases();


}
