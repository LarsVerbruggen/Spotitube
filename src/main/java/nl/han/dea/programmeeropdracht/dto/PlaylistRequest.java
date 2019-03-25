package nl.han.dea.programmeeropdracht.dto;

import nl.han.dea.programmeeropdracht.model.TrackModel;

import java.util.ArrayList;

public class PlaylistRequest {

    private int id;
    private String name;
    private boolean owner;
    private ArrayList<TrackModel> tracks;

    public ArrayList<TrackModel> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<TrackModel> tracks) {
        this.tracks = tracks;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
