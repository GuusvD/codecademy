package database;

import domain.Course;
import domain.Module;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//Class that runs different queries on the Course table in the connected database
public class CourseSQL extends ConnectToDatabase {

    //Method that returns all of the Course records in the Course table in a ObservableList
    public ObservableList<Course> getCourseList() {
        ObservableList<Course> courseList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM Course";
        Statement st;
        ResultSet rs;
        
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Course course;
            while(rs.next()) {
                course = new Course(rs.getString("CourseName"), rs.getString("CourseTopic"), rs.getString("CourseIntroduction"), rs.getString("CourseLevel"));
                courseList.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courseList;
    }

    //Method that creates a new Course record in the Course table
    public void createCourse(Course course) {
        Connection conn = getConnection();
        String query = "INSERT INTO Course VALUES ('" + course.getName() + "', '" + course.getTopic() + "', '" + course.getIntroduction() + "', '" + course.getLevel() + "')";
        Statement st;

        try {
            st = conn.createStatement();
            st.executeQuery(query);
            System.out.println("Course created!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Method that updates a existing Course record in the Course table
    public void updateCourse(Course course, Course old_course) {
        Connection conn = getConnection();
        String query = "UPDATE Course SET CourseName = '" + course.getName() + "', CourseTopic = '" + course.getTopic() + "', CourseIntroduction = '" + course.getIntroduction() + "', CourseLevel = '" + course.getLevel() + "' WHERE CourseName = '" + old_course.getName() + "'";
        Statement st;

        try {
            st = conn.createStatement();
            st.executeQuery(query);
            System.out.println("Course updated!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Method that deletes a existing Course record in the Course table
    public String deleteCourse(Course course) {

        Connection conn = getConnection();
        String query1 = "SELECT COUNT(*) AS NumberOfStudents FROM Registration WHERE CourseName = '" + course.getName() + "'";
        Statement st;
        ResultSet rs;
        int participants = 0;
        try {
            st= conn.createStatement();
            rs = st.executeQuery(query1);
            
            while(rs.next()){
                participants = rs.getInt("NumberOfStudents");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(participants == 0){
            String query2 = "DELETE FROM Course WHERE CourseName = '" + course.getName() + "'";
            try {
                st = conn.createStatement();
                st.executeQuery(query2);
                return "Course deleted" + course.getName() + "!";
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return "Can't delete course because there are still " + participants + " participants in " + course.getName() + "";
        }

        return "There was a mistake with deleting " + course.getName();
    }

    //Method that returns all of the Course names in a String Array
    public String[] getCourses() {
        Connection conn = getConnection();
        ArrayList<String> courses = new ArrayList<>();
        String query = "SELECT CourseName FROM Course";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            String courseName;
            while(rs.next()){
                courseName = new String(rs.getString("CourseName"));
                courses.add(courseName);
            }
            System.out.println("got the courses!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] strCourses = new String[courses.size()];
        for(int i = 0; i < courses.size(); i++){
            strCourses[i] = courses.get(i);
        }

        return strCourses;

    }

    //Method that returns all of the Module names in a String Array
    public String[] getModules() {
        Connection conn = getConnection();
        ArrayList<String> modules = new ArrayList<>();
        String query = "SELECT ItemTitle FROM Item JOIN Module ON Item.ItemID = Module.ItemID WHERE Module.CourseName IS NULL";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            String moduleTitle;
            while(rs.next()){
                moduleTitle = new String(rs.getString("ItemTitle"));
                modules.add(moduleTitle);
            }
            System.out.println("got the modules!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] strModules = new String[modules.size()];
        for(int i = 0; i < modules.size(); i++){
            strModules[i] = modules.get(i);
        }

        return strModules;
    }

    //Method that returns the Modules belonging to a given Course in a ArrayList
    public ArrayList<Module> getSpecificModules(Course course) {
        Connection conn = getConnection();
        ArrayList<Module> modules = new ArrayList<>();
        String query = "SELECT * FROM Item JOIN Module ON Item.ItemID = Module.ItemID WHERE Module.CourseName = '" + course.getName() + "'";
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

    //Method that changes the name of a Course that belongs to a given Item to a given name
    public void setCourseName(String itemTitle, String CourseName){
        Connection conn = getConnection();
        String query = "UPDATE Module SET Module.CourseName = '" + CourseName + "'FROM Module INNER JOIN Item ON Module.ItemID = Item.ItemID WHERE Item.ItemTitle = '" + itemTitle + "'";
        Statement st;
        
        try {
            st = conn.createStatement();
            st.executeQuery(query);
            System.out.println("CourseName added!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Method that changes the name of a Course that belongs to a given Item to NULL
    public void setNull(String itemTitle){
        Connection conn = getConnection();
        String query = "UPDATE Module SET Module.CourseName = NULL FROM Module INNER JOIN Item ON Module.ItemID = Item.ItemID WHERE Item.ItemTitle = ('" + itemTitle + "')";
        Statement st;
        try {
            st = conn.createStatement();
            st.executeQuery(query);
            System.out.println("CourseName is NULL!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Method that returns all of the Courses that have a similar topic as the topic of the given Course
    public ArrayList<Course> relevantCourses(Course course){
        Connection conn = getConnection();
        ArrayList<Course> relCourses = new ArrayList<>();
        String query = "SELECT * FROM Course WHERE CourseTopic = '" + course.getTopic() + "'";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Course relcourse;
            while(rs.next()){
                relcourse = new Course(rs.getString("CourseName"), rs.getString("CourseTopic"), rs.getString("CourseIntroduction"), rs.getString("CourseLevel"));
                if(!relcourse.getName().equals(course.getName())){
                    relCourses.add(relcourse);
                }
            }
            System.out.println("got the courses!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return relCourses;
    }

    //Method that returns the total of obtained certificates belonging to a given Course
    public int obtainedCertificates(Course course) {
        Connection conn = getConnection();
        String query = "SELECT Course.CourseName, COUNT(Registration.CertificateID) AS Total FROM Course INNER JOIN Registration ON Course.CourseName = Registration.CourseName GROUP BY Course.CourseName HAVING Course.CourseName = '" + course.getName() + "' ORDER BY Total DESC";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            int obtainedCertificates;
            while(rs.next()){
                obtainedCertificates = rs.getInt("Total");
                return obtainedCertificates;
            }
            System.out.println("got the certificates!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
