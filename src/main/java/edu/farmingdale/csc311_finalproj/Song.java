package edu.farmingdale.csc311_finalproj;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Alexander
 */
public class Song {

    private String songTitle;
    private String songArtist;
    private String songYear;
    private String songPath;
    private String songURI;

    public Song() {
        this.songTitle = "default";
        this.songArtist = "default";
        this.songYear = "2022";
        this.songPath = "default";
        this.songURI = "default";
    }

    public Song(Song song) {
        this.songTitle = song.songTitle;
        this.songArtist = song.songArtist;
        this.songYear = song.songYear;
        this.songPath = song.songPath;
        this.songURI = song.songURI;
    }

    public Song(String title, String artist, String year, String path, String URI) {
        this.songTitle = title;
        this.songArtist = artist;
        this.songYear = year;
        this.songPath = path;
        this.songURI = URI;
    }

    public Song(File file) throws IOException, UnsupportedTagException, InvalidDataException {
        Mp3File mp3file = new Mp3File(file);
        if (mp3file.hasId3v1Tag()) {
            ID3v1 tag = mp3file.getId3v1Tag();
            this.songTitle = tag.getTitle();
            this.songArtist = tag.getArtist();
            this.songYear = tag.getYear();
            this.songPath = file.getPath();
            this.songURI = file.toURI().toString();
        } else {
            this.songTitle = "default";
            this.songArtist = "default";
            this.songYear = "2022";
            this.songPath = "default";
            this.songURI = "default";
        }
    }

    public void writeToDB() {
        try {
            Connection conn = DatabaseConnection.connectDB();
            String sql = "INSERT INTO Library (Title, Artist, ReleaseYear, Path, URI) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, this.getSongTitle());
            preparedStatement.setString(2, this.getSongArtist());
            preparedStatement.setString(3, this.getSongYear());
            preparedStatement.setString(4, this.getSongPath());
            preparedStatement.setString(5, this.getSongURI());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {}
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public String getSongYear() {
        return songYear;
    }

    public void setSongYear(String songYear) {
        this.songYear = songYear;
    }

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }

    public String getSongURI() {
        return songURI;
    }

    public void setSongURI(String songURI) {
        this.songURI = songURI;
    }

}
