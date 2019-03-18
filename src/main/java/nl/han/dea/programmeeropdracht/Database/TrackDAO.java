package nl.han.dea.programmeeropdracht.Database;

import nl.han.dea.programmeeropdracht.Track;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrackDAO {
    private DatabaseConnection dbCon;

    public TrackDAO(){
        dbCon = new DatabaseConnection();
        dbCon.connectDatabase();
    }

    public ResultSet getTracksFromPlaylist(int playlist_id){
        ResultSet resultSet = null;
        ArrayList<Track> tracks = new ArrayList<>();

        try{
            PreparedStatement st = dbCon.getDbCon().prepareStatement("SELECT * FROM TRACK T INNER JOIN TRACK_IN_PLAYLIST TP ON T.TRACK_ID = TP.TRACK_ID WHERE PLAYLIST_ID = ?");
            st.setInt(1, playlist_id);
            resultSet = st.executeQuery();
        } catch (SQLException e){
            System.out.println("Error with getting tracks per playlist" + e);
        }
        return resultSet;
    }

}
