package com.zhuravlov.service;

import com.zhuravlov.db.Dao.UserDaoImpl;
import com.zhuravlov.model.entity.UserEntity;

import java.util.List;

public class UserService {
    private UserDaoImpl dao;

    public UserService() {
        dao = new UserDaoImpl();
    }

    public UserService(UserDaoImpl dao) {
        this.dao = dao;
    }

    public UserEntity create(UserEntity entity) {
        return dao.create(entity);
    }

    public UserEntity findById(int id) {
        return dao.findById(id);
    }

    public List<UserEntity> findAll() {
        return dao.findAll();
    }

    public UserEntity update(UserEntity entity) {
        dao.update(entity);
        return entity;
    }

    public boolean delete(int id) {
        return dao.delete(id);
    }

    public UserEntity findByEmail(String email) {
        return dao.findByEmail(email);
    }

    public List<UserEntity> findRepairmans() {
        return dao.findRepairmans();
    }
}
