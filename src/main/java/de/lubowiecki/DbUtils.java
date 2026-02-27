package de.lubowiecki;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {

    private static final String DSN = "jdbc:sqlite:data.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DSN);
    }
}
