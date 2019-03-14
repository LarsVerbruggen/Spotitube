package nl.han.dea.programmeeropdracht.dto;

import nl.han.dea.programmeeropdracht.Track;

import java.util.ArrayList;

public class TrackResponse {
    private ArrayList<Track> tracks;

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }
    }
