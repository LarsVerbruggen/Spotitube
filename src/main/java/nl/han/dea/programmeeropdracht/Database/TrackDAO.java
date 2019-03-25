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
        TrackModel track;

        try {
            PreparedStatement st = dbCon.getDbCon().prepareStatement("SELECT * FROM TRACK T INNER JOIN TRACK_IN_PLAYLIST TP ON T.TRACK_ID = TP.TRACK_ID WHERE PLAYLIST_ID = ?");
            st.setInt(1, playlist_id);
            resultSet = st.executeQuery();
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
        } catch (SQLException e) {
            System.out.println("Error with getting tracks per playlist" + e);
        }
        tracks.setTracks(tracksList);
        return tracks;
    }

}
