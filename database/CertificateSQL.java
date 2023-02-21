package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import domain.Certificate;
import domain.Course;
import domain.Registration;
import domain.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//Class that runs different queries on the Certificate table in the connected database
public class CertificateSQL extends ConnectToDatabase {
    private ExternalPersonSQL sql = new ExternalPersonSQL();

    //Method that returns all of the Certificate records belonging to a given Student
    public ObservableList<Certificate> getCertificateListFromStudent(Student student) {
        ObservableList<Certificate> certificateList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM Certificate INNER JOIN Registration ON Certificate.CertificateID = Registration.CertificateID WHERE Registration.StudentEmail = '" + student.getEmail() + "'";
        Statement st;
        ResultSet rs;
        
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Certificate certificate;
            while(rs.next()) {
                certificate = new Certificate(rs.getInt("CertificateID"), rs.getInt("CertificateGrade"), rs.getInt("ExternalPersonID"), sql.getEmployeeNameByIdWithIntegerParameter(rs.getInt("ExternalPersonID")), rs.getString("StudentEmail"), rs.getString("CourseName"));
                certificateList.add(certificate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return certificateList;
    }

    //Method that returns all of the Certificate records belonging to a given Course and Student
    public Certificate[] getCertificatesFromStudentForSpecificCourse(Course course, Student student) {
        Connection conn = getConnection();
        ArrayList<Certificate> certificates = new ArrayList<>();
        String query = "SELECT * FROM Certificate INNER JOIN Registration ON Certificate.CertificateID = Registration.CertificateID WHERE Registration.CourseName = '" + course.getName() + "' AND Registration.StudentEmail = '" + student.getEmail() + "'";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Certificate certificate;
            while(rs.next()){
                certificate = new Certificate(rs.getInt("CertificateID"), rs.getInt("CertificateGrade"), rs.getInt("ExternalPersonID"), rs.getString("StudentEmail"), rs.getString("CourseName"));
                certificates.add(certificate);
            }
            System.out.println("got the certificates!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Certificate[] strCertificates = new Certificate[certificates.size()];
        for(int i = 0; i < certificates.size(); i++){
            strCertificates[i] = certificates.get(i);
        }

        return strCertificates;

    }

    //Method that returns a single Certificate record belonging to a given Course and Student
    public Certificate getSingleCertificateFromStudentForSpecificCourse(Course course, Student student) {
        Connection conn = getConnection();
        String query = "SELECT * FROM Certificate WHERE Certificate.CourseName = '" + course.getName() + "' AND Certificate.StudentEmail = '" + student.getEmail() + "'";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Certificate certificate;
            while(rs.next()){
                certificate = new Certificate(rs.getInt("CertificateID"), rs.getInt("CertificateGrade"), rs.getInt("ExternalPersonID"), rs.getString("StudentEmail"), rs.getString("CourseName"));
                return certificate;
            }
            System.out.println("got the certificate!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    //Method that checks if a given Registration has a linked Certificate and if the Student has finished all their modules
    public String checkIfStudentReceiveCertificate(Certificate certificate, Registration registration) {
        Connection conn = getConnection();
        String querySelect = "SELECT (SELECT SUM(ItemProgress) FROM Registration INNER JOIN Student_View_Item ON Registration.StudentEmail = Student_View_Item.StudentEmail INNER JOIN Item ON Student_View_Item.ItemID = Item.ItemID INNER JOIN Module ON Item.ItemID = Module.ItemID WHERE Registration.StudentEmail = '" + certificate.getStudentEmail() + "' AND Registration.CourseName = '" + certificate.getCourseName()  + "' AND Module.CourseName = '" + certificate.getCourseName() + "' AND NOT Item.ItemStatus = 'CONCEPT') / (SELECT COUNT(*) FROM Registration INNER JOIN Student_View_Item ON Registration.StudentEmail = Student_View_Item.StudentEmail INNER JOIN Item ON Student_View_Item.ItemID = Item.ItemID INNER JOIN Module ON Item.ItemID = Module.ItemID WHERE Registration.StudentEmail = '" + certificate.getStudentEmail() + "' AND Registration.CourseName = '" + certificate.getCourseName() + "' AND Module.CourseName = '" + certificate.getCourseName() + "' AND NOT Item.ItemStatus = 'CONCEPT') AS AverageProgress";
        String queryInsert = "UPDATE Registration SET RegistrationDate = '" + registration.getRegistrationDate() + "', StudentEmail = '" + registration.getStudentEmail() + "', CourseName = '" + registration.getCourseName() + "', CertificateID = '" + certificate.getCertificateID() + "' WHERE RegistrationDate = '" + registration.getRegistrationDate() + "' AND StudentEmail = '" + registration.getStudentEmail() + "' AND CourseName = '" + registration.getCourseName() + "'";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(querySelect);
            while(rs.next()){
                if(rs.getInt("AverageProgress") == 100) {
                    st = conn.createStatement();
                    st.executeQuery(queryInsert);
                    return "Certificate has been linked to the Student";
                } else {
                    System.out.println("Oops");
                    return "Certificate cannot be linked to the student yet";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "There has occured a error in the linking process";
    }

    //Method that creates a new Certificate record in the Certificate table
    public String createCertificate(Certificate certificate) {
        Connection conn = getConnection();
        String query = "INSERT INTO Certificate VALUES ('" + certificate.getCertificateGrade() + "', '" + certificate.getExternalPersonID() + "', '" + certificate.getStudentEmail() + "', '" + certificate.getCourseName() + "')";
        String checkQuery = "SELECT COUNT(*) AS TotalRecords FROM Certificate WHERE StudentEmail = '" + certificate.getStudentEmail() + "' AND CourseName = '" + certificate.getCourseName() + "'";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(checkQuery);
            while(rs.next()) {
                if(rs.getInt("TotalRecords") < 1) {
                    st.executeQuery(query);
                    System.out.println("Certificate created!");
                    return "Certificate has been created";
                }
            }
            return "You've already created a certificate for this student/course!";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "There was an error creating the certificate";
    }

    //Method that updates a existing Certificate record in the Certificate table
    public void updateCertificate(Certificate certificate) {
        Connection conn = getConnection();
        String query = "UPDATE Certificate SET CertificateGrade = '" + certificate.getCertificateGrade() + "', ExternalPersonID = '" + certificate.getExternalPersonID() + "', StudentEmail = '" + certificate.getStudentEmail() + "', CourseName = '" + certificate.getCourseName() + "' WHERE CertificateID = '" + certificate.getCertificateID() + "'";
        Statement st;

        try {
            st = conn.createStatement();
            st.executeQuery(query);
            System.out.println("Certificate updated!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Method that set the certificateID to null in the Registration table
    public void setCertificateIDtoNull(Certificate certificate) {
        Connection conn = getConnection();
        String queryUpdate = "UPDATE Registration SET CertificateID = NULL WHERE CertificateID = '" + certificate.getCertificateID() + "'";
        Statement st;

        try {
            st = conn.createStatement();
            st.executeQuery(queryUpdate);
            System.out.println("CertificateID set null!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Method that deletes a existing Certificate record in the Certificate table
    public void deleteCertificate(Certificate certificate) {
        Connection conn = getConnection();
        String queryDelete = "DELETE FROM Certificate WHERE CertificateID = '" + certificate.getCertificateID() + "'";
        Statement st;

        try {
            setCertificateIDtoNull(certificate);
            st = conn.createStatement();
            st.executeQuery(queryDelete);
            System.out.println("Certificate deleted!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
