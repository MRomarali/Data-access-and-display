package noroff.assignment_6.data_access_and_display.Models;

public class Genre {
    String id;
    String name;

    public Genre(String id, String name){
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
