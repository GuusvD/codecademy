package database;

import java.sql.Connection;
import java.sql.DriverManager;

//Abstract class that creates a connection with the given database for further use in other classes
public abstract class ConnectToDatabase {
    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=CodecademyDB;integratedSecurity=true;");
            return conn;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
