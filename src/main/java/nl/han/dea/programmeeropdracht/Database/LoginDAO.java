package nl.han.dea.programmeeropdracht.Database;

import java.sql.*;

public class LoginDAO {
    private DatabaseConnection dbCon;


    public LoginDAO() {
        dbCon = new DatabaseConnection();
        dbCon.connectDatabase();
    }

    public ResultSet getLoginCredentials(String userName) {
        ResultSet result = null;

        try {
            PreparedStatement st = dbCon.getDbCon().prepareStatement("SELECT * FROM [USER] WHERE USER_NAME = ?");
            st.setString(1, userName);
            result = st.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error executing Query:" + e);
        }

        return result;
    }


}
