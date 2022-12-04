package edu.farmingdale.csc311_finalproj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Alexander
 */
public class DatabaseConnection {

    public static Connection connectDB() {
        try {
            String databaseURL = "jdbc:ucanaccess://.//AccessDatabase.accdb";
            Connection conn = DriverManager.getConnection(databaseURL);
            return conn;
        } catch (SQLException ex) {
            System.out.println("CAUGHT ERROR: Failed Database Connection!");
            return null;
        }
    }
}
