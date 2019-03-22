package nl.han.dea.programmeeropdracht.Database;

import nl.han.dea.programmeeropdracht.Playlist;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistDAO {

    private DatabaseConnection dbCon;
    private LoginDAO loginDAO;


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

    public void addPlaylist(Playlist request, String token) {
        String user = loginDAO.getUserByToken(token);

        try{
            PreparedStatement st = dbCon.getDbCon().prepareStatement("INSERT INTO PLAYLIST (USER_NAME, PLAYLIST_NAME) VALUES (?,?)");
            st.setString(1, user);
            st.setString(2, request.getName());
            st.execute();
        } catch (SQLException e) {
            System.out.println("Error adding playlist:" + e);
        }
    }

    public void deletePlaylist(int id){
        try{
            PreparedStatement st = dbCon.getDbCon().prepareStatement("DELETE FROM PLAYLIST WHERE PLAYLIST_ID = ?");
            st.setInt(1,id);
            st.execute();
        }catch (SQLException e){
            System.out.println("Error deleting playlist:" + e);
        }
    }

    @Inject
    public void setLoginDAO(LoginDAO loginDAO){
        this.loginDAO = loginDAO;
    }

}
