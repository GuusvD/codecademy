package database;

import domain.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//Class that runs different queries on the Student table in the connected database
public class StudentSQL extends ConnectToDatabase {

    //Method that returns all of the Student records in the Student table in a ObservableList
    public ObservableList<Student> getStudentList() {
        ObservableList<Student> studentList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM Student";
        Statement st;
        ResultSet rs;
        
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Student student;
            while(rs.next()) {
                student = new Student(rs.getString("StudentEmail"), rs.getString("StudentName"), rs.getInt("StudentBirthDay"), rs.getInt("StudentBirthMonth"), rs.getInt("StudentBirthYear"), rs.getString("StudentGender"), rs.getString("StudentStreet"), rs.getString("StudentHouseNumber"), rs.getString("StudentHouseNumberAddition"), rs.getString("StudentPostalCode"), rs.getString("StudentResidence"), rs.getString("StudentCountry"));
                studentList.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentList;
    }

    //Method that creates a new Student record in the Student table
    public void createStudent(Student student) {
        Connection conn = getConnection();
        String query = "INSERT INTO Student VALUES ('" + student.getEmail() + "', '" + student.getName() + "', '" + student.getBirthDay() + "', '" + student.getBirthMonth() + "', '" + student.getBirthYear() + "', '" + student.getGender() + "', '" + student.getStreet() + "', '" + student.getHouseNumber() + "', '" + student.getHouseNumberAddition() + "', '" + student.getPostalCode() + "', '" + student.getResidence()  + "', '" + student.getCountry() + "')";
        Statement st;

        try {
            st = conn.createStatement();
            st.executeQuery(query);
            System.out.println("Student created!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Method that updates a existing Student record in the Student table
    public void updateStudent(Student student, Student old_student) {
        Connection conn = getConnection();
        String query = "UPDATE Student SET StudentEmail = '" + student.getEmail() + "', StudentName = '" + student.getName() + "', StudentBirthDay = '" + student.getBirthDay() + "', StudentBirthMonth = '" + student.getBirthMonth() + "', StudentBirthYear = '" + student.getBirthYear() + "', StudentGender = '" + student.getGender() + "', StudentStreet = '" + student.getStreet() + "', StudentHouseNumber = '" + student.getHouseNumber() + "', StudentHouseNumberAddition = '" + student.getHouseNumberAddition() + "', StudentPostalCode = '" + student.getPostalCode() + "', StudentResidence = '" + student.getResidence()  + "', StudentCountry = '" + student.getCountry() + "' WHERE StudentEmail = '" + old_student.getEmail() + "'";
        Statement st;

        try {
            st = conn.createStatement();
            st.executeQuery(query);
            System.out.println("Student updated!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Method that deletes a existing Student record in the Student table
    public void deleteStudent(Student student) {
        Connection conn = getConnection();
        String query = "DELETE FROM Student WHERE StudentEmail = '" + student.getEmail() + "'";
        Statement st;
        try {
            st = conn.createStatement();
            st.executeQuery(query);
            System.out.println("Student deleted!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
