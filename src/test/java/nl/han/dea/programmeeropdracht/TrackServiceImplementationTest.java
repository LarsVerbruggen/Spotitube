package nl.han.dea.programmeeropdracht;

import nl.han.dea.programmeeropdracht.Database.PlaylistDAO;
import nl.han.dea.programmeeropdracht.Database.TrackDAO;
import nl.han.dea.programmeeropdracht.dto.TrackResponse;
import nl.han.dea.programmeeropdracht.model.TrackModel;
import nl.han.dea.programmeeropdracht.services.PlaylistServiceImplementation;
import nl.han.dea.programmeeropdracht.services.TrackServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TrackServiceImplementationTest {

    public static final int PLAYLIST_ID = 1;
    PlaylistDAO playlistDaoMock;
    PlaylistServiceImplementation playlistService;
    TrackServiceImplementation trackService;
    String token;
    TrackDAO trackDaoMock;

    @BeforeEach
    void setup(){
        playlistDaoMock = mock(PlaylistDAO.class);
        trackDaoMock = mock(TrackDAO.class);
        playlistService = new PlaylistServiceImplementation();
        trackService = new TrackServiceImplementation();
        playlistService.setPlaylistDAO(playlistDaoMock);
        playlistService.setTrackDAO(trackDaoMock);
        trackService.setTrackDAO(trackDaoMock);
        token = "123132-as";
    }

    @Test
    void doesEndpointDelegateToDAO(){
        // Setup
        when(trackDaoMock.getAllTracksNotInPlaylist(PLAYLIST_ID)).thenReturn(new TrackResponse());

        // Test
        trackService.getAllTracks(PLAYLIST_ID);

        verify(trackDaoMock).getAllTracksNotInPlaylist(PLAYLIST_ID);

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
        when(trackDaoMock.getAllTracksNotInPlaylist(1)).thenReturn(expected);

        // Test
        Response response = trackService.getAllTracks(1);
        TrackResponse actual = (TrackResponse) response.getEntity();

        // Verify
        assertEquals(expected,  actual);
    }
}
