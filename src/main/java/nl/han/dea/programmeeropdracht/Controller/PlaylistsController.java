package nl.han.dea.programmeeropdracht.Controller;

import nl.han.dea.programmeeropdracht.Database.PlaylistDAO;
import nl.han.dea.programmeeropdracht.Database.TrackDAO;
import nl.han.dea.programmeeropdracht.Playlist;
import nl.han.dea.programmeeropdracht.Track;
import nl.han.dea.programmeeropdracht.dto.PlaylistsResponse;
import nl.han.dea.programmeeropdracht.dto.TrackResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("playlists")
public class PlaylistsController {
    private PlaylistDAO playlistDAO = new PlaylistDAO();
    private TrackDAO trackDAO = new TrackDAO();

    PlaylistsResponse response = new PlaylistsResponse();

    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public Response getPlaylists(@QueryParam("token") String token){

        ResultSet result = playlistDAO.getPlaylists(token);
        Playlist resultSet;
        int playlist_id;
        try{
            while(result.next()){
                resultSet = new Playlist();
                playlist_id = result.getInt("PLAYLIST_ID");
                resultSet.setId(playlist_id);
                resultSet.setOwner(result.getBoolean("OWNER"));
                resultSet.setName(result.getString("PLAYLIST_NAME"));

                resultSet.setTracks(new ArrayList<>());

                response.addPlaylist(resultSet);
            }
        }catch (SQLException e){
            System.out.println("Error with database: " + e);
        }

        response.setLength(123445);
        return Response.ok().entity(response).build();
    }


    @Path("/{id}/tracks")
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public Response getTracksOfPlaylist(@QueryParam("token") String token, @PathParam("id") int id){

        ResultSet trackSet = trackDAO.getTracksFromPlaylist(id);
        Track track;
        ArrayList<Track> tracks = new ArrayList<>();

        try{
            while (trackSet.next()){
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
        } catch (SQLException e){
            System.out.println("Error with getting track details from playlist: " + e);
        }


        TrackResponse trackResponse = new TrackResponse();
        trackResponse.setTracks(tracks);



        return Response.ok().entity(trackResponse).build();
    }

}
