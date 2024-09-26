package com.Alumni_Connect.admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Alumni_Connect.dbHandler.DataInjector;

@WebServlet("/removeAlumni")
public class RemoveAlumniServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int alumniID = -1;
        try {
            alumniID = Integer.parseInt(req.getParameter("remove_alumni_id")); // Ensure this matches the form field name
        } catch (NumberFormatException e) {
            req.getSession().setAttribute("message", "Invalid Alumni ID format.");
            resp.sendRedirect("adminHome.jsp");
            return;
        }
        
        HttpSession session = req.getSession();
        boolean res = false;

        res = DataInjector.removeAlumni(alumniID);
        
        if (res) {
            session.setAttribute("message", "Alumni record deleted successfully.");
        } else {
            session.setAttribute("message", "Failed to delete alumni record.");
        }
        
        resp.sendRedirect("adminHome.jsp");
    }
}
