package com.zhuravlov.db.Dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zhuravlov.controller.command.CommandUtility;
import com.zhuravlov.db.DbUtil;
import com.zhuravlov.model.builder.RepairFormBuilder;
import com.zhuravlov.model.builder.UserEntityBuilder;
import com.zhuravlov.model.entity.RepairFormEntity;
import com.zhuravlov.model.entity.Role;
import com.zhuravlov.model.entity.Status;
import com.zhuravlov.model.entity.UserEntity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;

public class TestUtil {
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

    public static UserEntity getUser() {
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

    public static RepairFormEntity getRepairForm() {
        return RepairFormBuilder.getInstance()
                .setStatus(Status.NEW)
                .setDescription("descr")
                .setShortDescription("sh_descr")
                .setCar("car")
                .setCreationDate(LocalDateTime.now())
                .build();
    }
}
