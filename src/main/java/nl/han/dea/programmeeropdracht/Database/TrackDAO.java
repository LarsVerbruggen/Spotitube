package nl.han.dea.programmeeropdracht.Database;

import nl.han.dea.programmeeropdracht.dto.TrackResponse;
import nl.han.dea.programmeeropdracht.model.TrackModel;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrackDAO {
    private DatabaseConnection dbCon;

    public TrackDAO() {
        dbCon = new DatabaseConnection();
        dbCon.connectDatabase();
    }

    public TrackResponse getTracksFromPlaylist(int playlist_id) {
        ResultSet resultSet;
        TrackResponse tracks = new TrackResponse();
        ArrayList<TrackModel> tracksList = new ArrayList<>();

        try {
            PreparedStatement st = dbCon.getDbCon().prepareStatement("SELECT * FROM TRACK T INNER JOIN TRACK_IN_PLAYLIST TP ON T.TRACK_ID = TP.TRACK_ID WHERE PLAYLIST_ID = ?");
            st.setInt(1, playlist_id);
            resultSet = st.executeQuery();
            tracksList = addTracksToListFromResultSet(resultSet);
        } catch (SQLException e) {
            System.out.println("Error with getting tracks per playlist" + e);
        }
        tracks.setTracks(tracksList);
        return tracks;
    }

    public void addTrackToPlaylist(int playlistID, TrackModel track) {
        try {
            PreparedStatement st = dbCon.getDbCon().prepareStatement("INSERT INTO TRACK_IN_PLAYLIST (TRACK_ID, PLAYLIST_ID) VALUS(?,?) ");
            st.setInt(1,track.getId());
            st.setInt(2,playlistID);
        } catch (SQLException e) {
            System.out.println("Error adding track to playlist: " + e);
        }
    }

    public TrackResponse getAllTracksNotInPlaylist(int playlist_id) {
        ResultSet resultSet;
        TrackResponse tracks = new TrackResponse();
        ArrayList<TrackModel> tracksList = new ArrayList<>();

        try {
            PreparedStatement st = dbCon.getDbCon().prepareStatement("SELECT * FROM TRACK WHERE TRACK_ID NOT IN (SELECT TRACK_ID FROM TRACK_IN_PLAYLIST WHERE PLAYLIST_ID = ?)");
            st.setInt(1, playlist_id);
            resultSet = st.executeQuery();
            tracksList = addTracksToListFromResultSet(resultSet);
        } catch (SQLException e) {
            System.out.println("Error with getting tracks per playlist" + e);
        }
        tracks.setTracks(tracksList);
        return tracks;
    }

    private ArrayList<TrackModel> addTracksToListFromResultSet(ResultSet resultSet) throws SQLException {
        TrackModel track;
        ArrayList<TrackModel> tracksList = new ArrayList<>();
        while (resultSet.next()) {
            track = new TrackModel();
            track.setId(resultSet.getInt("TRACK_ID"));
            track.setTitle(resultSet.getString("TITLE"));
            track.setPerformer(resultSet.getString("PERFORMER"));
            track.setDuration(resultSet.getInt("DURATION"));
            track.setAlbum(resultSet.getString("ALBUM"));
            track.setPublicationDate(resultSet.getString("DESCRIPTION"));
            track.setOfflineAvailable(resultSet.getBoolean("OFFLINEAVAILABLE"));
            tracksList.add(track);
        }
        return tracksList;
    }
}
