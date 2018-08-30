package com.mpoznyak.casemanager.presenter;

import com.mpoznyak.domain.model.Case;
import com.mpoznyak.domain.model.Type;

import java.util.List;

public interface MainPresenter {

    boolean typeDataisEmpty();

    List<Type> loadTypes();

    List<Case> loadCasesByLastOpenedType();

    List<Case> loadCasesBySelectedType(String type);

    String loadNameForLastOpenedType();

    void updateType(Type type);

}
