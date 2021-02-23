package com.zhuravlov.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {
    private final static Logger log = Logger.getLogger(DbUtil.class);

    //TODO:from properties
    private static String DB_URL;

    private static HikariDataSource dataSource;

    public static void init() {
        Properties appProps = new Properties();
        try {
            InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/app.properties");
            appProps.load(input);
        } catch (IOException e) {
            log.warn(e.getMessage());
        }
        DB_URL = appProps.getProperty("connection.url");

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
            log.error(e.getMessage());
        }
        return connection;
    }

    public static String getDbUrl() {
        return DB_URL;
    }

    public static void setDbUrl(String dbUrl) {
        DB_URL = dbUrl;
    }
}
