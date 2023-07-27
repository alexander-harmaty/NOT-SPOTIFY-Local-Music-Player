# NOT SPOTIFY Local Music Player

Advanced Java Programming Capstone

(FSC-CSC-311-Capstone)

## User Interface


![output](https://github.com/alexander-harmaty/NOT-SPOTIFY-Local-Music-Player/assets/92049896/34a3e077-7e22-4ddf-812a-36c36534d17c)

## Assignment Context

This project was the fourth and final assignment for my Advanced Programming class in Java.

The goal of was to take concepts learned from previous assignments and put them together for an original final project.

The concepts and required features are: a GUI with ListView and animations, SQL Database with JDBC, JSON, Threads, Lambdas and Streams, and a Collection class.

To fulfill these requirements, I wrote a music player. 

This project was made using Java in Apache Netbeans.

## Features

This program is a music player that has been modeled on the Spotify user experience.

1. **Browse the song library:**
2. **Create a playlist:**
3. **Browse playlists:**
4. **Select a playlist:**
5. **Play a song:**
6. **Delete a playlist:**

## Requirements

This Java code satifies the professor requirements thorugh the following:

1. **GUI:** 
The project uses JavaFX for its GUI and has a Home.fxml and CreatePlaylist.fxml file for creating the interface.
It has multiple controls such as ListView, TableView, and various buttons for managing songs and playlists.
The application fetches data from the AccessDatabase.accdb file using JDBC and displays it in the GUI.
It fills the controls with data from the Library and Playlists tables in the database.
The applcation also utilizes fade animations to transition between song changes when presenting album art.
2. **SQL Database:**
The project uses a Microsoft Access database file (AccessDatabase.accdb) containing two tables: Library and Playlists.
It uses JDBC to interact with the database (DatabaseConnection.java).
3. **JSON:**
The application can import and export playlist data as JSON (Playlist.java).
The SongsJSON field in the Playlists table stores the list of songs as a JSON string. 
4. **Threads:**
The project does not currently utilize threads.
5. **Lambdas and Streams:**
The project uses lambdas in the event handlers for various controls (HomeController.java).
The project does not currently utilize streams.
6. **Collection Class:**
The application uses the ObservableList class for maintaining song lists and playlist data.

## How It Works

### Browse the song library

The HomeController class fetches song data from the Library table in the AccessDatabase.accdb file using the DatabaseConnection class.

The song data is then displayed in the TableView in the main interface, where users can see the song title, artist, and release year.

### Create a playlist

Click the "Create Playlist" button in the main interface to open the CreatePlaylist.fxml file.

The CreatePlaylist.fxml file defines the interface for creating a new playlist, including a TextField for the playlist name and a TableView for selecting songs.

In the CreatePlaylistController class, the song data from the Library table is fetched and displayed in the TableView.

Users can select songs by clicking on them in the TableView.

After selecting the desired songs, users can enter a name for the playlist in the TextField and click the "Create Playlist" button.

The CreatePlaylistController class will then create a Playlist object containing the selected songs and save it to the Playlists table in the AccessDatabase.accdb file as a JSON string.

### Browse playlists

The HomeController class fetches playlist data from the Playlists table in the AccessDatabase.accdb file.

The playlist data is then displayed in the ListView in the main interface, where users can see the playlist names.

### Select a playlist

Users can select a playlist by clicking on it in the ListView.

The HomeController class will fetch the songs in the selected playlist and display them in the TableView.

### Play a song

Users can play a song by selecting it in the TableView and clicking the "Play" button in the main interface.

Currently, the play functionality is not implemented in the provided code. You may consider using a JavaFX MediaPlayer for playing the selected song's audio file.

### Delete a playlist

Users can delete a playlist by selecting it in the ListView and clicking the "Delete Playlist" button in the main interface.

The HomeController class will then remove the selected playlist from the Playlists table in the AccessDatabase.accdb file.
