package noroff.assignment_6.data_access_and_display.service;

import noroff.assignment_6.data_access_and_display.data_access.ConnectionFactory;
import noroff.assignment_6.data_access_and_display.models.Artist;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class MusicService implements IMusicService {
    @Override
    public Collection<?> getArtists(int limit) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            // Set query
            String sqlQuery = "SELECT Name, GenreId FROM Genre ORDER BY random() LIMIT ?;";
            var statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, String.valueOf(limit));
            // Execute query 
            var resultSet = statement.executeQuery();
            var collection = new LinkedList<Artist>();
            // Get objects
            while(resultSet.next()){
                String id = resultSet.getString("GenreId");
                String name = resultSet.getString("Name");
                Artist artist = new Artist(id,name);
                collection.add(artist);
            }
            return collection;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.singleton("Request failed");
        }
    }

    @Override
    public Collection<Object> getSongs(int limit) {
        return null;
    }

    @Override
    public Collection<Object> getGenres(int limit) {
        return null;
    }
}
