package nl.han.dea.programmeeropdracht;

import nl.han.dea.programmeeropdracht.Controller.PlaylistsController;
import nl.han.dea.programmeeropdracht.Database.PlaylistDAO;
import nl.han.dea.programmeeropdracht.Database.TrackDAO;
import nl.han.dea.programmeeropdracht.dto.PlaylistsResponse;

import nl.han.dea.programmeeropdracht.services.PlaylistServiceImplementatie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import javax.ws.rs.core.Response;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlaylistControllerTest {

    PlaylistDAO playlistDaoMock;
    PlaylistServiceImplementatie playlistsController;
    String token;
    TrackDAO trackDaoMock;

    @BeforeEach
    void setup(){
        playlistDaoMock = mock(PlaylistDAO.class);
        trackDaoMock = mock(TrackDAO.class);
        playlistsController = new PlaylistServiceImplementatie();
        playlistsController.setPlaylistDAO(playlistDaoMock);
        token = "123132-as";
    }

    @Test
    void doesEndpointDelegateToDAO(){
       // Setup
        when(playlistDaoMock.getPlaylists(token)).thenReturn(new PlaylistsResponse());

        // Test
        playlistsController.getPlaylists(token);

        // Verify
        verify(playlistDaoMock).getPlaylists(token);
    }

    @Test
    void doesEndpointReturnPlaylists(){

        // Setup
        var expected = new PlaylistsResponse();
        var playlist = new Playlist();
        var length = 203;

        playlist.setOwner(true);
        playlist.setId(1);
        playlist.setName("Metal");
        playlist.setTracks(new ArrayList<>());

        expected.setLength(length);
        expected.addPlaylist(playlist);
        when(playlistDaoMock.getPlaylists(token)).thenReturn(expected);

        // Test
        Response response = playlistsController.getPlaylists(token);
        PlaylistsResponse actual = (PlaylistsResponse) response.getEntity();


        // Verify
        assertEquals(expected,actual);

    }

}
