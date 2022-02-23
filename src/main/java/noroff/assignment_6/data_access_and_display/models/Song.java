package noroff.assignment_6.data_access_and_display.models;

public class Song {
    String id;
    String name;
    String artist;
    String album;
    String genre;

    public Song(String id, String name, String artist, String album, String genre) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
