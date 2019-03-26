package nl.han.dea.programmeeropdracht.Controller;

import nl.han.dea.programmeeropdracht.Database.PlaylistDAO;
import nl.han.dea.programmeeropdracht.Database.TrackDAO;
import nl.han.dea.programmeeropdracht.Playlist;
import nl.han.dea.programmeeropdracht.dto.PlaylistsResponse;
import nl.han.dea.programmeeropdracht.dto.PlaylistRequest;
import nl.han.dea.programmeeropdracht.dto.TrackResponse;
import nl.han.dea.programmeeropdracht.model.TrackModel;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("playlists")
public class PlaylistsController {
    private PlaylistDAO playlistDAO;
    private TrackDAO trackDAO;

    @GET
    @Path("{id}/tracks")
    @Produces("application/json")
    @Consumes("application/json")
    public Response getTracksOfPlaylist(@QueryParam("token") String token, @PathParam("id") int id) {
        TrackResponse response = trackDAO.getTracksFromPlaylist(id);
        return Response.ok().entity(response).build();
    }

    @POST
    @Path("{id}/tracks")
    @Produces("application/json")
    public Response addTrackToPlaylist(@QueryParam("token") String token, @PathParam("id") int playlistID, TrackModel track){
        trackDAO.addTrackToPlaylist(playlistID, track);
        return Response.ok().entity(track).build();
    }

    @DELETE
    @Path("{id}/tracks/{track}")
    @Produces("application/json")
    public Response deleteTrackFromPlaylist(@QueryParam("token") String token, @PathParam("id") int playlist_id, @PathParam("track") int track_id){
        trackDAO.deleteTrackFromPlaylist(playlist_id,track_id);
        return getTracksOfPlaylist(token, playlist_id);
    }

    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public Response getPlaylists(@QueryParam("token") String token) {
        PlaylistsResponse response = playlistDAO.getPlaylists(token);
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
    public void setPlaylistDAO(PlaylistDAO playlistDAO){
        this.playlistDAO = playlistDAO;
    }

    @Inject
    public void setTrackDAO(TrackDAO trackDAO){
        this.trackDAO = trackDAO;
    }
}
