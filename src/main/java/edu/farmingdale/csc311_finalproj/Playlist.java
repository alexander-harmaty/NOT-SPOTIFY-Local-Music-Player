package edu.farmingdale.csc311_finalproj;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexander
 */
public class Playlist {
    
    private String title;
    private String[] paths;

    public Playlist(String title, List<Song> list_songs) {
        
        List<Song> songs = new ArrayList<>();
        songs.addAll(list_songs);
        paths = new String[songs.size()];
        
        this.title = title;
        
        int i = 0;
        while(!songs.isEmpty()){
            Song song = new Song(songs.remove(0));
            String path = song.getSongPath();
            paths[i] = path;
            i++;
        }
    }
    
//    public Playlist(FileReader fr) {
//        GsonBuilder builder = new GsonBuilder();
//        Gson gson = builder.create();
//        Playlist playlist = gson.fromJson(fr, Playlist.class);
//        
//        this.title = playlist.title;
//        this.list_songs = playlist.list_songs;
//    }
    
    public String getJSONString() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonString = gson.toJson(this);
        return jsonString;
    }

    
    
}