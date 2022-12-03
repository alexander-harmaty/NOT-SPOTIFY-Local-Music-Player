package edu.farmingdale.csc311_finalproj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class HomeController {

    @FXML
    private VBox VBox_playlists;

    @FXML
    private Button button_library, 
            button_next, button_previous, button_togglePlayPause;

    @FXML
    private ImageView imageView_albumArt;

    @FXML
    private Label label_currentTime, label_endTime, 
            label_songArtist, label_songTitle;

    @FXML
    private MenuItem menuItem_about, menuItem_close, 
            menuItem_createPlaylist, menuItem_importFiles;

    @FXML
    private Slider slider_progressBar, slider_volume;

    @FXML
    private TableColumn<?, ?> tableColumn_index;

    @FXML
    private TableColumn<?, ?> tableColumn_songAlbum;

    @FXML
    private TableColumn<?, ?> tableColumn_songArtist;

    @FXML
    private TableColumn<?, ?> tableColumn_songDuration;

    @FXML
    private TableColumn<?, ?> tableColumn_songTitle;

    @FXML
    private TableView<?> tableView_songsList;

    @FXML
    void handleButton_about(ActionEvent event) {

    }

    @FXML
    void handleButton_close(ActionEvent event) {

    }

    @FXML
    void handleButton_createPlaylist(ActionEvent event) {

    }

    @FXML
    void handleButton_importFiles(ActionEvent event) {

    }

    @FXML
    void handleButton_library(ActionEvent event) {

    }

    @FXML
    void handleButton_next(ActionEvent event) {

    }

    @FXML
    void handleButton_previous(ActionEvent event) {

    }

    @FXML
    void handleButton_togglePlayPause(ActionEvent event) {

    }

}
