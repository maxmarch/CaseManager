package com.mpoznyak.domain.repository;

import java.util.List;

public interface Repository<T> {

    void add(T item);

    void add(Iterable<T> item);

    void remove(T item);

    void update(T item);

    List<T> query(Specification spec);
}
