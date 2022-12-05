package edu.farmingdale.csc311_finalproj;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.util.Duration;

/**
 *
 * @author Alexander
 */
public class Song {

    //fields
    private String songTitle;
    private String songArtist;
    private int songYear;
    private String songPath;
    private String songURI;
    private Image songArt;
    private Duration songDuration;

    //default constructor
    public Song() {
        this.songTitle = "default";
        this.songArtist = "default";
        this.songYear = 2022;
        this.songPath = "default";
        this.songURI = "default";
        this.songArt = null;
        this.songDuration = Duration.ZERO;
    }

    //copy constructor
    public Song(Song song) {
        this.songTitle = song.songTitle;
        this.songArtist = song.songArtist;
        this.songYear = song.songYear;
        this.songPath = song.songPath;
        this.songURI = song.songURI;
        this.songArt = song.songArt;
        this.songDuration = song.songDuration;
    }

    //all fields constructor without album art
    public Song(String title, String artist, int year, String path, String URI, Duration duration) {
        this.songTitle = title;
        this.songArtist = artist;
        this.songYear = year;
        this.songPath = path;
        this.songURI = URI;
        this.songArt = null;
        this.songDuration = duration;
    }
    
    //all fields constructor with album art
    public Song(String title, String artist, int year, String path, String URI, Image image, Duration duration) {
        this.songTitle = title;
        this.songArtist = artist;
        this.songYear = year;
        this.songPath = path;
        this.songURI = URI;
        this.songArt = image;
        this.songDuration = duration;
    }

    //file constructor
    public Song(File file) throws IOException, UnsupportedTagException, InvalidDataException {
        
        //use Mp3File from Mp3agic dependency to read file metadata
        Mp3File mp3file;
        mp3file = new Mp3File(file);
        
        //check for string fields
        if (mp3file.hasId3v1Tag()) {
            ID3v1 tag = mp3file.getId3v1Tag();
            this.songTitle = tag.getTitle();
            this.songArtist = tag.getArtist();
            this.songYear = Integer.parseInt(tag.getYear());
            this.songPath = file.getPath();
            this.songURI = file.toURI().toString();
            this.songArt = null;
            Media media = new Media(songURI);
            this.songDuration = media.getDuration();
        }
        
        //check for image fields
        if (mp3file.hasId3v2Tag()) {
            ID3v2 tag = mp3file.getId3v2Tag();
            byte[] imageData = tag.getAlbumImage();
            if (imageData != null) {
                RandomAccessFile raf = new RandomAccessFile("album-artwork", "rw");
                raf.write(imageData);
                raf.close();
                this.songArt = new Image(new ByteArrayInputStream(imageData));
            }
        }
        
        //if no tags are detected, become default constructor
        else {
            this.songTitle = "default";
            this.songArtist = "default";
            this.songYear = 2022;
            this.songPath = "default";
            this.songURI = "default";
            this.songArt = null;
            this.songDuration = Duration.ZERO;
        }
        
    }

    //medthod to write class data to Library table in database
    public void writeToDB() {
        try {
            Connection conn = DatabaseConnection.connectDB();
            String sql = 
                    "INSERT INTO Library "
                    + "(Title, Artist, ReleaseYear, Minutes, Seconds, Path, URI) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, this.getSongTitle());
            preparedStatement.setString(2, this.getSongArtist());
            preparedStatement.setInt(3, this.getSongYear());
            preparedStatement.setInt(4, (int) this.getSongDuration().toMinutes());
            preparedStatement.setInt(5, (int) this.getSongDuration().toSeconds());
            preparedStatement.setString(6, this.getSongPath());
            preparedStatement.setString(7, this.getSongURI());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {}
    }

    public String getSongTitle() { return songTitle; }
    public void setSongTitle(String songTitle) { this.songTitle = songTitle; }

    public String getSongArtist() { return songArtist; }
    public void setSongArtist(String songArtist) { this.songArtist = songArtist; }

    public int getSongYear() { return songYear; }
    public void setSongYear(int songYear) { this.songYear = songYear; }

    public String getSongPath() { return songPath; }
    public void setSongPath(String songPath) { this.songPath = songPath; }

    public String getSongURI() { return songURI; }
    public void setSongURI(String songURI) { this.songURI = songURI; }

    public Image getSongArt() { return songArt; }
    public void setSongArt(Image songArt) { this.songArt = songArt; }

    public Duration getSongDuration() { return songDuration; }
    public void setSongDuration(Duration songDuration) { this.songDuration = songDuration; }
    
}
