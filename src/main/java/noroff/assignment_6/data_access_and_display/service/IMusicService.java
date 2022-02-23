package noroff.assignment_6.data_access_and_display.service;

import org.springframework.stereotype.Repository;

import java.util.Collection;

public interface IMusicService {
    // Method to get x random artists
    Collection<Object> getArtists(int limit);

    // Method to get x random songs
    Collection<Object> getSongs(int limit);

    // Method to get x random genres
    Collection<Object> getGenres(int limit);
}
