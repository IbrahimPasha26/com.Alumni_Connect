package com.Alumni_Connect.User;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Alumni_Connect.dbHandler.DataInjector;

@WebServlet("/register")
public class SignUpServlet extends HttpServlet 
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String uname = req.getParameter("username");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String fname = req.getParameter("fullName");
		String address = req.getParameter("address");
		
		// Check if the user already exists
		boolean userExists = DataInjector.isUserExist(uname, email);
		
		if (userExists) 
		{
			// Redirect back to signUp.html with an error message that the user already exists
			resp.sendRedirect("signUp.html?error=User+Already+Exists");
		} 
		else 
		{
			// Proceed to add the user if they don't already exist
			boolean result = DataInjector.addUser(uname, password, email, fname, address);
			
			if(result) 
			{
				// Redirect to userHome.jsp with a success message
				resp.sendRedirect("userLogin.html?message=SignUp+Completed");
			} 
			else 
			{
				// Redirect back to signUp.html with an error message
				resp.sendRedirect("signUp.html?error=SignUp+Failed");
			}
		}
	}
}
