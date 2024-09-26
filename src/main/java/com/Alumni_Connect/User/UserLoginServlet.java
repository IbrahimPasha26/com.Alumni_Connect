package com.Alumni_Connect.User;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Alumni_Connect.dbHandler.DataFetcher;

/**
 * Servlet implementation class UserLoginServlet
 */
@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet 
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String uname = req.getParameter("username");
		String pasd = req.getParameter("password");
		String dbPass = DataFetcher.fetchUserPassword(uname);
		if(pasd.equals(dbPass))
		{
			resp.sendRedirect("home.jsp");
		}
		else
		{
			resp.sendRedirect("userLogin.html");
		}
	}
}
