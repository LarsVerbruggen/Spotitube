package nl.han.dea.programmeeropdracht;

import nl.han.dea.programmeeropdracht.Controller.PlaylistsController;
import nl.han.dea.programmeeropdracht.dto.PlaylistRequest;
import nl.han.dea.programmeeropdracht.model.PlaylistModel;
import nl.han.dea.programmeeropdracht.model.TrackModel;
import nl.han.dea.programmeeropdracht.services.PlaylistServiceImplementation;
import nl.han.dea.programmeeropdracht.services.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.core.Response;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlaylistControllerTest {

    private PlaylistsController sut;
    private PlaylistServiceImplementation service;
    private TokenService tokenService;
    private final String TOKEN = "1234-ab";
    private final String USER = "Lars";

    @BeforeEach
    void setup(){
        sut = new PlaylistsController();
        service = mock(PlaylistServiceImplementation.class);
        tokenService = mock(TokenService.class);
        sut.setService(service);
        sut.setTokenService(tokenService);
        when(tokenService.getUserByToken(TOKEN)).thenReturn(USER);
    }

    @Test
    void doesPlaylistControllerMakeUseOfServiceForGettingPlaylists(){
        when(service.getPlaylists(USER)).thenReturn(Response.ok().build());

        Response actual = sut.getPlaylists(TOKEN);

        assertEquals(200, actual.getStatus());
    }

    @Test
    void doesPlaylistControllerMakeUseOfServiceForGettingTrackPerPlaylist(){
        // Setup
        when(service.getTracksOfPlaylist(1)).thenReturn(Response.ok().build());

        // test
        Response actual = sut.getTracksOfPlaylist(TOKEN, 1);

        // Verify
        assertEquals(200, actual.getStatus());
    }

    @Test
    void doesPlaylistControllerMakeUseOfServiceForAddingTracksToPlaylist(){
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

        when(service.addTrackToPlaylist(1, track)).thenReturn(Response.ok().build());

        // Test
        Response actual = sut.addTrackToPlaylist(TOKEN, 1, track);

        // Verify
        assertEquals(200, actual.getStatus());
    }

    @Test
    void doesPlaylistControllerMakeUseOfServiceForDeletingTracksFromPlaylist(){
        // Setup
        when(service.deleteTrackFromPlaylist(1,1)).thenReturn(Response.ok().build());

        // Test
        Response actual = sut.deleteTrackFromPlaylist(TOKEN , 1, 1);

        // Verify
        assertEquals(200, actual.getStatus());
    }

    @Test
    void doesPlaylistControllerMakeUseOfServiceWhenUpdatingPlaylistName(){
        //  Setup
        var request = new PlaylistRequest();
        when(service.updatePlaylistName(request, TOKEN, 1)).thenReturn(Response.ok().build());

        // Test
        Response actual = sut.updatePlaylistName(request, TOKEN, 1);

        // Verify
        assertEquals(200, actual.getStatus());
    }

    @Test
    void doesPlaylistControllerMakeUseOfServiceWhenAddingPlaylist(){
        PlaylistModel request = new PlaylistModel();

        when(service.addPlaylist(request, USER)).thenReturn(Response.ok().build());

        // Test
        Response actual = sut.addPlaylist(request, TOKEN);

        // Verify
        assertEquals(200, actual.getStatus());
    }

    @Test
    void doesPlaylistControllerMakeUseOfServiceWhenDeletingPlaylist(){
        // Setup
        when(service.deletePlaylist(1, USER)).thenReturn(Response.ok().build());

        // Test
        Response actual = sut.deletePlaylist(1, TOKEN);

        // Verify
        assertEquals(200, actual.getStatus());
    }

}
