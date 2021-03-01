package com.zhuravlov.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class for database util methods
 */
public class DbUtil {
    private final static Logger log = Logger.getLogger(DbUtil.class);

    //TODO:from properties
    private static String DB_URL;

    private static HikariDataSource dataSource;

    /**
     * Get url from property fle
     */
    public static void setUrlFromProp() {
        Properties appProps = new Properties();
        try {
            InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/app.properties");
            appProps.load(input);
        } catch (IOException e) {
            log.warn(e.getMessage());
        }
        DB_URL = appProps.getProperty("connection.url");

        log.info("Db file path :" + DB_URL);
    }

    /**
     * Create hickari connection pool config for mysql
     */
    public static void init() {
        HikariConfig config = new HikariConfig();
        config.setPoolName("MySqlPool");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl(DB_URL);
        config.setUsername("root");
        config.setPassword("admin");
        config.setMaximumPoolSize(1); // 50 Connections (including idle connections)
        dataSource = new HikariDataSource(config);
    }

    /**
     * Static method for getting connection from connection pool
     * @return connection
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return connection;
    }

    public static void setDataSource(HikariDataSource dataSource) {
        DbUtil.dataSource = dataSource;
    }
}
