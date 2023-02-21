package domain;

public class Student {

    //Class attributes
    private String email;
    private String name;
    private int birthDay;
    private int birthMonth;
    private int birthYear;
    private String gender;
    private String street;
    private String houseNumber;
    private String houseNumberAddition;
    private String postalCode;
    private String residence;
    private String country;

    //Constructor
    public Student(String email, String name, int birthDay, int birthMonth, int birthYear, String gender, String street, String houseNumber, String houseNumberAddition, String postalCode, String residence, String country){
        this.email = email;
        this.name = name;
        this.birthDay = birthDay;
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;
        this.gender = gender;
        this.street = street;
        this.houseNumber = houseNumber;
        this.houseNumberAddition = houseNumberAddition;
        this.postalCode = postalCode;
        this.residence = residence;
        this.country = country;
    }

    //Getters
    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public int getBirthDay() {
        return birthDay;
    }

    public int getBirthMonth() {
        return birthMonth;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public String getGender() {
        return gender;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getHouseNumberAddition() {
        return houseNumberAddition;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getResidence() {
        return residence;
    }

    public String getCountry() {
        return country;
    }   
   
}