package nl.han.dea.programmeeropdracht.services;

import nl.han.dea.programmeeropdracht.Database.PlaylistDAO;
import nl.han.dea.programmeeropdracht.Database.TrackDAO;
import nl.han.dea.programmeeropdracht.dto.PlaylistRequest;
import nl.han.dea.programmeeropdracht.dto.PlaylistsResponse;
import nl.han.dea.programmeeropdracht.dto.TrackResponse;
import nl.han.dea.programmeeropdracht.model.PlaylistModel;
import nl.han.dea.programmeeropdracht.model.TrackModel;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

public class PlaylistServiceImplementation implements PlaylistService {

    private PlaylistDAO playlistDAO;
    private TrackDAO trackDAO;

    @Override
    public Response getTracksOfPlaylist(int id) {
        TrackResponse response = trackDAO.getTracksFromPlaylist(id);
        return Response.ok().entity(response).build();
    }

    @Override
    public Response addTrackToPlaylist(int playlistID, TrackModel track) {
        trackDAO.addTrackToPlaylist(playlistID, track);
        return Response.ok().entity(track).build();
    }

    @Override
    public Response deleteTrackFromPlaylist(int playlist_id, int track_id) {
        trackDAO.deleteTrackFromPlaylist(playlist_id,track_id);
        return getTracksOfPlaylist(playlist_id);
    }

    @Override
    public Response getPlaylists(String user) {
        PlaylistsResponse response = playlistDAO.getPlaylists(user);
        return Response.ok().entity(response).build();
    }

    @Override
    public Response updatePlaylistName(PlaylistRequest request, String token, int id) {
        playlistDAO.updatePlaylistName(request.getName(), id);
        return getPlaylists(token);
    }

    @Override
    public Response addPlaylist(PlaylistModel request, String username) {
        playlistDAO.addPlaylist(request, username);

        return getPlaylists(username);
    }

    @Override
    public Response deletePlaylist(int id, String username) {
        playlistDAO.deletePlaylist(id);

        return getPlaylists(username);
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
