package nl.han.dea.programmeeropdracht.services;

import javax.ws.rs.core.Response;

public interface TrackService {
    Response getAllTracks(int playlistID);
}
