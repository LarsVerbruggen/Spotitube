package nl.han.dea.programmeeropdracht.dto;

import nl.han.dea.programmeeropdracht.model.PlaylistModel;

import java.util.ArrayList;
import java.util.List;

public class PlaylistsResponse {

    private List<PlaylistModel> playlists = new ArrayList<>();
    private int length;


    public void addPlaylist(PlaylistModel list) {
        playlists.add(list);
    }

    public void setLength(int i) {
        length = i;
    }

    public int getLength() {
        return length;
    }

    public List<PlaylistModel> getPlaylists() {
        return playlists;
    }

    public void setPlaylist(List<PlaylistModel> playlist) {
        playlists = playlist;
    }
}