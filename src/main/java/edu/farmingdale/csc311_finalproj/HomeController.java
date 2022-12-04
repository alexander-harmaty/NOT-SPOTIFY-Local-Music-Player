package edu.farmingdale.csc311_finalproj;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXSlider;
import io.github.palexdev.materialfx.controls.MFXTableView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

public class HomeController implements Initializable {

    MediaPlayer mediaPlayer;
    MediaView mediaView;
    List<Media> mediaFiles = new ArrayList<>();
    
    Song song = new Song();
    
    String title;
    String artist;
    int year;

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
    private MFXTableView<?> tableView_songsList;

    ////////////////////////////////////////////////////////////////////////////
    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
    void handleMenuItem_importSongFiles(ActionEvent event) throws IOException, UnsupportedTagException, InvalidDataException {
        FileChooser fc = new FileChooser();
        File file = null;
        try {
            file = new File(new File(".").getCanonicalPath());
        } catch (IOException io) {
        }
        fc.setInitialDirectory(file);

        File selected = fc.showOpenDialog(null);
        if (selected != null) {
            
            Mp3File mp3file = new Mp3File(selected);
            if(mp3file.hasId3v1Tag()){
                ID3v1 tag = mp3file.getId3v1Tag();
                song.setSongTitle(tag.getTitle());
                song.setSongArtist(tag.getArtist());
            }
        }
        //imageView_albumArtMain.setImage(image);
        System.out.println(song.getSongArtist() + song.getSongTitle());
    }

}
