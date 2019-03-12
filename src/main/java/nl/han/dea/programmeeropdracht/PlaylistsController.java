package nl.han.dea.programmeeropdracht;


import nl.han.dea.programmeeropdracht.dto.PlaylistsResponse;
import nl.han.dea.programmeeropdracht.dto.TrackResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("playlists")
public class PlaylistsController {


    PlaylistsResponse response = new PlaylistsResponse();

    Playlist deathMetal = new Playlist();
    Playlist pop = new Playlist();

    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public Response getPlaylists(@QueryParam("token") String token){


        deathMetal.setId(1);
        deathMetal.setName("Death Metal");
        deathMetal.setOwner(true);
        deathMetal.setTracks(new ArrayList<>());

        pop.setId(2);
        pop.setName("Pop");
        pop.setOwner(false);
        pop.setTracks(new ArrayList<>());

        response.addPlaylist(deathMetal);
        response.addPlaylist(pop);
        response.setLength(123445);


        return Response.ok().entity(response).build();
    }


    @Path("/{id}/tracks")
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public Response getTracksOfPlaylist(@QueryParam("token") String token, @PathParam("id") int id){
        Playlist playlist;

        ArrayList<Track> tracks = new ArrayList<>();
        Track track1 = new Track();
        track1.setId(1);
        track1.setTitle("Song for Someone");
        track1.setPerformer("The Frames");
        track1.setDuration(350);
        track1.setAlbum("The cost");
        track1.setOfflineAvailable(false);

        Track track2 = new Track();
        track2.setId(2);
        track2.setTitle("The cost");
        track2.setPerformer("The Frames");
        track2.setDuration(423);
        track2.setPlaycount(37);
        track2.setPublicationDate("10-01-2005");
        track2.setDescription("Title song from the Album The Cost");
        track2.setOfflineAvailable(true);

        tracks.add(track1);
        tracks.add(track2);

        deathMetal.setTracks(tracks);


        switch(id){
            case 1:
                playlist = deathMetal;
                break;
            case 2:
                playlist = pop;
                break;
            default: playlist = deathMetal;
        }

        TrackResponse response = new TrackResponse();
        response.setTracks(playlist.getTracks());



        return Response.ok().entity(response).build();
       // return Response.status(502).build();
    }

}
