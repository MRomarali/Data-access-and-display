package noroff.assignment_6.data_access_and_display.models;

public class Song {
    String id;
    String name;
    String albumId;
    String mediaTypeId;
    String genreId;
    String composer;
    String milliseconds;
    String bytes;
    String unitPrice;

    public Song(String id, String name, String albumId, String mediaTypeId, String genreId, String composer, String milliseconds, String bytes, String unitPrice) {
        this.id = id;
        this.name = name;
        this.albumId = albumId;
        this.mediaTypeId = mediaTypeId;
        this.genreId = genreId;
        this.composer = composer;
        this.milliseconds = milliseconds;
        this.bytes = bytes;
        this.unitPrice = unitPrice;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAlbumId() {
        return albumId;
    }

    public String getMediaTypeId() {
        return mediaTypeId;
    }

    public String getGenreId() {
        return genreId;
    }

    public String getComposer() {
        return composer;
    }

    public String getMilliseconds() {
        return milliseconds;
    }

    public String getBytes() {
        return bytes;
    }

    public String getUnitPrice() {
        return unitPrice;
    }
}
