package nl.han.dea.programmeeropdracht.Database;


import java.sql.*;

public class DatabaseConnection{
    Connection dbCon = null;

    public void connectDatabase(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }

        try {
            dbCon = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Spotitube", "sa", "dbrules");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
