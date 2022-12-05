package edu.farmingdale.csc311_finalproj;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Alexander
 */
public class CreatePlaylistController implements Initializable {
    
    @FXML
    private MFXButton button_addSong;

    @FXML
    private MFXButton button_createPlaylist;

    @FXML
    private TableView<?> tableView_library;

    @FXML
    private TableView<?> tableView_playlist;

    @FXML
    private MFXTextField textField_title;

    @FXML
    void handleButton_addSong(ActionEvent event) {

    }

    @FXML
    void handleButton_createPlaylist(ActionEvent event) {

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
