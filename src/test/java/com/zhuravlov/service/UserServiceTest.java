package com.zhuravlov.service;

import com.zaxxer.hikari.HikariDataSource;
import com.zhuravlov.db.Dao.UserDaoImpl;
import com.zhuravlov.model.builder.UserEntityBuilder;
import com.zhuravlov.model.entity.UserEntity;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private UserService service;
    private UserDaoImpl mockDao;
    private Connection mockConn;
    private HikariDataSource mockDatasource;
    private PreparedStatement mockPs;
    private ResultSet mockResultSet;


    @Before
    public void init() {
        mockDao = mock(UserDaoImpl.class);
        service = new UserService(mockDao);
    }

    @Test
    public void create() {
        UserEntity userEntity = UserEntityBuilder.getInstance().build();
        when(mockDao.create(any(UserEntity.class))).thenReturn(userEntity);

    }

    @Test
    public void findById() {
        UserEntity userEntity = UserEntityBuilder.getInstance().build();
        when(mockDao.findById(anyInt())).thenReturn(userEntity);
        assertEquals(userEntity, service.findById(1));
    }

    @Test
    public void findAll() {
        List<UserEntity> all = Arrays.asList(new UserEntity(), new UserEntity());
        when(mockDao.findAll()).thenReturn(all);
        assertEquals(all, service.findAll());
    }

    @Test
    public void update() {
        UserEntity userEntity = UserEntityBuilder.getInstance().build();
        when(mockDao.update(any(UserEntity.class))).thenReturn(userEntity);
        assertEquals(userEntity, service.update(userEntity));
    }

    @Test
    public void delete() {
        when(mockDao.delete(anyInt())).thenReturn(true);
        assertTrue(service.delete(1));
    }

    @Test
    public void findByEmail() {
        UserEntity userEntity = UserEntityBuilder.getInstance().build();
        when(mockDao.findByEmail(anyString())).thenReturn(userEntity);
        assertEquals(userEntity, service.findByEmail("any"));
    }

    @Test
    public void findRepairmans() {
        List<UserEntity> all = Arrays.asList(new UserEntity(), new UserEntity());
        when(mockDao.findRepairmans()).thenReturn(all);
        assertEquals(all, service.findRepairmans());
    }

    @Test
    public void getInstance() {
        assertNotNull(UserService.getInstance());
    }

    @Test
    public void registerUser() {
        UserEntity userEntity = UserEntityBuilder.getInstance().setFirstName("").setLastName("").setPassword("1").setEmail("2")
        .build();
        when(mockDao.create(any(UserEntity.class))).thenReturn(userEntity);
        assertEquals(userEntity, service.registerUser("", "", "1", "2"));

    }
}