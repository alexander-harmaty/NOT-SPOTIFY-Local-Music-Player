package edu.farmingdale.csc311_finalproj;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexander
 */
public class Playlist {
    
    private String title;
    private String[] paths;

    public Playlist(String title, List<Song> list_songs) {
        this.title = title;
        setSongs(list_songs);
    }
    
    public Playlist(FileReader fr) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Playlist playlist = gson.fromJson(fr, Playlist.class);
        
        this.title = playlist.title;
        this.paths = playlist.paths;
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

    public String[] getPaths() {
        return paths;
    }

    public void setPaths(String[] paths) {
        this.paths = paths;
    }

    public List<Song> getSongs() throws IOException, UnsupportedTagException, InvalidDataException {
        List<Song> list_songs = new ArrayList<>();
        for (int i = 0; i < paths.length; i++) {
            String path = paths[i];
            File file = new File(path);
            try {
                Song song = new Song(file);
                list_songs.add(song);
            } catch (InvalidDataException ex) {}
        }
        return list_songs;
    }

    public void setSongs(List<Song> list_songs) {
        List<Song> songs = new ArrayList<>();
        songs.addAll(list_songs);
        paths = new String[songs.size()];
        int i = 0;
        while(!songs.isEmpty()){
            Song song = new Song(songs.remove(0));
            String path = song.getSongPath();
            paths[i] = path;
            i++;
        }
    }
    
}