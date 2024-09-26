package com.Alumni_Connect.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Alumni_Connect.Login.Validator;

/**
 * Servlet implementation class AdminLogServlet
 */
@WebServlet("/admin_log")
public class AdminLogServlet extends HttpServlet 
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        String email = req.getParameter("adminEmail");
        String password = req.getParameter("adminPassword");
        boolean result = Validator.isValid(email, password);

        // Create a session object
        HttpSession session = req.getSession();
        
        if(result)
        {
            // Set a success message in the session
            session.setAttribute("message", "Login successful! Welcome to the admin panel.");
            session.setAttribute("messageType", "success");
            
            // Redirect to adminHome.jsp
            resp.sendRedirect("adminHome.jsp");
        }
        else
        {
        	resp.sendRedirect("login.html?error=Invalid credentials. Please try again.");
        }
    }
}
