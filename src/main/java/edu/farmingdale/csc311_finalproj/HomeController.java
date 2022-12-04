package edu.farmingdale.csc311_finalproj;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXSlider;
import io.github.palexdev.materialfx.controls.MFXTableView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;

public class HomeController implements Initializable{
    
    MediaPlayer MediaPlayer;

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
    void handleMenuItem_importSongFiles(ActionEvent event) {

    }

    ////////////////////////////////////////////////////////////////////////////
    
    

}
