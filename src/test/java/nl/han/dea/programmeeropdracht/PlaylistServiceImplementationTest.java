package nl.han.dea.programmeeropdracht;

import nl.han.dea.programmeeropdracht.Database.PlaylistDAO;
import nl.han.dea.programmeeropdracht.Database.TrackDAO;
import nl.han.dea.programmeeropdracht.dto.PlaylistsResponse;

import nl.han.dea.programmeeropdracht.dto.TrackResponse;
import nl.han.dea.programmeeropdracht.model.PlaylistModel;
import nl.han.dea.programmeeropdracht.model.TrackModel;
import nl.han.dea.programmeeropdracht.services.PlaylistServiceImplementation;
import nl.han.dea.programmeeropdracht.services.TrackServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


import javax.ws.rs.core.Response;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlaylistServiceImplementationTest {

    private static final int PLAYLIST_ID = 1;
    private PlaylistDAO playlistDaoMock;
    private PlaylistServiceImplementation playlistService;
    private TrackServiceImplementation trackService;
    private String token;
    private TrackDAO trackDaoMock;

    @BeforeEach
    void setup(){
        playlistDaoMock = mock(PlaylistDAO.class);
        trackDaoMock = mock(TrackDAO.class);
        trackService = new TrackServiceImplementation();
        playlistService = new PlaylistServiceImplementation();
        playlistService.setPlaylistDAO(playlistDaoMock);
        playlistService.setTrackDAO(trackDaoMock);
        token = "123132-as";

    }

    @Test
    void doesEndpointDelegateToDAO(){
       // Setup
        when(playlistDaoMock.getPlaylists(token)).thenReturn(new PlaylistsResponse());

        // Test
        playlistService.getPlaylists(token);

        // Verify
        verify(playlistDaoMock).getPlaylists(token);
    }

    @Test
    void doesEndpointReturnTracksByPlaylist(){
        // Setup
        var expected = new TrackResponse();
        var track = new TrackModel();
        var trackList = new ArrayList<TrackModel>();
        track.setId(1);
        track.setTitle("Mooi lied");
        track.setAlbum("Mooi album");
        track.setDuration(256);
        track.setOfflineAvailable(true);
        track.setPerformer("Goede band");
        track.setPublicationDate("02-05-2015");
        track.setDescription("Goed lied");
        track.setPlaycount(100);
        trackList.add(track);

        expected.setTracks(trackList);

        when(trackDaoMock.getTracksFromPlaylist(PLAYLIST_ID)).thenReturn(expected);

        // Test
        Response response = playlistService.getTracksOfPlaylist(PLAYLIST_ID);
        TrackResponse actual = (TrackResponse) response.getEntity();

        // Verify
        assertEquals(expected, actual);
    }

    @Test
    void doesEndpointAddTrackToPlaylist(){
        // Setup
        var track = new TrackModel();
        track.setId(1);
        track.setTitle("Mooi lied");
        track.setAlbum("Mooi album");
        track.setDuration(256);
        track.setOfflineAvailable(true);
        track.setPerformer("Goede band");
        track.setPublicationDate("02-05-2015");
        track.setDescription("Goed lied");
        track.setPlaycount(100);

        doAnswer(invocationOnMock -> track).when(trackDaoMock).addTrackToPlaylist(1, track);

        // test
        Response response = playlistService.addTrackToPlaylist(1, track);
        TrackModel actual = (TrackModel) response.getEntity();

        // Verify
        assertEquals(track, actual);

    }

    @Test
    void doesEndpointDeleteTracksFromPlaylist(){
        // Setup
        var expected = new TrackResponse();
        var track = new TrackModel();
        var actual = new TrackResponse();
        track.setId(1);
        track.setTitle("Mooi lied");
        track.setAlbum("Mooi album");
        track.setDuration(256);
        track.setOfflineAvailable(true);
        track.setPerformer("Goede band");
        track.setPublicationDate("02-05-2015");
        track.setDescription("Goed lied");
        track.setPlaycount(100);

        actual.addTrack(track);

        doAnswer(invocationOnMock -> actual.getTracks().remove(0)).when(trackDaoMock).deleteTrackFromPlaylist(1,1);
        // Test
        playlistService.deleteTrackFromPlaylist(1, 1);


        // Verify
        assertEquals(expected.getTracks(), actual.getTracks());
    }

    @Test
    void doesEndpointReturnPlaylists(){

        // Setup
        var expected = new PlaylistsResponse();
        var playlist = new PlaylistModel();
        var length = 203;

        playlist.setOwner(true);
        playlist.setId(1);
        playlist.setName("Metal");
        playlist.setTracks(new ArrayList<>());

        expected.setLength(length);
        expected.addPlaylist(playlist);
        when(playlistDaoMock.getPlaylists(token)).thenReturn(expected);

        // Test
        Response response = playlistService.getPlaylists(token);
        PlaylistsResponse actual = (PlaylistsResponse) response.getEntity();


        // Verify
        assertEquals(expected,actual);

    }

}
