package domain;

public class Certificate {
    
    //Class attributes
    private int certificateID;
    private int certificateGrade;
    private int externalPersonID;
    private String externalPersonName;
    private String studentEmail;
    private String courseName;

    //Constructor
    public Certificate(int certificateID, int certificateGrade, int externalPersonID, String externalPersonName, String studentEmail, String courseName) {
        this.certificateID = certificateID;
        this.certificateGrade = certificateGrade;
        this.externalPersonID = externalPersonID;
        this.externalPersonName = externalPersonName;
        this.studentEmail = studentEmail;
        this.courseName = courseName;
    }

    //Overload constructor
    public Certificate(int certificateID, int certificateGrade, int externalPersonID, String studentEmail, String courseName) {
        this.certificateID = certificateID;
        this.certificateGrade = certificateGrade;
        this.externalPersonID = externalPersonID;
        this.studentEmail = studentEmail;
        this.courseName = courseName;
    }

    //Overload constructor
    public Certificate(int certificateGrade, int externalPersonID, String studentEmail, String courseName) {
        this.certificateGrade = certificateGrade;
        this.externalPersonID = externalPersonID;
        this.studentEmail = studentEmail;
        this.courseName = courseName;
    }

    //Getters
    public int getCertificateID() {
        return certificateID;
    }

    public int getCertificateGrade() {
        return certificateGrade;
    }

    public int getExternalPersonID() {
        return externalPersonID;
    }

    public String getExternalPersonName() {
        return externalPersonName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getCourseName() {
        return courseName;
    }
}
