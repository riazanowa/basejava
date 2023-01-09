package com.urise.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SQLFunction <T>{
    T apply(PreparedStatement ps) throws SQLException;
}
