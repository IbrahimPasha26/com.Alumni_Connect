package com.Alumni_Connect.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.Alumni_Connect.dbHandler.DataInjector;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 5, // 5MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 20    // 20MB
)
public class AddAlumniServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        try {
            //int id = Integer.parseInt(req.getParameter("alumni_id"));
            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String phno = req.getParameter("phone_number");
            int yearOfGraduation = Integer.parseInt(req.getParameter("year_of_graduation"));
            String course = req.getParameter("course");
            String curJobTitle = req.getParameter("current_job_title");
            String curCompany = req.getParameter("current_company");
            String location = req.getParameter("location");
            String bio = req.getParameter("bio");

            // Handling the upload file
            Part filePart = req.getPart("profile_picture");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = getServletContext().getRealPath("") + File.separator + "Images";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            filePart.write(uploadPath + File.separator + fileName);

            // Path to the uploaded file
            String picPath = "Images/" + fileName;
            
            // Calling the DataInjector Class to add the details to DataBase
            boolean res = DataInjector.addAlumni(name, email, phno, yearOfGraduation, course, curJobTitle, curCompany, location, bio, picPath);
            
            if (res) {
                // Set the success message as a session attribute
                session.setAttribute("message", "Alumni details added successfully!");
                session.setAttribute("messageType", "success");
            } else {
                // Set the error message as a session attribute
                session.setAttribute("message", "Failed to add alumni details.");
                session.setAttribute("messageType", "error");
            }
            // Redirect to adminHome.jsp
            resp.sendRedirect("adminHome.jsp");
        } catch (NumberFormatException e) {
            // Handle number format exception
            session.setAttribute("message", "Invalid number format: " + e.getMessage());
            session.setAttribute("messageType", "error");
            resp.sendRedirect("adminHome.jsp");
        } catch (Exception e) {
            // Handle other exceptions
            session.setAttribute("message", "An error occurred: " + e.getMessage());
            session.setAttribute("messageType", "error");
            resp.sendRedirect("adminHome.jsp");
        }
    }
}
