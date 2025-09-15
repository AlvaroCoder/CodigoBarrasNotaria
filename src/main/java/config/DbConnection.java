package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static final String URL= Settings.getJdbcUrl();
    private static final String USER= Settings.DB_USER;
    private static final String PASSWORD= Settings.DB_PASSWORD;

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }

}
