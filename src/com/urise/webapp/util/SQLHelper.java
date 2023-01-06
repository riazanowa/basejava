package com.urise.webapp.util;

import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {

    public static final String DB_URL = Config.getInstance().getDbUrl();
    public static final String DB_USER = Config.getInstance().getDbUser();
    public static final String DB_PASSWORD = Config.getInstance().getDbPassword();

    public static Connection connection;

    public SQLHelper() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            } catch (SQLException e) {
                throw new StorageException(e);
            }
        }
        return connection;
    }
}
