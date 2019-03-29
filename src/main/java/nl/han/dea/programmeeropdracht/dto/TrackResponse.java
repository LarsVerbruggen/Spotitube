package nl.han.dea.programmeeropdracht.dto;

import nl.han.dea.programmeeropdracht.model.TrackModel;

import java.util.ArrayList;

public class TrackResponse {
    private ArrayList<TrackModel> tracks = new ArrayList<>();

    public ArrayList<TrackModel> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<TrackModel> tracks) {
        this.tracks = tracks;
    }

    public void addTrack(TrackModel track) {
        tracks.add(track);
    }
}
