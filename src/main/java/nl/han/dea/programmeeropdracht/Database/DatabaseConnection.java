package nl.han.dea.programmeeropdracht.Database;


import java.sql.*;

public class DatabaseConnection{
    private Connection dbCon = null;

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

    public Connection getDbCon() {
        return dbCon;
    }

    public void setDbCon(Connection dbCon) {
        this.dbCon = dbCon;
    }
}
