package nl.han.dea.programmeeropdracht;


import nl.han.dea.programmeeropdracht.Controller.TrackController;
import nl.han.dea.programmeeropdracht.services.TrackServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.mockito.Mockito.*;


public class TrackControllerTest {

    private TrackController controller;
    private TrackServiceImplementation service;
    private String token;
    private int playlist_id;

    @BeforeEach
    void setup(){
        controller = new TrackController();
        service = mock(TrackServiceImplementation.class);
        controller.setService(service);
        token = "232-567";
        playlist_id = 1;
    }

    @Test
    void doesTrackControllerMakeUseOfService(){
        // Setup
        when(service.getAllTracks(1)).thenReturn(Response.ok().build());
        // Test
        controller.getAllTracks(token, playlist_id);
        // Verify
        service.getAllTracks(1);

    }
}
