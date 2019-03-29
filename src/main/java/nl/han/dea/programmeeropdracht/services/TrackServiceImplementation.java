package nl.han.dea.programmeeropdracht.services;

import nl.han.dea.programmeeropdracht.Database.TrackDAO;
import nl.han.dea.programmeeropdracht.dto.TrackResponse;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

public class TrackServiceImplementation implements TrackService {

    private TrackDAO trackDAO;

    @Override
    public Response getAllTracks(int playlistID) {
        TrackResponse response = trackDAO.getAllTracksNotInPlaylist(playlistID);
        return Response.ok().entity(response).build();
    }
    @Inject
    public void setTrackDAO(TrackDAO trackDAO){
        this.trackDAO = trackDAO;
    }
}
