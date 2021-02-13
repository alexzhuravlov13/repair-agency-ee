package com.zhuravlov.db.Dao;

import java.util.List;

public interface Dao<T> {
    T create(T entity);

    T findById(int id);

    List<T> findAll();

    T update(T entity);

    boolean delete(int id);

    boolean updateRoles(T entity);
}
