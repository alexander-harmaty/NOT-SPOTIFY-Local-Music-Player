package edu.farmingdale.csc311_finalproj;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static Set<Song> set_librarySongs = new HashSet<>();

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Home"), 1280, 720);
        stage.setScene(scene);
        stage.setTitle("Not Spotify");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}