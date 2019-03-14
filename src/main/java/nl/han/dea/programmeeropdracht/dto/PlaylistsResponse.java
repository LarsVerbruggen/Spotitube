package nl.han.dea.programmeeropdracht.dto;

import nl.han.dea.programmeeropdracht.Playlist;

import java.util.ArrayList;
import java.util.List;

public class PlaylistsResponse {

    private List<Playlist> playlists = new ArrayList<>();
    private int length;



    public void addPlaylist(Playlist list){
            playlists.add(list);
    }

    public void setLength(int i){
        length = i;
    }

    public int getLength() {
        return length;
    }

    public List<Playlist> getPlaylists(){
        return playlists;
    }
}


//    {
//        "playlists" :[
//        {
//            "id"    : 1,
//                "name"  : "Death metal",
//                "owner" : true,
//                "tracks": []
//        },
//        {
//            "id"    : 2,
//                "name"  : "Pop",
//                "owner" : false,
//                "tracks": []
//        }
//              ],
//        "length"  :123445}
