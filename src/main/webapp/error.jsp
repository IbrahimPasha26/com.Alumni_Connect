<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contact Error</title>
    <!-- Redirect after 5 seconds -->
    <meta http-equiv="refresh" content="3;url=home.jsp">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            color: #333;
            margin: 0;
            padding: 0;
            text-align: center;
        }
        h1 {
            color: #f44336;
            margin-top: 50px;
            font-size: 2em;
        }
        p {
            font-size: 1.2em;
            margin: 20px;
        }
        a {
            color: #2196F3;
            text-decoration: none;
            font-weight: bold;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <h1>${message}</h1>
    <p>You will be redirected to the Home page shortly. If not, <a href="home.jsp">click here</a>.</p>
</body>
</html>
