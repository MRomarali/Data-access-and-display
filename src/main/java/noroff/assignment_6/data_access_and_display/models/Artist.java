package noroff.assignment_6.data_access_and_display.models;

public class Artist {
    String id;
    String name;

    public Artist(String id, String name){
        this.id = id;
        this.name = name;
    }
    public String getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }
}
