package com.zhuravlov.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zhuravlov.controller.Servlet;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class DbUtil {
    private final static Logger log = Logger.getLogger(DbUtil.class);

    //TODO:from properties
    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/repair_service_db?useUnicode=true&serverTimezone=UTC&useSSL=false";

    private static HikariDataSource dataSource;

    public static void init() {
        log.info("Db file path :" + DB_URL);

        HikariConfig config = new HikariConfig();
        config.setPoolName("MySqlPool");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl(DB_URL);
        config.setUsername("root");
        config.setPassword("admin");
        config.setMaximumPoolSize(1); // 50 Connections (including idle connections)
        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() {
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            log.error(e.getStackTrace());
        }

        return connection;
    }

}
