package domain;

import java.sql.Date;

public class Webcast extends Item {

    //Class attributes
    private int duration;
    private String url;

    //Constructor
    public Webcast(int itemId, String title, String description, Date publicationDate, int externalPerson, String status, int duration, String url) {
        super(itemId, title, description, publicationDate, externalPerson, status);
        this.duration = duration;
        this.url = url;
    }
    
    //Getters
    public int getDuration() {
        return duration;
    }

    public String getUrl() {
        return url;
    }
}
