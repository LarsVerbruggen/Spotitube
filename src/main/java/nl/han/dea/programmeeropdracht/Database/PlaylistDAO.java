package nl.han.dea.programmeeropdracht.Database;

import nl.han.dea.programmeeropdracht.Playlist;
import nl.han.dea.programmeeropdracht.dto.PlaylistsResponse;
import nl.han.dea.programmeeropdracht.dto.TrackResponse;

import nl.han.dea.programmeeropdracht.model.TrackModel;


import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistDAO {

    private DatabaseConnection dbCon;
    private LoginDAO loginDAO;
    private TrackDAO trackDAO;


    public PlaylistDAO() {
        dbCon = new DatabaseConnection();
        dbCon.connectDatabase();
    }

    public PlaylistsResponse getPlaylists(String token) {
        ResultSet result;
        PlaylistsResponse model = new PlaylistsResponse();
        Playlist playlist;
        int playlist_id;
        int length = 0;

        try {
            PreparedStatement st = dbCon.getDbCon().prepareStatement("SELECT PLAYLIST_ID, USER_NAME, PLAYLIST_NAME FROM PLAYLIST");
            result = st.executeQuery();
            while (result.next()) {
                    playlist = new Playlist();
                    playlist_id = result.getInt("PLAYLIST_ID");
                    playlist.setId(playlist_id);
                    if(loginDAO.getUserByToken(token).equals(result.getString("USER_NAME"))){
                        playlist.setOwner(true);
                    }else{
                        playlist.setOwner(false);
                    }

                    playlist.setName(result.getString("PLAYLIST_NAME"));

                    playlist.setTracks(new ArrayList<>());

                    length = length + getLengthOfPlayList(playlist_id);
                    model.addPlaylist(playlist);
                }
        } catch (SQLException e) {
            System.out.println("Error in getting results from playlist: " + e);
        }
        model.setLength(length);
        return model;
    }

    private int getLengthOfPlayList(int playlist_id) {
        int length = 0;
        TrackResponse trackSet = trackDAO.getTracksFromPlaylist(playlist_id);
        for (TrackModel track: trackSet.getTracks()) {
            length = length + track.getDuration();
        }
        return length;
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

    @Inject
    public void setTrackDAO(TrackDAO trackDAO){
        this.trackDAO = trackDAO;
    }

}
