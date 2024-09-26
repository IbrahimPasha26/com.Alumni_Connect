package com.Alumni_Student.Student;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.Alumni_Connect.admin.Alumni;
import com.Alumni_Connect.dbHandler.DataFetcher;
import com.Alumni_Connect.dbHandler.DataInjector;

@WebServlet("/updateProfile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
                 maxFileSize = 1024 * 1024 * 10,      // 10 MB
                 maxRequestSize = 1024 * 1024 * 15)   // 15 MB
public class UpdateServlet extends HttpServlet 
{
    private static final String UPLOAD_DIR = "Images"; // Updated to match the AddAlumniServlet

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        // Retrieve form fields
        String fname = req.getParameter("firstName");
        String lname = req.getParameter("lastName");
        //String email = req.getParameter("email");
        String phno = req.getParameter("phoneNumber");
        String yrofenrol = req.getParameter("enrollmentYear");
        String course = req.getParameter("course");
        String currentStatus = req.getParameter("currentStatus");
        String graduationyr = req.getParameter("graduationYear");

        // Handle file upload
        Part filePart = req.getPart("profilePicture");
        String fileName = filePart != null ? Paths.get(filePart.getSubmittedFileName()).getFileName().toString() : null;

        String filePath = null;
        Cookie[] cookies = req.getCookies();
        String email = null;
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if("mail".equals(cookie.getName())) {
                    email = cookie.getValue();
                }
            }
        }
        List<Alumni> alumniList = DataFetcher.fetchAlumniInfo(email);
        
        if (fileName != null && !fileName.isEmpty()) {
            // Save the uploaded file
            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;

            // Create the upload directory if it doesn't exist
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            filePart.write(uploadPath + File.separator + fileName);
            filePath = UPLOAD_DIR + File.separator + fileName;  // Store the relative path
        } else if (!alumniList.isEmpty()) {
            // Use existing profile picture if no new file was uploaded
            filePath = alumniList.get(0).getProfilePicture();
        }

        // Check if alumni data was found
        if (alumniList.isEmpty()) {
            req.getSession().setAttribute("message", "No alumni data found for the given email.");
            resp.sendRedirect("studentHome.jsp");
            return; // Stop further processing since there's no alumni data
        }

        // Fetch current student data from the database
        Alumni currentAlumniData = alumniList.get(0);

        // Update student information in the STUDENT table
        boolean res = DataInjector.addStudent(fname, lname, email, phno, yrofenrol, course, currentStatus, graduationyr, filePath);

        // If successful, compare the new data with the old data and update the ALUMNI table if necessary
        if (res) {
            if (!fname.equals(currentAlumniData.getFirstName()) || 
                !lname.equals(currentAlumniData.getLastName()) || 
                !phno.equals(currentAlumniData.getPhoneNumber()) ||
                !course.equals(currentAlumniData.getCourse()) || 
                !graduationyr.equals(currentAlumniData.getYearOfGraduation()) || 
                !filePath.equals(currentAlumniData.getProfilePicture())) {

                boolean alumniUpdateResult = DataInjector.updateAlumni(email, fname, lname, phno, course, graduationyr, filePath);

				if (alumniUpdateResult) {
				    req.getSession().setAttribute("message", "Profile updated successfully.");
				} else {
				    req.getSession().setAttribute("message", "Profile update failed.");
				}
            } else {
                req.getSession().setAttribute("message", "No changes were detected.");
            }
        } else {
            req.getSession().setAttribute("message", "Data Insertion Failed");
        }

        // Redirect to the student home page
        resp.sendRedirect("studentHome.jsp");
    }

    // Utility method to get the file name from the Part header
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return null;
    }
}
