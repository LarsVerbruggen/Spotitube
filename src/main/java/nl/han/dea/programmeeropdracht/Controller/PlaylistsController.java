package nl.han.dea.programmeeropdracht.Controller;

import nl.han.dea.programmeeropdracht.Database.PlaylistDAO;
import nl.han.dea.programmeeropdracht.Database.TrackDAO;
import nl.han.dea.programmeeropdracht.Playlist;
import nl.han.dea.programmeeropdracht.dto.PlaylistsResponse;
import nl.han.dea.programmeeropdracht.dto.PlaylistRequest;
import nl.han.dea.programmeeropdracht.dto.TrackResponse;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("playlists")
public class PlaylistsController {
    private PlaylistDAO playlistDAO;
    private TrackDAO trackDAO;


    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public Response getPlaylists(@QueryParam("token") String token) {
        PlaylistsResponse response = playlistDAO.getPlaylists(token);
        return Response.ok().entity(response).build();
    }



    @Path("/{id}/tracks")
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public Response getTracksOfPlaylist(@QueryParam("token") String token, @PathParam("id") int id) {
        TrackResponse response = trackDAO.getTracksFromPlaylist(id);
        return Response.ok().entity(response).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    public Response updatePlaylistName(PlaylistRequest request, @QueryParam("token") String token, @PathParam("id") int id){
        playlistDAO.updatePlaylistName(request.getName(), id);

        return getPlaylists(token);
    }

    @Path("/")
    @POST
    @Consumes("application/json")
    public Response addPlaylist(Playlist request, @QueryParam("token") String token){
        playlistDAO.addPlaylist(request, token);

        return getPlaylists(token);
    }

    @DELETE
    @Path("/{id}")
    public Response deletePlaylist(@PathParam("id") int id, @QueryParam("token") String token){
        playlistDAO.deletePlaylist(id);

        return getPlaylists(token);
    }

    @Inject
    public void setTrackDAO(TrackDAO trackDAO){
        this.trackDAO = trackDAO;
    }

    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO){
        this.playlistDAO = playlistDAO;
    }
    }
