package domain;

import java.sql.Date;

public class Item {

    //Class attributes
    private int itemId;
    private String title;
    private String description;
    private Date publicationDate;
    private int externalPerson;
    private String status;

    //Constructor
    public Item(int itemId, String title, String description, Date publicationDate, int externalPerson, String status) {
        this.itemId = itemId;
        this.title = title;
        this.description = description;
        this.publicationDate = publicationDate;
        this.externalPerson = externalPerson;
        this.status = status;
    }

    //Getters
    public int getItemId() {
        return itemId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public int getExternalPerson() {
        return externalPerson;
    }

    public String getStatus() {
        return status;
    }
}
