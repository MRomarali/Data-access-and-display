package noroff.assignment_6.data_access_and_display.service;

import noroff.assignment_6.data_access_and_display.data_access.ConnectionFactory;
import noroff.assignment_6.data_access_and_display.models.Artist;
import noroff.assignment_6.data_access_and_display.models.Genre;
import noroff.assignment_6.data_access_and_display.models.Song;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
@Service
public class MusicService implements IMusicService {
    @Override
    public Collection<Artist> getArtists(int limit) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            // Set query
            String sqlQuery = "SELECT Name, ArtistId FROM Artist ORDER BY random() LIMIT ?;";
            var statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, String.valueOf(limit));
            // Execute query
            var resultSet = statement.executeQuery();
            var collection = new LinkedList<Artist>();
            // Get objects
            while(resultSet.next()){
                String id = resultSet.getString("ArtistId");
                String name = resultSet.getString("Name");
                Artist artist = new Artist(id,name);
                collection.add(artist);
            }
            return collection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<Song> getSongs(int limit) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            // Set query
            String sqlQuery = "select Track.TrackId as \"id\",Track.Name as \"name\", Artist.Name as \"artist\", Album.Title as \"album\", Genre.Name as \"genre\" from Track\n" +
                    "                            join Album on Track.AlbumId = Album.AlbumId\n" +
                    "                            join Artist on Album.ArtistId = Artist.ArtistId\n" +
                    "                            join Genre on Genre.GenreId = Track.GenreId ORDER BY random() LIMIT ?;";
            var statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, String.valueOf(limit));
            // Execute query
            var resultSet = statement.executeQuery();
            var collection = new LinkedList<Song>();
            // Get objects
            while(resultSet.next()){
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String artist = resultSet.getString("artist");
                String album = resultSet.getString("album");
                String genre = resultSet.getString("genre");
                var song = new Song(id,name,artist,album,genre);
                collection.add(song);
            }
            return collection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<Genre> getGenres(int limit) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            // Set query
            String sqlQuery = "SELECT Name, GenreId FROM Genre ORDER BY random() LIMIT ?;";
            var statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, String.valueOf(limit));
            // Execute query
            var resultSet = statement.executeQuery();
            var collection = new LinkedList<Genre>();
            // Get objects
            while(resultSet.next()){
                String id = resultSet.getString("GenreId");
                String name = resultSet.getString("Name");
                var genre = new Genre(id,name);
                collection.add(genre);
            }
            return collection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<Song> getSongs(String songName) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            // Set query
            String sqlQuery = """
                    select Track.TrackId as "id",Track.Name as "name", Artist.Name as "artist", Album.Title as "album", Genre.Name as "genre" from Track
                            join Album on Track.AlbumId = Album.AlbumId
                            join Artist on Album.ArtistId = Artist.ArtistId
                            join Genre on Genre.GenreId = Track.GenreId where Track.Name like ?;""";
            var statement = connection.prepareStatement(sqlQuery);
            String searchTerm = "%"+songName+"%";
            statement.setString(1,searchTerm);
            // Execute query
            var resultSet = statement.executeQuery();
            var collection = new LinkedList<Song>();
            // Get objects
            while(resultSet.next()){
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String artist = resultSet.getString("artist");
                String album = resultSet.getString("album");
                String genre = resultSet.getString("genre");

                var song = new Song(id,name,artist,album,genre);
                collection.add(song);
            }
            return collection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

