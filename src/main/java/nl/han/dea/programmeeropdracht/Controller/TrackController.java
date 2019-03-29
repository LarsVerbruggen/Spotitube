package nl.han.dea.programmeeropdracht.Controller;

import nl.han.dea.programmeeropdracht.Database.TrackDAO;
import nl.han.dea.programmeeropdracht.dto.TrackResponse;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("")
public class TrackController {

    private TrackDAO trackDAO;

    @GET
    @Path("tracks")
    @Produces("application/json")
    public Response getAllTracks(@QueryParam("token") String token, @QueryParam("forPlaylist") int playlistID){
        TrackResponse response = trackDAO.getAllTracksNotInPlaylist(playlistID);
        return Response.ok().entity(response).build();
    }



    @Inject
    public void setTrackDAO(TrackDAO trackDAO){
        this.trackDAO = trackDAO;
    }
}
