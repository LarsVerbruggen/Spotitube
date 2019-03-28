package nl.han.dea.programmeeropdracht.Controller;

import nl.han.dea.programmeeropdracht.model.PlaylistModel;
import nl.han.dea.programmeeropdracht.dto.PlaylistRequest;
import nl.han.dea.programmeeropdracht.model.TrackModel;
import nl.han.dea.programmeeropdracht.services.PlaylistService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("playlists")
public class PlaylistsController {

    @Inject
    PlaylistService service;

    @GET
    @Path("{id}/tracks")
    @Produces("application/json")
    @Consumes("application/json")
    public Response getTracksOfPlaylist(@QueryParam("token") String token, @PathParam("id") int id) {
        return service.getTracksOfPlaylist(id);
    }

    @POST
    @Path("{id}/tracks")
    @Produces("application/json")
    public Response addTrackToPlaylist(@QueryParam("token") String token, @PathParam("id") int playlistID, TrackModel track){
       return service.addTrackToPlaylist(playlistID, track);
    }

    @DELETE
    @Path("{id}/tracks/{track}")
    @Produces("application/json")
    public Response deleteTrackFromPlaylist(@QueryParam("token") String token, @PathParam("id") int playlist_id, @PathParam("track") int track_id){
       return service.deleteTrackFromPlaylist(playlist_id, track_id);
    }

    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public Response getPlaylists(@QueryParam("token") String token) {
       return service.getPlaylists(token);
    }

    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    public Response updatePlaylistName(PlaylistRequest request, @QueryParam("token") String token, @PathParam("id") int id){
       return service.updatePlaylistName(request, token, id);
    }

    @Path("/")
    @POST
    @Consumes("application/json")
    public Response addPlaylist(PlaylistModel request, @QueryParam("token") String token){
       return service.addPlaylist(request, token);
    }

    @DELETE
    @Path("/{id}")
    public Response deletePlaylist(@PathParam("id") int id, @QueryParam("token") String token){
        return service.deletePlaylist(id, token);
    }


}
