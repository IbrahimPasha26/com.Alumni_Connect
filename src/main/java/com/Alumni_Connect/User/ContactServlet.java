package com.Alumni_Connect.User;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.Alumni_Connect.dbHandler.DataInjector;

/**
 * Servlet implementation class ContactServlet
 */
@WebServlet("/contact")
public class ContactServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phno = req.getParameter("phno");
        String msg = req.getParameter("msg");

        boolean result = DataInjector.addContactFormEntry(name, email, phno, msg);

        if (result) {
            // Forward to a confirmation page with a success message
            req.setAttribute("message", "We will contact you soon.");
            req.getRequestDispatcher("/confirmation.jsp").forward(req, resp);
        } else {
            // Forward to an error page or display an error message
            req.setAttribute("message", "There was an error processing your request. Please try again.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}
