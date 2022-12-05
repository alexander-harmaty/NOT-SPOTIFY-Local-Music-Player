package edu.farmingdale.csc311_finalproj;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.sun.javafx.font.FontResource;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXSlider;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.font.FontResources;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.Icon;

public class HomeController implements Initializable {

    @FXML
    private VBox VBox_playlists;

    @FXML
    private MFXButton button_library;

    @FXML
    private MFXButton button_next, button_shuffle, button_queue, button_clearQueue,
            button_togglePlayPause;

    @FXML
    private ImageView imageView_albumArtBar, imageView_albumArtMain;

    @FXML
    private Label label_currentTime, label_endTime, label_playlistTitle,
            label_songArtistBar, label_songArtistMain, label_songTitleBar, label_songTitleMain;

    @FXML
    private MenuItem menuItem_about, menuItem_close, menuItem_clearLibraryDB,
            menuItem_createPlaylist, menuItem_deletePlaylist, menuItem_editPlaylist,
            menuItem_exportPlaylistJSON, menuItem_importPlaylistJSON, menuItem_importSongFiles;

    @FXML
    private MFXSlider slider_progressBar, slider_volume;

    @FXML
    private MFXTableView<Song> tableView_songsList;
    
    @FXML
    private MFXListView<String> listView_queue;
    
    ////////////////////////////////////////////////////////////////////////////
    
    public ObservableList<Song> observableList_songsList;
    public ObservableList<String> observableList_queueSongs;
    
    public List<Song> list_queue = new ArrayList<>();
    public List<Song> list_currentPlayList = new ArrayList<>();
    
    ////////////////////////////////////////////////////////////////////////////
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //set column comparator and click rules for the tableView
        setupTable();
        //setOnMousePressed();
        
        //set initial tableView data to show user library
        try {
            updateLibrarySongsSetList();
            insertIntoTable(App.set_librarySongs);
        } catch (IOException | UnsupportedTagException | InvalidDataException ex) {}
        
        readPlaylists();
    }

    ////////////////////////////////////////////////////////////////////////////
    
    void setupTable() {
        
        MFXTableColumn<Song> column_title = new MFXTableColumn<>("Title", true, Comparator.comparing(Song::getSongTitle));
        MFXTableColumn<Song> column_artist = new MFXTableColumn<>("Artist", true, Comparator.comparing(Song::getSongArtist));
        MFXTableColumn<Song> column_year = new MFXTableColumn<>("Year", true, Comparator.comparing(Song::getSongYear));
        //MFXTableColumn<Song> column_duration = new MFXTableColumn<>("Time", true, Comparator.comparing(Song::getSongDuration));
        
        column_title.setRowCellFactory(song -> new MFXTableRowCell<>(Song::getSongTitle));
        column_artist.setRowCellFactory(song -> new MFXTableRowCell<>(Song::getSongArtist));
        column_year.setRowCellFactory(song -> new MFXTableRowCell<>(Song::getSongYear));
        //column_duration.setRowCellFactory(song -> new MFXTableRowCell<>(Song::getSongDuration));
        
        //tableView_songsList.getTableColumns().addAll(column_title, column_artist, column_year, column_duration);
        tableView_songsList.getTableColumns().addAll(column_title, column_artist, column_year);
        tableView_songsList.getFilters().addAll (
                new StringFilter<>("Title", Song::getSongTitle),
                new StringFilter<>("Artist", Song::getSongArtist),
                new IntegerFilter<>("Year", Song::getSongYear)
                //new StringFilter<>("Duration", song -> song.getSongDuration().toString())
        );
        
    }
    
    void insertIntoTable(Collection collection) {
        observableList_songsList = FXCollections.observableArrayList(collection);
        tableView_songsList.getItems().clear();
        tableView_songsList.setItems(observableList_songsList);
        list_currentPlayList.clear();
        list_currentPlayList.addAll(collection);
    }
    
    void updateLibrarySongsSetList() throws IOException, UnsupportedTagException, InvalidDataException {
        
        App.set_librarySongs.clear();
        
        try {
            Connection conn = DatabaseConnection.connectDB();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from Library");
            while (rs.next()) {
                String path = rs.getString("Path");
                File file = new File(path);
                Song song = new Song(file);
                
                App.set_librarySongs.add(song);
            }
        } catch (SQLException except) {}
        
    }
    
    void setObservableList_queueSongs() {
        List<String> queueStrings = new ArrayList<>();
        List<Song> tempList = new ArrayList<>();
        tempList.addAll(list_queue);
        while (!tempList.isEmpty()) {
            Song tempSong = new Song(tempList.remove(0));
            String songTitleAndArtist = tempSong.getSongTitle() + " by " + tempSong.getSongArtist();
            queueStrings.add(songTitleAndArtist);
        }
        observableList_queueSongs = FXCollections.observableArrayList(queueStrings);
        listView_queue.setItems(observableList_queueSongs);
    }
    
    void readPlaylists() {
        VBox_playlists.getChildren().clear();
        VBox_playlists.getChildren().add(button_library);
        List<MFXButton> buttons = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.connectDB();
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("select * from Playlists");
            while (result.next()) {
                String SongsJSON = result.getString("SongsJSON");
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Playlist playlist = gson.fromJson(SongsJSON, Playlist.class);
                MFXButton button = new MFXButton(playlist.getTitle(), 150, 25);
                button.setOnAction(event -> {
                    try {
                        tableView_songsList.getItems().clear();
                        insertIntoTable(playlist.getSongs());
                        App.currentJSON = SongsJSON;
                    } catch (IOException | UnsupportedTagException | InvalidDataException ex) {}
                });
                buttons.add(button);
            }
            VBox_playlists.getChildren().addAll(buttons);
        } catch (SQLException except) {}
    }
    
    void setOnMousePressed() {
        
        tableView_songsList.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //check for primary mouse clicks
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    
                    //if click is double click...
                    if (event.getClickCount() == 2) {
                        
                        //read selected course CRN
                        ObservableMap<Integer, Song> map;
                        map = tableView_songsList.getSelectionModel().getSelection();
                        Song song = new Song(map.get(0));
                        System.out.println(song.toString());
                    }
                }
            }    
        });
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
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
            
            if (tableView_songsList.getItems() == observableList_songsList) {
                //read new data into Library set for songs
                updateLibrarySongsSetList();
                insertIntoTable(App.set_librarySongs);
            } else {
                //read new data into Library set for songs
                updateLibrarySongsSetList();
            }
        } catch (IOException io) {}
        
    }
    
    @FXML
    void handleMenuItem_exportPlaylistJSON(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showSaveDialog(null);
        
        if(selectedFile != null) {
            PrintStream ps = null;
            try{
                ps = new PrintStream(selectedFile);
                ps.append(App.currentJSON);
            }
            catch (FileNotFoundException e){}
        }
        
    }
    
    @FXML
    void handleMenuItem_importPlaylistJSON(ActionEvent e) throws FileNotFoundException {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        
        if(selectedFile != null) {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            
            FileReader fr = new FileReader(selectedFile);
            Playlist playlist = gson.fromJson(fr, Playlist.class);
            String SongsJSON = playlist.getJSONString();
            
            MFXButton button = new MFXButton(playlist.getTitle(), 150, 25);
            button.setOnAction(event -> {
                try {
                    tableView_songsList.getItems().clear();
                    insertIntoTable(playlist.getSongs());
                    App.currentJSON = SongsJSON;
                } catch (IOException | UnsupportedTagException | InvalidDataException ex) {}
            });
            VBox_playlists.getChildren().add(button);
        }
    }
    
    @FXML
    void handleMenuItem_clearLibraryDB(ActionEvent event) {

    }
    
    @FXML
    void handleMenuItem_close(ActionEvent event) {
        System.exit(0);
    }
    
    @FXML
    void handleMenuItem_about(ActionEvent event) {}
    
    ////////////////////////////////////////////////////////////////////////////
    
    @FXML
    void handleMenuItem_createPlaylist(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreatePlaylist.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Course List");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {}
    }

    @FXML
    void handleMenuItem_deletePlaylist(ActionEvent event) {}

    @FXML
    void handleMenuItem_editPlaylist(ActionEvent event) {}

    ////////////////////////////////////////////////////////////////////////////

    @FXML
    void handleButton_togglePlayPause(ActionEvent event) {}
    
    @FXML
    void handleButton_next(ActionEvent event) {}
    
    @FXML
    void handleButton_queue(ActionEvent event) {
        list_queue.addAll(list_currentPlayList) ;
        setObservableList_queueSongs();
    }
    
    @FXML
    void handleButton_shuffle(ActionEvent event) {
        Collections.shuffle(list_queue);
        setObservableList_queueSongs();
    }
    
    @FXML
    void handleButton_clearQueue(ActionEvent event) {
        listView_queue.getItems().clear();
        list_queue.clear();
    }

    ////////////////////////////////////////////////////////////////////////////
    
    @FXML
    void handleButton_library(ActionEvent event) throws IOException, UnsupportedTagException, UnsupportedTagException, InvalidDataException{
        try {
            updateLibrarySongsSetList();
            insertIntoTable(App.set_librarySongs);
            //App.currentJSON = SongsJSON;
        } catch (IOException | UnsupportedTagException | InvalidDataException ex) {}
    }

    ////////////////////////////////////////////////////////////////////////////
    
}
