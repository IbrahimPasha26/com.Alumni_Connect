package com.Alumni_Connect.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AlumniSummaryServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(AlumniSummaryServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Received request for alumni summary");

        List<Alumni2> alumniList = fetchAlumniSummary();

        if (alumniList.isEmpty()) {
            logger.warning("No alumni data retrieved from the database");
        } else {
            logger.info("Number of alumni records fetched: " + alumniList.size());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(alumniList);
        
        // Log the JSON response
        logger.info("Sending JSON response: " + json);

        response.getWriter().write(json);
    }

    private List<Alumni2> fetchAlumniSummary() {
        String path = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String un = "System";
        String pasd = "system";
        String sql = "SELECT name, email, year_of_graduation, current_job_title, current_company FROM Alumni";
        List<Alumni2> alumniList = new ArrayList<>();
        Random random = new Random();

        try {
            Class.forName(path);
            try (Connection con = DriverManager.getConnection(url, un, pasd);
                 Statement stmt = con.createStatement();
                 ResultSet resultSet = stmt.executeQuery(sql)) {

                while (resultSet.next()) {
                    Alumni2 alumni = new Alumni2();
                    alumni.setName(resultSet.getString("name"));
                    alumni.setEmail(resultSet.getString("email"));
                    alumni.setYearOfGraduation(resultSet.getInt("year_of_graduation"));
                    alumni.setCurrentJobTitle(resultSet.getString("current_job_title"));
                    alumni.setCurrentCompany(resultSet.getString("current_company"));

                    logger.info("Fetched Alumni: " + alumni.getName() + ", " + alumni.getEmail());

                    alumniList.add(alumni);
                }

                java.util.Collections.shuffle(alumniList, random);
            }
        } catch (Exception e) {
            logger.severe("Error in fetching alumni summary data: " + e.getMessage());
            e.printStackTrace();
        }

        return alumniList;
    }
}