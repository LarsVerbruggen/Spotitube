package nl.han.dea.programmeeropdracht.Database;

import nl.han.dea.programmeeropdracht.model.UserModel;

import javax.ws.rs.core.Response;
import java.sql.*;

public class LoginDAO {
    private DatabaseConnection dbCon;


    public LoginDAO() {
        dbCon = new DatabaseConnection();
        dbCon.connectDatabase();
    }

    public UserModel getLoginCredentials(String userName, String password) {
        ResultSet result;
        UserModel user = new UserModel();

        try {
            PreparedStatement st = dbCon.getDbCon().prepareStatement("SELECT * FROM [USER] WHERE USER_NAME = ?");
            st.setString(1, userName);
            result = st.executeQuery();

            while (result.next()) {
                if (password.equals(result.getString("PASSWORD"))) {
                    user.setName(result.getString("USER_NAME"));
                    user.setToken(result.getString("TOKEN"));
                }
            }


        } catch (SQLException e) {
            System.out.println("Error executing Query:" + e);
        }

        return user;
    }

    public String getUserByToken(String token) {
        ResultSet resultSet = null;
        String result = null;

        try {
            PreparedStatement st = dbCon.getDbCon().prepareStatement("SELECT USER_NAME FROM [USER] WHERE TOKEN = ?");
            st.setString(1, token);
            resultSet = st.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error in getting results from playlist: " + e);
        }

        try {
            if (resultSet.next()) {
                result = resultSet.getString("USER_NAME");
            }
        } catch (SQLException e) {
            System.out.println("Error getting result from user by token" + e);
        }

        return result;
    }


    public void updateToken(String name, String token) {
        try {
            PreparedStatement st = dbCon.getDbCon().prepareStatement("UPDATE [USER] SET TOKEN= ? WHERE [USER_NAME] = ?");
            st.setString(1, token);
            st.setString(2, name);
            st.execute();
        } catch (SQLException e) {
            System.out.println("Error updating token:" + e);
        }
    }
}
