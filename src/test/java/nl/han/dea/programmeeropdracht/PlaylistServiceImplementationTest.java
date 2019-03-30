package nl.han.dea.programmeeropdracht;

import nl.han.dea.programmeeropdracht.Database.PlaylistDAO;
import nl.han.dea.programmeeropdracht.Database.TrackDAO;
import nl.han.dea.programmeeropdracht.dto.PlaylistRequest;
import nl.han.dea.programmeeropdracht.dto.PlaylistsResponse;

import nl.han.dea.programmeeropdracht.dto.TrackResponse;
import nl.han.dea.programmeeropdracht.model.PlaylistModel;
import nl.han.dea.programmeeropdracht.model.TrackModel;
import nl.han.dea.programmeeropdracht.services.PlaylistServiceImplementation;
import nl.han.dea.programmeeropdracht.services.TrackServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import javax.ws.rs.core.Response;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlaylistServiceImplementationTest {

    private static final int PLAYLIST_ID = 1;
    private PlaylistDAO playlistDaoMock;
    private PlaylistServiceImplementation playlistService;
    private String token;
    private TrackDAO trackDaoMock;
    private TrackModel track;

    @BeforeEach
    void setup(){
        playlistDaoMock = mock(PlaylistDAO.class);
        trackDaoMock = mock(TrackDAO.class);
        playlistService = new PlaylistServiceImplementation();
        playlistService.setPlaylistDAO(playlistDaoMock);
        playlistService.setTrackDAO(trackDaoMock);
        token = "123132-as";

        track = new TrackModel();
        track.setId(1);
        track.setTitle("Mooi lied");
        track.setAlbum("Mooi album");
        track.setDuration(256);
        track.setOfflineAvailable(true);
        track.setPerformer("Goede band");
        track.setPublicationDate("02-05-2015");
        track.setDescription("Goed lied");
        track.setPlaycount(100);
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

        var trackList = new ArrayList<TrackModel>();

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
        var actual = new TrackResponse();

        actual.addTrack(track);

        doAnswer(invocationOnMock -> actual.getTracks().remove(0)).when(trackDaoMock).deleteTrackFromPlaylist(1,1);
        // Test
        playlistService.deleteTrackFromPlaylist(1, 1);


        // Verify
        assertEquals(expected.getTracks(), actual.getTracks());
    }

    @Test
    void doesEndpointUpdatePlaylistName(){
        // Setup
        var request = new PlaylistRequest();
        request.setName("Metal");

        var expected = new PlaylistModel();
        expected.setName("Metal");
        var actual = new PlaylistModel();
        actual.setName("Pop");

        doAnswer(invocationOnMock -> {
             actual.setName("Metal");
             return actual;
        }).when(playlistDaoMock).updatePlaylistName("Metal", 1);

        // Test
        playlistService.updatePlaylistName(request, token, 1);

        // Verify
        assertEquals(expected.getName(), actual.getName());
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

    @Test
    void doesEndpointAddPlaylist(){
        // Setup
        var expected = new PlaylistsResponse();
        var add = new PlaylistModel();
        var actual = new PlaylistsResponse();
        add.setName("Lars");
        add.setId(1);
        add.setOwner(true);
        expected.addPlaylist(add);

        doAnswer(invocationOnMock -> {
            actual.addPlaylist(add);
            return actual;
        }).when(playlistDaoMock).addPlaylist(add, token);

        // Test
        playlistService.addPlaylist(add, token);

        // Verify
        assertEquals(expected.getPlaylists(), actual.getPlaylists());
    }

    @Test
    void doesEndpointDeletePlaylist(){
        // Setup
        var expected = new PlaylistsResponse();
        var playlist = new PlaylistModel();
        var actual = new PlaylistsResponse();
        playlist.setName("Lars");
        playlist.setId(1);
        playlist.setOwner(true);
        actual.addPlaylist(playlist);

        doAnswer(invocationOnMock -> {
            actual.getPlaylists().remove(0);
            return actual;
        }).when(playlistDaoMock).deletePlaylist(1);

        // Test
        playlistService.deletePlaylist(1 ,token);

        // Verify
        assertEquals(expected.getPlaylists(), actual.getPlaylists());
    }

}
