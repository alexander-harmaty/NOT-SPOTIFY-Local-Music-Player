package edu.farmingdale.csc311_finalproj;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.util.List;

/**
 *
 * @author Alexander
 */
public class Playlist {
    
    private String title;
    private List<Song> list_songs;

    public Playlist(String title, List<Song> list_songs) {
        this.title = title;
        this.list_songs = list_songs;
    }
    
    public Playlist(FileReader fr) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Playlist playlist = gson.fromJson(fr, Playlist.class);
        
        this.title = playlist.title;
        this.list_songs = playlist.list_songs;
    }
    
    public String getJSONString() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonString = gson.toJson(this);
        return jsonString;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Song> getList_songs() {
        return list_songs;
    }

    public void setList_songs(List<Song> list_songs) {
        this.list_songs = list_songs;
    }
    
    
}