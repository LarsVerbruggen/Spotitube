package nl.han.dea.programmeeropdracht.Controller;

import nl.han.dea.programmeeropdracht.Database.LoginDAO;
import nl.han.dea.programmeeropdracht.Database.PlaylistDAO;
import nl.han.dea.programmeeropdracht.Database.TrackDAO;
import nl.han.dea.programmeeropdracht.Playlist;
import nl.han.dea.programmeeropdracht.Track;
import nl.han.dea.programmeeropdracht.dto.PlaylistRequest;
import nl.han.dea.programmeeropdracht.dto.PlaylistsResponse;
import nl.han.dea.programmeeropdracht.dto.TrackResponse;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("playlists")
public class PlaylistsController {
    private PlaylistDAO playlistDAO;
    private TrackDAO trackDAO;
    private LoginDAO loginDAO;

    PlaylistsResponse response = new PlaylistsResponse();

    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public Response getPlaylists(@QueryParam("token") String token) {

        int length = 0;
        ResultSet result = playlistDAO.getPlaylists();
        Playlist playlist;
        int playlist_id;
        try {
            while (result.next()) {
                playlist = new Playlist();
                playlist_id = result.getInt("PLAYLIST_ID");
                playlist.setId(playlist_id);
                if(loginDAO.getUserByToken(token).equals(result.getString("USER_NAME"))){
                    playlist.setOwner(true);
                }else{
                    playlist.setOwner(false);
                }

                playlist.setName(result.getString("PLAYLIST_NAME"));

                playlist.setTracks(new ArrayList<>());

                length = length + getLengthOfPlayList(playlist_id);

                response.addPlaylist(playlist);
            }
        } catch (SQLException e) {
            System.out.println("Error with database" + e);

        }

        response.setLength(length);

        return Response.ok().entity(response).build();
    }

    private int getLengthOfPlayList(int playlist_id) {
        int length = 0;
        ResultSet trackSet = trackDAO.getTracksFromPlaylist(playlist_id);
        try {
            while (trackSet.next()) {
                length = length + trackSet.getInt("DURATION");
            }
        } catch (SQLException e) {
            System.out.println("Error with getting length of playlist: " + e);
        }
        return length;
    }

    @Path("/{id}/tracks")
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public Response getTracksOfPlaylist(@QueryParam("token") String token, @PathParam("id") int id) {
        ResultSet trackSet = trackDAO.getTracksFromPlaylist(id);
        Track track;
        ArrayList<Track> tracks = new ArrayList<>();

        try {
            while (trackSet.next()) {
                track = new Track();
                track.setId(trackSet.getInt("TRACK_ID"));
                track.setTitle(trackSet.getString("TITLE"));
                track.setPerformer(trackSet.getString("PERFORMER"));
                track.setDuration(trackSet.getInt("DURATION"));
                track.setAlbum(trackSet.getString("ALBUM"));
                track.setPublicationDate(trackSet.getString("DESCRIPTION"));
                track.setOfflineAvailable(trackSet.getBoolean("OFFLINEAVAILABLE"));
                tracks.add(track);
            }
        } catch (SQLException e) {
            System.out.println("Error with getting track details from playlist: " + e);
        }

        TrackResponse trackResponse = new TrackResponse();
        trackResponse.setTracks(tracks);

        return Response.ok().entity(trackResponse).build();
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

    @Inject
    public void setLoginDAO(LoginDAO loginDAO){
        this.loginDAO = loginDAO;
    }

}
