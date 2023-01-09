package com.urise.webapp.util;

import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SQLFunction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLHelper {

    private final ConnectionFactory connectionFactory;

    public SQLHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void execute(String sqlQuery) {
        execute(sqlQuery, ps -> ps.execute());
    }

    public <T> T execute(String sqlQuery, SQLFunction<T> function) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
            return function.apply(ps);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }
}
