package nl.han.dea.programmeeropdracht;


import nl.han.dea.programmeeropdracht.dto.PlaylistsResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("playlists")
public class PlaylistsController {

    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public Response getPlaylists(@QueryParam("token") String token){

        PlaylistsResponse response = new PlaylistsResponse();

        Playlist deathMetal = new Playlist();
        deathMetal.setId(1);
        deathMetal.setName("Death Metal");
        deathMetal.setOwner(true);
        deathMetal.setTracks(new ArrayList<>());


        Playlist pop = new Playlist();
        pop.setId(2);
        pop.setName("Pop");
        pop.setOwner(false);
        pop.setTracks(new ArrayList<>());

        response.addPlaylist(deathMetal);
        response.addPlaylist(pop);
        response.setLength(123445);


        return Response.ok().entity(response).build();
    }


    @Path("{id}/tracks")
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public Response getTracksOfPlaylist(@QueryParam("token") String token, @PathParam("id") int id){



        // return Response.ok().entity(response).build();
        return Response.status(502).build();
    }

}
