package noroff.assignment_6.data_access_and_display.service;

import noroff.assignment_6.data_access_and_display.models.Artist;
import noroff.assignment_6.data_access_and_display.models.Genre;
import noroff.assignment_6.data_access_and_display.models.Song;
import org.springframework.stereotype.Repository;

import java.util.Collection;
@Repository
public interface IMusicService {
    // Method to get x random artists
    Collection<Artist> getArtists(int limit);

    // Method to get x random songs
    Collection<Song> getSongs(int limit);

    // Method to get x random genres
    Collection<Genre> getGenres(int limit);

    Collection<Song> getSongs(String name);
}
