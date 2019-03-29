package nl.han.dea.programmeeropdracht.Database;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseConnection {
    private Connection dbCon = null;
    private Properties properties = new Properties();

    public void connectDatabase() {

        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Class.forName(properties.getProperty("db.driver.class"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            dbCon = DriverManager.getConnection(properties.getProperty("db.conn.url"), properties.getProperty("db.username"), properties.getProperty("db.password"));
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public Connection getDbCon() {
        return dbCon;
    }
}
