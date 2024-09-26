package com.Alumni_Connect.Login;

import com.Alumni_Connect.dbHandler.DataFetcher;

public class Validator 
{
	public static boolean isValid(String email, String pas)
	{
		String dbPass = DataFetcher.fetchPassword(email);
		if(dbPass.equals(pas))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static boolean isValidStudent(String email, String num)
	{
		String dbPass = DataFetcher.fetchNumber(email);
		if(dbPass.endsWith(num))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
