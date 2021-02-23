package com.zhuravlov.db.Dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zhuravlov.controller.command.CommandUtility;
import com.zhuravlov.db.DbUtil;
import com.zhuravlov.model.builder.UserEntityBuilder;
import com.zhuravlov.model.entity.Role;
import com.zhuravlov.model.entity.UserEntity;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserDaoImplTest {
    private static UserDaoImpl dao;

    @BeforeClass
    public static void setup() throws SQLException {
        dao = new UserDaoImpl();
        TestUtil.initH2();

        Connection connection = DbUtil.getConnection();
        Statement statement = connection.createStatement();
        try {
            TestUtil.executeDBScripts("sql\\schema.sql", statement);
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void create() {
        UserEntity userEntity = TestUtil.getUser();
        UserEntity userEntityByDao = dao.create(userEntity);
        assertEquals(userEntity, userEntityByDao);

    }

    @Test
    public void findByEmail() {
        UserEntity userEntity = TestUtil.getUser();
        UserEntity userEntityByDao1 = dao.create(userEntity);
        assertEquals("first", dao.findByEmail("email@email.com").getFirstName());
    }

    @Test
    public void findById() {
        UserEntity userEntity = TestUtil.getUser();
        UserEntity userEntityByDao1 = dao.create(userEntity);
        assertEquals(userEntityByDao1, dao.findById(1));
    }

    @Test
    public void findAll() {
        UserEntity userEntity1 = TestUtil.getUser();
        UserEntity userEntity2 = TestUtil.getUser();
        userEntity2.setEmail("user1@emal.com");
        UserEntity userEntityByDao1 = dao.create(userEntity1);
        UserEntity userEntityByDao2 = dao.create(userEntity2);
        List<UserEntity> all = Arrays.asList(userEntityByDao1, userEntityByDao2);
        assertEquals(all, dao.findAll());
    }

    @Test
    public void update() {
        UserEntity userEntity1 = TestUtil.getUser();
        userEntity1.setEmail("user42@email.com");
        UserEntity userEntity = dao.create(userEntity1);
        assertEquals(userEntity, dao.update(userEntity));
    }

    @Test
    public void delete() {
        UserEntity userEntity1 = TestUtil.getUser();
        UserEntity userEntityByDao1 = dao.create(userEntity1);
        assertTrue(dao.delete(1));
    }

    @Test
    public void findRepairmans() {
        assertEquals(0, dao.findRepairmans().size());
    }
}