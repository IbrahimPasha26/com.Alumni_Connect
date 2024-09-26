package com.Alumni_Connect.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Alumni_Connect.dbHandler.DataInjector;

@WebServlet("/AddEvent")
public class AddEventServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eventName = request.getParameter("event_name");
        String eventDate = request.getParameter("event_date");
        String eventDescription = request.getParameter("event_description");
        String[] alumniIds = request.getParameterValues("alumni_ids");

        DataInjector di = new DataInjector();
        int eventId = di.addEvent(eventName, eventDate, eventDescription);

        if (eventId != -1) {
            // Assign event to selected alumni
            di.assignEventToAlumni(eventId, alumniIds);

            // Set success message and redirect
            HttpSession session = request.getSession();
            session.setAttribute("message", "Event added successfully!");
            session.setAttribute("messageType", "success");
        } else {
            // Set error message and redirect
            HttpSession session = request.getSession();
            session.setAttribute("message", "Failed to add event!");
            session.setAttribute("messageType", "error");
        }

        response.sendRedirect("adminHome.jsp");
    }
}
