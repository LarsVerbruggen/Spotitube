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
            while (resultSet.next()) {
                TrackModel track = turnResultSetToTrackModel(resultSet);
                track.setOfflineAvailable(resultSet.getBoolean("OFFLINEAVAILABLE"));
                tracksList.add(track);
            }
        } catch (SQLException e) {
            System.out.println("Error with getting tracks per playlist" + e);
        }
        tracks.setTracks(tracksList);
        return tracks;
    }

    public void addTrackToPlaylist(int playlistID, TrackModel track) {
        try {
            PreparedStatement st = dbCon.getDbCon().prepareStatement("INSERT INTO TRACK_IN_PLAYLIST (TRACK_ID, PLAYLIST_ID, OFFLINEAVAILABLE) VALUES(?,?,?) ");
            st.setInt(1,track.getId());
            st.setInt(2,playlistID);
            st.setBoolean(3, track.isOfflineAvailable());
            st.execute();
        } catch (SQLException e) {
            System.out.println("Error adding track to playlist: " + e);
        }
    }

    public TrackResponse getAllTracksNotInPlaylist(int playlist_id) {
        ResultSet resultSet;
        TrackResponse tracks = new TrackResponse();
        ArrayList<TrackModel> tracksList = new ArrayList<>();

        try {
            PreparedStatement st = dbCon.getDbCon().prepareStatement("SELECT DISTINCT T.* FROM TRACK T LEFT JOIN TRACK_IN_PLAYLIST TIP ON T.TRACK_ID = TIP.TRACK_ID WHERE T.TRACK_ID NOT IN (SELECT TRACK_ID FROM TRACK_IN_PLAYLIST WHERE PLAYLIST_ID = ?)");
            st.setInt(1, playlist_id);
            resultSet = st.executeQuery();TrackModel track;
            System.out.println(resultSet);
            while (resultSet.next()) {
                track = turnResultSetToTrackModel(resultSet);
                tracksList.add(track);
            }
        } catch (SQLException e) {
            System.out.println("Error with getting tracks per playlist" + e);
        }
        tracks.setTracks(tracksList);
        return tracks;
    }

    private TrackModel turnResultSetToTrackModel(ResultSet resultSet) throws SQLException {
        TrackModel track;
        track = new TrackModel();
        track.setId(resultSet.getInt("TRACK_ID"));
        track.setTitle(resultSet.getString("TITLE"));
        track.setPerformer(resultSet.getString("PERFORMER"));
        track.setDuration(resultSet.getInt("DURATION"));
        track.setAlbum(resultSet.getString("ALBUM"));
        track.setPublicationDate(resultSet.getString("DESCRIPTION"));
        return track;
    }

    public void deleteTrackFromPlaylist(int playlist_id, int track_id) {
        try{
            PreparedStatement st = dbCon.getDbCon().prepareStatement("DELETE FROM TRACK_IN_PLAYLIST WHERE TRACK_ID = ? AND PLAYLIST_ID = ?");
            st.setInt(1, track_id);
            st.setInt(2, playlist_id);
            st.execute();
        } catch (SQLException e) {
            System.out.println("Error deleting track from playlist: " + e);
        }
    }
}
