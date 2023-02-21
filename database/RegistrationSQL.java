package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import domain.Course;
import domain.Item;
import domain.Module;
import domain.Registration;
import domain.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//Class that runs different queries on the Registration table in the connected database
public class RegistrationSQL extends ConnectToDatabase {

    //Method that returns all of the Registration records belonging to a Student in the Registration table in a ObservableList
    public ObservableList<Registration> getStudentRegistrationList(Student current_student) {
        ObservableList<Registration> studentRegistrationList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM Registration WHERE StudentEmail = '" + current_student.getEmail() + "'";
        Statement st;
        ResultSet rs;
        
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Registration registration;
            while(rs.next()) {
                registration = new Registration(rs.getDate("RegistrationDate"), rs.getString("StudentEmail"), rs.getString("CourseName"), rs.getInt("CertificateID"));
                studentRegistrationList.add(registration);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentRegistrationList;
    }

    //Method that returns a Course belonging to a given Registration
    public Course getStudentRegistrationCourseFromList(Registration registration) {
        Connection conn = getConnection();
        String query = "SELECT * FROM Registration INNER JOIN Course ON Registration.CourseName = Course.CourseName WHERE RegistrationDate = '" + registration.getRegistrationDate() + "' AND StudentEmail = '" + registration.getStudentEmail() + "' AND Registration.CourseName = '" + registration.getCourseName() + "'";
        Statement st;
        ResultSet rs;

        try {
            Course course;
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while(rs.next()) {
                course = new Course(rs.getString("CourseName"), rs.getString("CourseTopic"), rs.getString("CourseIntroduction"), rs.getString("CourseLevel"));
                return course;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Method that returns a ArrayList of Modules that belong to a given Registration
    public ArrayList<Module> getSpecificModules(Registration registration) {
        Connection conn = getConnection();
        ArrayList<Module> modules = new ArrayList<>();
        String query = "SELECT * FROM Registration INNER JOIN Course ON Registration.CourseName = Course.CourseName INNER JOIN Module ON Course.CourseName = Module.CourseName INNER JOIN Item ON Module.ItemID = Item.ItemID WHERE RegistrationDate = '" + registration.getRegistrationDate() + "' AND StudentEmail = '" + registration.getStudentEmail() + "' AND Registration.CourseName = '" + registration.getCourseName() + "'";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Module module;
            while(rs.next()){
                module = new Module(rs.getInt("ItemID"), rs.getString("ItemTitle"), rs.getString("ItemDescription"), rs.getDate("ItemPublicationDate"), rs.getInt("ExternalPersonID"), rs.getString("ItemStatus"), rs.getInt("ModuleSerialNumber"), rs.getString("ModuleVersion"));
                modules.add(module);
            }
            System.out.println("got the modules!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return modules;
    }

    //Method that returns the amount of views from a given Item and Registration
    public int getViews(Registration registration, Item item) {
        Connection conn = getConnection();
        String query = "SELECT * FROM Student_View_Item WHERE StudentEmail = '" + registration.getStudentEmail() + "' AND ItemID = " + item.getItemId();
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while(rs.next()) {
                int viewCount = rs.getInt("ItemViews");
                return viewCount;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //Method that returns the average amount of views from a given Item
    public int getAverageViews(Item item) {
        Connection conn = getConnection();
        String query = "SELECT (SELECT SUM(Student_View_Item.ItemViews) AS TotalViews FROM Student_View_Item INNER JOIN Item ON Student_View_Item.ItemID = Item.ItemID WHERE Item.ItemTitle = '" + item.getTitle() + "') / (SELECT COUNT(*) FROM Student_View_Item INNER JOIN Item ON Student_View_Item.ItemID = Item.ItemID WHERE Item.ItemTitle = '" + item.getTitle() + "') AS AverageViews";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while(rs.next()) {
                int averageViews = rs.getInt("AverageViews");
                return averageViews;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //Method that returns the itemprogress from a given Item and Registration
    public int getProgress(Registration registration, Item item) {
        Connection conn = getConnection();
        String query = "SELECT * FROM Student_View_Item WHERE StudentEmail = '" + registration.getStudentEmail() + "' AND ItemID = " + item.getItemId();
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while(rs.next()) {
                int itemProgress = rs.getInt("ItemProgress");
                return itemProgress;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //Method that returns the average itemprogress from a given Item
    public int getAverageProgress(Item item) {
        Connection conn = getConnection();
        String query = "SELECT (SELECT SUM(Student_View_Item.ItemProgress) AS TotalProgress FROM Student_View_Item INNER JOIN Item ON Student_View_Item.ItemID = Item.ItemID WHERE Item.ItemTitle = '" + item.getTitle() + "') / (SELECT COUNT(*) FROM Student_View_Item INNER JOIN Item ON Student_View_Item.ItemID = Item.ItemID WHERE Item.ItemTitle = '" + item.getTitle() + "') AS AverageProgress";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while(rs.next()) {
                int averageProgress = rs.getInt("AverageProgress");
                return averageProgress;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //Method that creates a new Registration record in the Registration table
    public void createRegistration(Registration registration) {
        Connection conn = getConnection();
        String query = "INSERT INTO Registration VALUES ('" + registration.getRegistrationDate() + "', '" + registration.getStudentEmail() + "', '" + registration.getCourseName() + "', NULL)";
        Statement st;

        try {
            st = conn.createStatement();
            st.executeQuery(query);
            System.out.println("Registration created!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Method that updates a existing Registration record in the Registration table
    public void updateRegistration(Registration old_registration, Registration registration) {
        Connection conn = getConnection();
        String query = "UPDATE Registration SET RegistrationDate = '" + registration.getRegistrationDate() + "', StudentEmail = '" + registration.getStudentEmail() + "', CourseName = '" + registration.getCourseName() + "', CertificateID = NULL WHERE RegistrationDate = '" + old_registration.getRegistrationDate() + "' AND StudentEmail = '" + old_registration.getStudentEmail() + "' AND CourseName = '" + old_registration.getCourseName() + "'";
        Statement st;

        try {
            st = conn.createStatement();
            st.executeQuery(query);
            System.out.println("Registration updated!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Method that deletes a existing Registration record in the Registration table
    public void deleteRegistration(Registration registration) {
        Connection conn = getConnection();
        String query = "DELETE FROM Registration WHERE RegistrationDate = '" + registration.getRegistrationDate() + "' AND StudentEmail = '" + registration.getStudentEmail() + "' AND CourseName = '" + registration.getCourseName() + "'";
        Statement st;

        try {
            st = conn.createStatement();
            st.executeQuery(query);
            System.out.println("Registration deleted!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
