package com.mpoznyak.casemanager.presenter;

import com.mpoznyak.domain.model.Type;

public interface NewTypePresenter {

    void saveType(String name, String color);

    void updateType(Type type);
}
