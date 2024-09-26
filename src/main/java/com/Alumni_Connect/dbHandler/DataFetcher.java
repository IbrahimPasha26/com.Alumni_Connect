package com.Alumni_Connect.dbHandler;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.Alumni_Connect.admin.Alumni;
import com.Alumni_Student.Student.Event;

public class DataFetcher {

    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String DB_USER = "System";
    private static final String DB_PASSWORD = "system";

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static String fetchPassword(String email) {
        String sql = "SELECT Password FROM Admins WHERE Email = ?";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Password");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Problem in Fetching Password");
            e.printStackTrace();
        }
        return "";
    }

    public static List<Alumni> fetchAlumniInfo() {
        String sql = "SELECT * FROM Alumni";
        List<Alumni> alumniList = new ArrayList<>();
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {
            while (resultSet.next()) {
                Alumni alumni = new Alumni();
                alumni.setAlumniId(resultSet.getInt("alumni_id"));
                alumni.setName(resultSet.getString("name"));
                alumni.setEmail(resultSet.getString("email"));
                alumni.setPhoneNumber(resultSet.getString("phone_number"));
                alumni.setYearOfGraduation(resultSet.getInt("year_of_graduation"));
                alumni.setCourse(resultSet.getString("course"));
                alumni.setCurrentJobTitle(resultSet.getString("current_job_title"));
                alumni.setCurrentCompany(resultSet.getString("current_company"));
                alumni.setLocation(resultSet.getString("location"));
                alumni.setBio(resultSet.getString("bio"));
                alumni.setProfilePicture(resultSet.getString("profile_picture"));

                alumniList.add(alumni);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error in Fetching Alumni Data");
            e.printStackTrace();
        }
        return alumniList;
    }

    public static String fetchNumber(String email) {
        String sql = "SELECT Phone_Number FROM Alumni WHERE Email = ?";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Phone_Number");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Problem in Fetching Phone Number");
            e.printStackTrace();
        }
        return "";
    }

    public static List<Alumni> fetchAlumniInfo(String email) {
        String sql = "SELECT * FROM Alumni WHERE Email = ?";
        List<Alumni> alumniList = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    Alumni alumni = new Alumni();
                    alumni.setAlumniId(resultSet.getInt("alumni_id"));
                    alumni.setName(resultSet.getString("name"));
                    alumni.setEmail(resultSet.getString("email"));
                    alumni.setPhoneNumber(resultSet.getString("phone_number"));
                    alumni.setYearOfGraduation(resultSet.getInt("year_of_graduation"));
                    alumni.setCourse(resultSet.getString("course"));
                    alumni.setCurrentJobTitle(resultSet.getString("current_job_title"));
                    alumni.setCurrentCompany(resultSet.getString("current_company"));
                    alumni.setLocation(resultSet.getString("location"));
                    alumni.setBio(resultSet.getString("bio"));
                    alumni.setProfilePicture(resultSet.getString("profile_picture"));

                    alumniList.add(alumni);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error in Fetching Alumni Data");
            e.printStackTrace();
        }
        return alumniList;
    }

    public static String fetchUserPassword(String username) {
        String sql = "SELECT PASSWORD FROM USERS WHERE USERNAME = ?";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("PASSWORD");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Problem in Fetching User Password");
            e.printStackTrace();
        }
        return "";
    }
    
    public static List<Event> fetchEventsForAlumni(int alumniId) {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT E.EVENT_ID, E.EVENT_NAME, E.EVENT_DATE, E.EVENT_DESCRIPTION " +
                     "FROM EVENTS E " +
                     "JOIN ALUMNIEVENTS AE ON E.EVENT_ID = AE.EVENT_ID " +
                     "WHERE AE.ALUMNI_ID = ?";

        try (Connection con = DataInjector.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, alumniId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Event event = new Event();
                event.setEventId(rs.getInt("EVENT_ID"));
                event.setEventName(rs.getString("EVENT_NAME"));

                // Convert SQL Date to LocalDate
                Date sqlDate = rs.getDate("EVENT_DATE");
                LocalDate localDate = sqlDate.toLocalDate();
                event.setEventDate(localDate);

                event.setEventDescription(rs.getString("EVENT_DESCRIPTION"));
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return events;
    }

 // Method to fetch all events with associated alumni
    public static List<Event> fetchAllEventsWithAlumni() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT E.EVENT_ID, E.EVENT_NAME, E.EVENT_DATE, E.EVENT_DESCRIPTION, "
                   + "A.ALUMNI_ID, A.NAME, A.EMAIL, A.CURRENT_JOB_TITLE, A.CURRENT_COMPANY "
                   + "FROM EVENTS E "
                   + "JOIN ALUMNIEVENTS AE ON E.EVENT_ID = AE.EVENT_ID "
                   + "JOIN ALUMNI A ON AE.ALUMNI_ID = A.ALUMNI_ID";

        try (Connection con = DataInjector.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Event event = new Event();
                event.setEventId(rs.getInt("EVENT_ID"));
                event.setEventName(rs.getString("EVENT_NAME"));

                // Convert SQL Date to LocalDate
                Date sqlDate = rs.getDate("EVENT_DATE");
                if (sqlDate != null) {
                    LocalDate localDate = sqlDate.toLocalDate();
                    event.setEventDate(localDate);
                }

                event.setEventDescription(rs.getString("EVENT_DESCRIPTION"));

                Alumni alumni = new Alumni();
                alumni.setAlumniId(rs.getInt("ALUMNI_ID"));
                alumni.setName(rs.getString("NAME"));
                alumni.setEmail(rs.getString("EMAIL"));
                alumni.setCurrentJobTitle(rs.getString("CURRENT_JOB_TITLE"));
                alumni.setCurrentCompany(rs.getString("CURRENT_COMPANY"));

                event.setAlumni(alumni);
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return events;
    }
}
