package com.zhuravlov.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class DbUtil
{
  private static final Logger log = LogManager.getLogger(DbUtil.class);

  private static final String PROTOCOL = "jdbc:mysql:";
  private static final String DB_NAME = "db.name";

  private static HikariDataSource dataSource;

  public static void init(String dbFilePath)
  {

    log.debug("Db file path : {}", dbFilePath);

    HikariConfig config = new HikariConfig();
    config.setPoolName("MySqlPool");
    config.setDriverClassName("com.mysql.jdbc.Driver");
    config.setJdbcUrl(PROTOCOL + dbFilePath);
    config.setUsername("root");
    config.setPassword("admin");
    config.setMaximumPoolSize(1); // 50 Connections (including idle connections)
    dataSource = new HikariDataSource(config);
  }

  public static Connection getConnection()
  {
    Connection connection = null;

    try
    {
      connection = dataSource.getConnection();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }

    return connection;
  }

}
