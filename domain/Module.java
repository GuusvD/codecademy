package domain;

import java.sql.Date;

public class Module extends Item {
    
    //Class attributes
    private int serialNumber;
    private String version;

    //Constructor
    public Module(int itemId, String title, String description, Date publicationDate, int externalPerson, String status, int serialNumber, String version) {
        super(itemId, title, description, publicationDate, externalPerson, status);
        this.serialNumber = serialNumber;
        this.version = version;
    }

    //Getters
    public int getSerialNumber() {
        return serialNumber;
    }

    public String getVersion() {
        return version;
    }
}
