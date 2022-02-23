package noroff.assignment_6.data_access_and_display.service;

import org.springframework.stereotype.Repository;

import java.util.Collection;

public interface IMusicService {
    // Method to get x random artists
    Collection<?> getArtists(int limit);

    // Method to get x random songs
    Collection<?> getSongs(int limit);

    // Method to get x random genres
    Collection<?> getGenres(int limit);
}