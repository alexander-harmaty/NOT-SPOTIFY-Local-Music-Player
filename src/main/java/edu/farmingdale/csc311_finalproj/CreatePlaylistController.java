package edu.farmingdale.csc311_finalproj;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class CreatePlaylistController implements Initializable{

    @FXML
    private MFXButton button_addSong;

    @FXML
    private MFXButton button_createPlaylist;

    @FXML
    private TableColumn<Song, String> tableColumn_library_artist;

    @FXML
    private TableColumn<Song, String> tableColumn_library_title;

    @FXML
    private TableColumn<Song, String> tableColumn_playlist_artist;

    @FXML
    private TableColumn<Song, String> tableColumn_playlist_title;

    @FXML
    private TableView<Song> tableView_library;

    @FXML
    private TableView<Song> tableView_playlist;

    @FXML
    private MFXTextField textField_title;
    
    ObservableList<Song> observableList_library;
    ObservableList<Song> observableList_playlist;
    List<Song> songs = new ArrayList<>();

    @FXML
    void handleButton_addSong(ActionEvent event) {
        Song song = new Song(tableView_library.getSelectionModel().getSelectedItem());
        songs.add(song);
        observableList_playlist.clear();
        observableList_playlist = FXCollections.observableArrayList(songs);
        tableView_playlist.setItems(observableList_playlist);
    }

    @FXML
    void handleButton_createPlaylist(ActionEvent event) {
        if (!textField_title.equals("") || !tableView_playlist.getItems().isEmpty()) {
            Playlist playlist = new Playlist(textField_title.getText(), songs);
            String SongsJSON = playlist.getJSONString();
            
            try {
                Connection conn = DatabaseConnection.connectDB();
                String sql = "INSERT INTO Playlists (SongsJSON) VALUES (?)";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, SongsJSON);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {}
        }
       
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        tableColumn_library_title.setCellValueFactory(new PropertyValueFactory<>("songTitle"));        
        tableColumn_library_artist.setCellValueFactory(new PropertyValueFactory<>("songArtist"));
        observableList_library = FXCollections.observableArrayList(App.set_librarySongs);
        tableView_library.setItems(observableList_library);
        
        tableColumn_playlist_title.setCellValueFactory(new PropertyValueFactory<>("songTitle"));
        tableColumn_playlist_artist.setCellValueFactory(new PropertyValueFactory<>("songArtist"));
        observableList_playlist = FXCollections.observableArrayList(songs);
        tableView_playlist.setItems(observableList_playlist);
        
        setOnMousePressed();
    }
    
    private void setOnMousePressed() {
        
        tableView_library.setOnMousePressed((MouseEvent event) -> {
            
            //check for primary mouse clicks
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                
                //if click is double click...
                if (event.getClickCount() == 2) {
                    
                    //read song
                    Song song = new Song(tableView_library.getSelectionModel().getSelectedItem());
                    songs.add(song);
                    observableList_playlist.clear();
                    observableList_playlist = FXCollections.observableArrayList(songs);
                    tableView_playlist.setItems(observableList_playlist);
                }
            }
        });
    }    

}
