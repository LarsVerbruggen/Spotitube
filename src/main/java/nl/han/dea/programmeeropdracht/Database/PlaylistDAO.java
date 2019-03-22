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

    public ResultSet getPlaylists() {
        ResultSet result = null;

        try {
            PreparedStatement st = dbCon.getDbCon().prepareStatement("SELECT PLAYLIST_ID, USER_NAME, PLAYLIST_NAME FROM PLAYLIST");
            result = st.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error in getting results from playlist: " + e);
        }
        return result;
    }

    public void updatePlaylistName(String newName, int id) {
        try{
            PreparedStatement st = dbCon.getDbCon().prepareStatement("UPDATE PLAYLIST SET PLAYLIST_NAME = ? WHERE PLAYLIST_ID = ?");
            st.setString(1, newName);
            st.setInt(2, id);
            st.execute();
        } catch(SQLException e){
            System.out.println("Error updating playlist name: " + e);
        }

    }
}
