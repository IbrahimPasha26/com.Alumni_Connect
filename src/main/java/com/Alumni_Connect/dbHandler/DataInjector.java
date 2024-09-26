package com.Alumni_Connect.dbHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataInjector {

    private static final String DRIVER_PATH = "oracle.jdbc.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USERNAME = "System";
    private static final String PASSWORD = "system";
    private static final Logger LOGGER = Logger.getLogger(DataInjector.class.getName());
    // Method to add a new alumni record
    public static boolean addAlumni(String name, String email, String phno, int yrOfgrad, String course,
                                    String curJobTitle, String curCompany, String location, String bio, String picPath) 
    {
        String sql = "INSERT INTO Alumni (NAME, EMAIL, PHONE_NUMBER, YEAR_OF_GRADUATION, COURSE, " +
                     "CURRENT_JOB_TITLE, CURRENT_COMPANY, LOCATION, BIO, PROFILE_PICTURE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        boolean result = false;

        try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            //pstmt.setInt(1, id);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phno);
            pstmt.setInt(4, yrOfgrad);
            pstmt.setString(5, course);
            pstmt.setString(6, curJobTitle);
            pstmt.setString(7, curCompany);
            pstmt.setString(8, location);
            pstmt.setString(9, bio);
            pstmt.setString(10, picPath);

            int rowsInserted = pstmt.executeUpdate();
            result = rowsInserted > 0;
        } catch (SQLException e) {
            handleSQLException(e, "addAlumni");
        }

        return result;
    }

 // Method to remove an alumni record by ID
    public static boolean removeAlumni(int alumniId) {
        String sql = "DELETE FROM Alumni WHERE ALUMNI_ID = ?";
        boolean isRemoved = false;

        try (Connection con = getConnection(); 
             PreparedStatement pstmt = con.prepareStatement(sql)) {
             
            pstmt.setInt(1, alumniId);
            int rowsAffected = pstmt.executeUpdate();
            isRemoved = rowsAffected > 0;
            
        } catch (SQLException e) {
            // Log the exception details
            LOGGER.log(Level.SEVERE, "SQL error while trying to remove alumni", e);
        }

        return isRemoved;
    }
    
    // Method to add a new student record
    public static boolean addStudent(String fname, String lname, String email, String phno, String yrofenrol,
                                     String course, String cur_stat, String graduationyr, String photo) {
        String sql = "INSERT INTO STUDENTS (FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, ENROLLMENT_YEAR, " +
                     "COURSE, CURRENT_STATUS, GRADUATION_YEAR, PROFILE_PICTURE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        boolean result = false;

        try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {

            // Check for duplicate email before attempting to insert.
            if (isDuplicate(con, "EMAIL", email)) {
                System.out.println("Duplicate entry detected for EMAIL: " + email + ". Skipping this entry.");
                return false;
            }

            // Continue with insertion if no duplicates are found.
            pstmt.setString(1, fname);
            pstmt.setString(2, lname);
            pstmt.setString(3, email);
            pstmt.setString(4, phno);
            pstmt.setString(5, yrofenrol);
            pstmt.setString(6, course);
            pstmt.setString(7, cur_stat);
            pstmt.setString(8, graduationyr);
            pstmt.setString(9, photo);

            int rowsInserted = pstmt.executeUpdate();
            result = rowsInserted > 0;
        } catch (SQLException e) {
            handleSQLException(e, "addStudent");
        }

        return result;
    }

    // Method to check if a user exists by username or email
    public static boolean isUserExist(String uname, String email) 
    {
        String sql = "SELECT COUNT(*) FROM Users WHERE USERNAME = ? OR EMAIL = ?";
        boolean exists = false;

        try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) 
        {
            pstmt.setString(1, uname);
            pstmt.setString(2, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) 
            {
                exists = rs.getInt(1) > 0; // If count > 0, then the user already exists
            }
        } 
        catch (SQLException e) 
        {
            handleSQLException(e, "isUserExist");
        }

        return exists;
    }

    // Method to update an alumni record
    public static boolean updateAlumni(String email, String firstName, String lastName, String phoneNumber,
                                       String course, String graduationYear, String profilePicture) {
        boolean result = true;  // Start assuming success, and only fail if all updates fail.

        // Attempt to update each field individually, catching and handling duplicates.
        result &= updateField(email, "NAME", firstName + " " + lastName);
        result &= updateField(email, "PHONE_NUMBER", phoneNumber);
        result &= updateField(email, "COURSE", course);
        result &= updateField(email, "YEAR_OF_GRADUATION", graduationYear);
        result &= updateField(email, "PROFILE_PICTURE", profilePicture);

        return result;
    }

    // Method to add a new user record
    public static boolean addUser(String uname, String password, String email, String fname, String address)
    {
        String sql = "Insert into Users (USERNAME, PASSWORD, EMAIL, FULL_NAME, ADDRESS) Values (?, ?, ?, ?, ?)";
        boolean result = false;
        try(Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setString(1, uname);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            pstmt.setString(4, fname);
            pstmt.setString(5, address);
            int row = pstmt.executeUpdate();
            result = row > 0;
        }
        catch (SQLException e) 
        {
            System.out.println("Problem in Adding User ");
            e.printStackTrace();
        }
        return result;
    }

    // Method to add a new contact form entry
    public static boolean addContactFormEntry(String name, String email, String phoneNumber, String message) {
        String sql = "INSERT INTO contact_form (NAME, EMAIL, PHONE_NUMBER, MESSAGE) VALUES (?, ?, ?, ?)";
        boolean result = false;

        try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phoneNumber);
            pstmt.setString(4, message);

            int rowsInserted = pstmt.executeUpdate();
            result = rowsInserted > 0;
        } catch (SQLException e) {
            handleSQLException(e, "addContactFormEntry");
        }

        return result;
    }

    // Method to add a new event
    public int addEvent(String eventName, String eventDate, String eventDescription) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int eventId = -1;

        try {
            con = getConnection();
            String insertQuery = "INSERT INTO EVENTS (EVENT_NAME, EVENT_DATE, EVENT_DESCRIPTION) VALUES (?, TO_DATE(?, 'YYYY-MM-DD'), ?)";
            pstmt = con.prepareStatement(insertQuery, new String[] { "EVENT_ID" });
            pstmt.setString(1, eventName);
            pstmt.setString(2, eventDate);
            pstmt.setString(3, eventDescription);
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                eventId = rs.getInt(1);
            }
        } catch (SQLException e) {
            handleSQLException(e, "addEvent");
        } finally {
            closeResources(rs, pstmt, con);
        }
        return eventId;
    }

    // Method to assign event to alumni
    public void assignEventToAlumni(int eventId, String[] alumniIds) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            String insertQuery = "INSERT INTO ALUMNIEVENTS (ALUMNI_ID, EVENT_ID) VALUES (?, ?)";
            pstmt = con.prepareStatement(insertQuery);

            for (String alumniId : alumniIds) {
                pstmt.setInt(1, Integer.parseInt(alumniId));
                pstmt.setInt(2, eventId);
                pstmt.addBatch();
            }

            pstmt.executeBatch();
        } catch (SQLException e) {
            handleSQLException(e, "assignEventToAlumni");
        } finally {
            closeResources(null, pstmt, con);
        }
    }

    // Helper method to update a specific field in the alumni record
    private static boolean updateField(String email, String fieldName, String value) {
        String sql = "UPDATE ALUMNI SET " + fieldName + " = ? WHERE EMAIL = ?";
        boolean result = true;  // Assume success unless proven otherwise.

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, value);
            pstmt.setString(2, email);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("No rows updated for field " + fieldName + " with email " + email);
                result = false;
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1) { // Unique constraint violated
                System.out.println("Duplicate entry detected for " + fieldName + " with email " + email + ". Skipping this update.");
            } else {
                handleSQLException(e, "updateField (" + fieldName + ")");
                result = false;
            }
        }

        return result;
    }

    // Helper method to check for duplicate entries
    private static boolean isDuplicate(Connection con, String columnName, String value) throws SQLException {
        String sql = "SELECT COUNT(*) FROM STUDENTS WHERE " + columnName + " = ?";
        boolean isDuplicate = false;

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, value);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                isDuplicate = rs.getInt(1) > 0;
            }
        }

        return isDuplicate;
    }

    // Method to get a database connection
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName(DRIVER_PATH);
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    // Helper method to handle SQL exceptions
    private static void handleSQLException(SQLException e, String methodName) {
        System.out.println("Error occurred in " + methodName + ": " + e.getMessage());
        e.printStackTrace();
    }

    // Helper method to close database resources
    private static void closeResources(ResultSet rs, PreparedStatement pstmt, Connection con) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
