package com.Alumni_Connect.Login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String phno = req.getParameter("phone_number");
        
        boolean res = Validator.isValidStudent(email, phno);

        if (res) {
            // Create a session object
            HttpSession session = req.getSession();
            
            // Set success message in the session
            session.setAttribute("message", "Login successful! Welcome back.");
            session.setAttribute("messageType", "success");
            
            // Set cookies for email and phone number
            Cookie ck1 = new Cookie("mail", email);
            Cookie ck2 = new Cookie("ph", phno);
            
            ck1.setMaxAge(60 * 60 * 60); // 60 hours
            ck2.setMaxAge(60 * 60 * 60); // 60 hours
            resp.addCookie(ck1);
            resp.addCookie(ck2);
            
            // Redirect to studentHome.jsp
            resp.sendRedirect("studentHome.jsp");
        } else {
            // Redirect to login.html with an error message
            String errorMessage = "Invalid credentials. Please try again.";
            resp.sendRedirect("login.html?error=" + java.net.URLEncoder.encode(errorMessage, "UTF-8"));
        }
    }
}
