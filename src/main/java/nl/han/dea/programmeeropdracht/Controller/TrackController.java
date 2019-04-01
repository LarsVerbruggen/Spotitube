package nl.han.dea.programmeeropdracht.Controller;

import nl.han.dea.programmeeropdracht.services.TrackService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("")
public class TrackController {

    TrackService service;

    @GET
    @Path("tracks")
    @Produces("application/json")
    public Response getAllTracks(@QueryParam("token") String token, @QueryParam("forPlaylist") int playlistID){
        return service.getAllTracks(playlistID);
    }

    @Inject
    public void setService(TrackService service){
        this.service = service;
    }



}
