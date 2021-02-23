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

    private UserEntity getUser() {
        String email = "email@email.com";
        String password = "password";
        return UserEntityBuilder.getInstance()
                .setFirstName("first")
                .setLastName("last")
                .setEmail(email)
                .setPassword(CommandUtility.hashPass(password, email))
                .setRoles(new HashSet<>(Collections.singletonList(Role.USER)))
                .build();
    }

    @BeforeClass
    public static void setup() throws SQLException {
        dao = new UserDaoImpl();
        initH2();

        Connection connection = DbUtil.getConnection();
        Statement statement = connection.createStatement();
        try {
            executeDBScripts("sql\\schema.sql", statement);
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void create() {
        UserEntity userEntity = getUser();
        UserEntity userEntityByDao = dao.create(userEntity);
        assertEquals(userEntity, userEntityByDao);

    }

    @Test
    public void findByEmail() {
        UserEntity userEntity = getUser();
        UserEntity userEntityByDao1 = dao.create(userEntity);
        assertEquals("first", dao.findByEmail("email@email.com").getFirstName());
    }

    @Test
    public void findById() {
        UserEntity userEntity = getUser();
        UserEntity userEntityByDao1 = dao.create(userEntity);
        assertEquals(userEntityByDao1, dao.findById(1));
    }

    @Test
    public void findAll() {
        UserEntity userEntity1 = getUser();
        UserEntity userEntity2 = getUser();
        userEntity2.setEmail("user1@emal.com");
        UserEntity userEntityByDao1 = dao.create(userEntity1);
        UserEntity userEntityByDao2 = dao.create(userEntity2);
        List<UserEntity> all = Arrays.asList(userEntityByDao1, userEntityByDao2);
        assertEquals(all, dao.findAll());
    }

    @Test
    public void update() {
        UserEntity userEntity1 = getUser();
        userEntity1.setEmail("user42@email.com");
        UserEntity userEntity = dao.create(userEntity1);
        assertEquals(userEntity, dao.update(userEntity));
    }

    @Test
    public void delete() {
        UserEntity userEntity1 = getUser();
        UserEntity userEntityByDao1 = dao.create(userEntity1);
        assertTrue(dao.delete(1));
    }

    @Test
    public void findRepairmans() {
        assertEquals(0, dao.findRepairmans().size());
    }

    public static boolean executeDBScripts(String aSQLScriptFilePath, Statement stmt) throws IOException, SQLException {
        boolean isScriptExecuted = false;
        try {
            BufferedReader in = new BufferedReader(new FileReader(aSQLScriptFilePath));
            String str;
            StringBuilder sb = new StringBuilder();
            while ((str = in.readLine()) != null) {
                sb.append(str).append("\n ");
            }
            in.close();
            stmt.executeUpdate(sb.toString());
            isScriptExecuted = true;
        } catch (Exception e) {
            System.err.println("Failed to Execute" + aSQLScriptFilePath + ". The error is" + e.getMessage());
        } finally {
            stmt.close();
        }
        return isScriptExecuted;
    }

    public static void initH2() {
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
        config.setConnectionTestQuery("VALUES 1");
        config.addDataSourceProperty("URL", "jdbc:h2:~/test");
        config.addDataSourceProperty("user", "sa");
        config.addDataSourceProperty("password", "");
        HikariDataSource ds = new HikariDataSource(config);
        DbUtil.setDataSource(ds);
    }
}