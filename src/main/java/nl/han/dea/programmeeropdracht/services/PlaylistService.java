package nl.han.dea.programmeeropdracht.services;

import nl.han.dea.programmeeropdracht.Playlist;
import nl.han.dea.programmeeropdracht.dto.PlaylistRequest;
import nl.han.dea.programmeeropdracht.model.TrackModel;

import javax.ws.rs.core.Response;

public interface PlaylistService {
    Response getTracksOfPlaylist(int id);
    Response addTrackToPlaylist(int playlistID, TrackModel track);
    Response deleteTrackFromPlaylist(int playlist_id, int track_id);
    Response getPlaylists(String token);
    Response updatePlaylistName(PlaylistRequest request,String token, int id);
    Response addPlaylist(Playlist request, String token);
    Response deletePlaylist(int id, String token);
}
