package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import domain.Certificate;
import domain.ExternalPerson;
import domain.Item;

//Class that runs different queries on the ExternalPerson table in the connected database
public class ExternalPersonSQL extends ConnectToDatabase {

    //Method that returns the ID of a given ExternalPerson
    public int findExternalPersonID(String externalPersonName) {
        Connection conn = getConnection();
        HashMap<Integer, Integer> externalPersonIDList = new HashMap<>();
        String query = "SELECT ExternalPersonID FROM ExternalPerson WHERE ExternalPersonName = '" + externalPersonName + "'";
        Statement st;
        ResultSet rs;
        int id = 0;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            int externalPersonID;
            while(rs.next()){
                externalPersonID = rs.getInt("externalPersonID");
                externalPersonIDList.put(id, externalPersonID);
            }
            System.out.println("got the external person id!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return externalPersonIDList.get(id);
    }

    //Method that returns a ExternalPerson object that belongs to the given Item
    public ExternalPerson getExternalPersonById(Item item) {
        Connection conn = getConnection();
        String query = "SELECT * FROM ExternalPerson WHERE ExternalPersonID = '" + item.getExternalPerson() + "'";
        Statement st;
        ResultSet rs;
        ExternalPerson externalPerson;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while(rs.next()){
                externalPerson = new ExternalPerson(rs.getString("ExternalPersonName"), rs.getString("ExternalPersonEmail"), rs.getString("ExternalPersonOrganisation"), rs.getString("ExternalPersonRole"));
                return externalPerson;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Method that returns the name of the ExternalPerson that belongs to a given Certificate
    public String getEmployeeNameByIdWithCertificateParameter(Certificate certificate) {
        Connection conn = getConnection();
        String query = "SELECT ExternalPersonName FROM ExternalPerson WHERE ExternalPersonID = '" + certificate.getExternalPersonID() + "'";
        Statement st;
        ResultSet rs;
        String externalPersonName;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while(rs.next()){
                externalPersonName = new String(rs.getString("ExternalPersonName"));
                return externalPersonName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //Method that returns the name of the ExternalPerson that belongs to a given externalPersonId
    public String getEmployeeNameByIdWithIntegerParameter(int externalPersonID) {
        Connection conn = getConnection();
        String query = "SELECT ExternalPersonName FROM ExternalPerson WHERE ExternalPersonID = '" + externalPersonID + "'";
        Statement st;
        ResultSet rs;
        String externalPersonName;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while(rs.next()){
                externalPersonName = new String(rs.getString("ExternalPersonName"));
                return externalPersonName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //Method that returns all of the ExternalPerson names that have the Employee role in the form of a String Array
    public String[] getEmployeeExternalPersons() {
        Connection conn = getConnection();
        ArrayList<String> externalPersons = new ArrayList<>();
        String query = "SELECT ExternalPersonName FROM ExternalPerson WHERE ExternalPersonRole = 'Employee'";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            String externalPersonName;
            while(rs.next()){
                externalPersonName = new String(rs.getString("ExternalPersonName"));
                externalPersons.add(externalPersonName);
            }
            System.out.println("got the external persons!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] strExternalPersons = new String[externalPersons.size()];
        for(int i = 0; i < externalPersons.size(); i++){
            strExternalPersons[i] = externalPersons.get(i);
        }

        return strExternalPersons;

    }
}
