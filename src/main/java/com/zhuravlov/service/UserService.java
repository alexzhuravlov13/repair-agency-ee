package com.zhuravlov.service;

import com.zhuravlov.controller.command.CommandUtility;
import com.zhuravlov.db.Dao.UserDaoImpl;
import com.zhuravlov.model.builder.UserEntityBuilder;
import com.zhuravlov.model.entity.Role;
import com.zhuravlov.model.entity.UserEntity;

import java.util.Collections;
import java.util.HashSet;
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

    public static UserService getInstance() {
        return new UserService();
    }

    public UserEntity registerUser(String firstName, String lastName, String email, String password) {
        return dao.create(UserEntityBuilder.getInstance()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPassword(CommandUtility.hashPass(password, email))
                .setRoles(new HashSet<>(Collections.singletonList(Role.USER)))
                .build());
    }
}
