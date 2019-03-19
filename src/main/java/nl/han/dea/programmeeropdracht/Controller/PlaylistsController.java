package nl.han.dea.programmeeropdracht.Controller;

import nl.han.dea.programmeeropdracht.Database.PlaylistDAO;
import nl.han.dea.programmeeropdracht.Database.TrackDAO;
import nl.han.dea.programmeeropdracht.Playlist;
import nl.han.dea.programmeeropdracht.Track;
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

    PlaylistsResponse response = new PlaylistsResponse();

    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public Response getPlaylists(@QueryParam("token") String token) {

        int length = 0;
        ResultSet result = playlistDAO.getPlaylists(token);
        Playlist playlist;
        int playlist_id;
        try {
            while (result.next()) {
                playlist = new Playlist();
                playlist_id = result.getInt("PLAYLIST_ID");
                playlist.setId(playlist_id);
                playlist.setOwner(result.getBoolean("OWNER"));
                playlist.setName(result.getString("PLAYLIST_NAME"));

                playlist.setTracks(new ArrayList<>());

                length = length + getLengthOfPlayList(playlist_id);

                response.addPlaylist(playlist);
            }
        } catch (SQLException e) {
            System.out.println("Error with database: " + e);
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

    @Inject
    public void setTrackDAO(TrackDAO trackDAO){
        this.trackDAO = trackDAO;
    }

    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO){
        this.playlistDAO = playlistDAO;
    }

}
