package nl.han.dea.programmeeropdracht;

import nl.han.dea.programmeeropdracht.Controller.PlaylistsController;
import nl.han.dea.programmeeropdracht.Controller.TrackController;
import nl.han.dea.programmeeropdracht.Database.PlaylistDAO;
import nl.han.dea.programmeeropdracht.Database.TrackDAO;
import nl.han.dea.programmeeropdracht.dto.TrackResponse;
import nl.han.dea.programmeeropdracht.model.TrackModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrackControllerTest {

    PlaylistDAO playlistDaoMock;
    PlaylistsController playlistsController;
    TrackController trackController;
    String token;
    TrackDAO trackDaoMock;

    @BeforeEach
    void setup(){
        playlistDaoMock = mock(PlaylistDAO.class);
        trackDaoMock = mock(TrackDAO.class);
        playlistsController = new PlaylistsController();
        trackController = new TrackController();
        playlistsController.setPlaylistDAO(playlistDaoMock);
        token = "123132-as";
    }

    @Test
    void doesEndpointReturnTracks(){
        // Setup
        var track = new TrackModel();
        var trackList = new ArrayList<TrackModel>();
        track.setId(1);
        track.setTitle("Mooi lied");
        track.setAlbum("Mooi album");
        track.setDuration(256);
        track.setOfflineAvailable(true);
        track.setPerformer("Freedom Call");
        track.setPublicationDate("02-05-2015");
        track.setDescription("Goed lied");
        track.setPlaycount(100);

        var expected = new TrackResponse();
        expected.setTracks(trackList);
        when(trackDaoMock.getTracksFromPlaylist(1)).thenReturn(expected);
        // Test


        Response response = playlistsController.getTracksOfPlaylist(token, 1);
        TrackResponse actual = (TrackResponse) response.getEntity();

        // Verify
        assertEquals(expected,  actual);
    }
}
