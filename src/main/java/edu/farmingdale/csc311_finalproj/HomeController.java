package edu.farmingdale.csc311_finalproj;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXSlider;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class HomeController implements Initializable {

    @FXML
    private VBox VBox_main, VBox_playlists;

    @FXML
    private MFXButton button_library;

    @FXML
    private MFXButton button_next, button_previous, button_togglePlayPause;

    @FXML
    private ImageView imageView_albumArtBar, imageView_albumArtMain;

    @FXML
    private Label label_currentTime, label_endTime, label_playlistTitle,
            label_songArtistBar, label_songArtistMain, label_songTitleBar, label_songTitleMain;

    @FXML
    private MenuItem menuItem_about, menuItem_close,
            menuItem_createPlaylist, menuItem_deletePlaylist, menuItem_editPlaylist,
            menuItem_exportPlaylistJSON, menuItem_importPlaylistJSON, menuItem_importSongFiles;

    @FXML
    private MFXSlider slider_progressBar, slider_volume;

    @FXML
    private MFXTableView<Song> tableView_songsList;
    
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTable();
    }

    ////////////////////////////////////////////////////////////////////////////
    @FXML
    void handleButton_library(ActionEvent event) {
    }

    ////////////////////////////////////////////////////////////////////////////
    @FXML
    void handleButton_next(ActionEvent event) {

    }

    @FXML
    void handleButton_previous(ActionEvent event) {

    }

    @FXML
    void handleButton_togglePlayPause(ActionEvent event) {

    }

    ////////////////////////////////////////////////////////////////////////////
    @FXML
    void handleMenuItem_about(ActionEvent event) {

    }

    @FXML
    void handleMenuItem_close(ActionEvent event) {

    }

    ////////////////////////////////////////////////////////////////////////////
    @FXML
    void handleMenuItem_createPlaylist(ActionEvent event) {

    }

    @FXML
    void handleMenuItem_deletePlaylist(ActionEvent event) {

    }

    @FXML
    void handleMenuItem_editPlaylist(ActionEvent event) {

    }

    ////////////////////////////////////////////////////////////////////////////
    @FXML
    void handleMenuItem_exportPlaylistJSON(ActionEvent event) {

    }

    @FXML
    void handleMenuItem_importPlaylistJSON(ActionEvent event) {

    }

    @FXML
    void handleMenuItem_importSongFiles(ActionEvent event) throws IOException, UnsupportedTagException, InvalidDataException, SQLException {

        //declare
        FileChooser fc = new FileChooser();
        Song song = null;

        try {
            //set initial directory
            File file = new File(new File(".").getCanonicalPath());
            fc.setInitialDirectory(file);

            //open file chooser
            File selected = fc.showOpenDialog(null);

            //check if not null...
            if (selected != null) {
                //...then create song from file, and write to database
                song = new Song(selected);
                song.writeToDB();
            }
            
            updateLibrary();
        } catch (IOException io) {}
    }
    
    ////////////////////////////////////////////////////////////////////////////
    void updateLibrary() throws IOException, UnsupportedTagException, InvalidDataException {
        
        ObservableList<Song> songsList;
        List<Song> songs = new ArrayList<>();

        try {
            Connection conn = DatabaseConnection.connectDB();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from Library");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String title = rs.getString("Title");
                String artist = rs.getString("Artist");
                int year = rs.getInt("ReleaseYear");
                int min = rs.getInt("Minutes");
                int sec = rs.getInt("Seconds");
                String path = rs.getString("Path");
                String URI = rs.getString("URI");
                Image image = null;
                
                File file = new File(path);
                Song song = new Song(file);
                songs.add(song);
            }
            songsList = FXCollections.observableArrayList(songs);
            tableView_songsList.setItems(songsList);
        } catch (SQLException except) {}
        
    }
    
    void setupTable() {
        
        MFXTableColumn<Song> column_title = new MFXTableColumn<>("Title", true, Comparator.comparing(Song::getSongTitle));
        MFXTableColumn<Song> column_artist = new MFXTableColumn<>("Artist", true, Comparator.comparing(Song::getSongArtist));
        MFXTableColumn<Song> column_year = new MFXTableColumn<>("Year", true, Comparator.comparing(Song::getSongYear));
        MFXTableColumn<Song> column_duration = new MFXTableColumn<>("Time", true, Comparator.comparing(Song::getSongDuration));
        
        column_title.setRowCellFactory(song -> new MFXTableRowCell<>(Song::getSongTitle));
        column_artist.setRowCellFactory(song -> new MFXTableRowCell<>(Song::getSongArtist));
        column_year.setRowCellFactory(song -> new MFXTableRowCell<>(Song::getSongYear));
        column_duration.setRowCellFactory(song -> new MFXTableRowCell<>(Song::getSongDuration));
        
        tableView_songsList.getTableColumns().addAll(column_title, column_artist, column_year, column_duration);
        tableView_songsList.getFilters().addAll (
                new StringFilter<>("Title", Song::getSongTitle),
                new StringFilter<>("Artist", Song::getSongArtist),
                new IntegerFilter<>("Year", Song::getSongYear),
                new StringFilter<>("Duration", song -> song.getSongDuration().toString())
        );
        
    }
    
}
