package nl.han.dea.programmeeropdracht.Controller;

import nl.han.dea.programmeeropdracht.Database.TrackDAO;
import nl.han.dea.programmeeropdracht.dto.TrackResponse;
import nl.han.dea.programmeeropdracht.services.TrackServiceImplementation;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("")
public class TrackController {

    @Inject
    private TrackServiceImplementation service;

    @GET
    @Path("tracks")
    @Produces("application/json")
    public Response getAllTracks(@QueryParam("token") String token, @QueryParam("forPlaylist") int playlistID){
        return service.getAllTracks(playlistID);
    }



}
