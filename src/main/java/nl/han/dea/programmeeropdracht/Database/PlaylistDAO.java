package nl.han.dea.programmeeropdracht.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistDAO {

    private DatabaseConnection dbCon;


    public PlaylistDAO() {
        dbCon = new DatabaseConnection();
        dbCon.connectDatabase();
    }

    public ResultSet getPlaylists(String token) {
        ResultSet result = null;

        try {
            PreparedStatement st = dbCon.getDbCon().prepareStatement("SELECT * FROM PLAYLIST WHERE USER_NAME = ?");
            st.setString(1, getUserByToken(token));
            result = st.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error in getting results from playlist: " + e);
        }
        return result;
    }

    private String getUserByToken(String token) {
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

    public void updatePlaylistName(String newName, int id) {
        try{
            PreparedStatement st = dbCon.getDbCon().prepareStatement("UPDATE PLAYLIST SET PLAYLIST_NAME = ? WHERE PLAYLIST_ID = ?");
            st.setString(1, newName);
            st.setInt(2, id);
        } catch(SQLException e){
            System.out.println("Error updating playlist name");
        }

    }
}
