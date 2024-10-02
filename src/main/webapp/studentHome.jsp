<%@ page import="com.Alumni_Student.Student.Event" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Alumni_Connect.dbHandler.DataFetcher" %>
<%@ page import="com.Alumni_Connect.admin.Alumni" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDate" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Student Profile - Alumni Connect</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
    <link href="studentHome.css" rel="stylesheet">
    <style>
        .form-control {
            width: 100%;
            max-width: 100%;
            box-sizing: border-box;
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #ccc;
            font-size: 1.1em;
            transition: box-shadow 0.3s ease-in-out, border-color 0.3s ease-in-out;
            background: rgba(255, 255, 255, 0.6);
            backdrop-filter: blur(5px);
        }
        .form-control:focus {
            box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.2);
            border-color: #3f51b5;
        }
        /* Responsive adjustments */
@media (max-width: 768px) {
    .event-card {
        width: calc(100% - 20px); /* Full width on small screens */
    }
}
.event-card {
    background: #fff; /* White background for cards */
    border-radius: 10px; /* Rounded corners */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Subtle shadow */
    padding: 15px;
    width: calc(50% - 20px); /* Adjust width for two cards per row */
    transition: transform 0.3s ease, box-shadow 0.3s ease;
    overflow: hidden; /* Ensure no overflow of content */
}
    </style>
    <script>
        function confirmLogout() {
            if (confirm("Are you sure you want to logout?")) {
                window.location.href = 'userLogin.html';
            }
        }

        function scrollToUpdateProfile() {
            document.getElementById("updateProfile").scrollIntoView({ behavior: 'smooth' });
        }

        function scrollToEvents() {
            document.getElementById("eventsSection").scrollIntoView({ behavior: 'smooth' });
        }

        function showMessage() {
            const message = '<%= (session.getAttribute("message") != null) ? session.getAttribute("message") : "" %>';
            if (message !== "") {
                alert(message);
                <% session.removeAttribute("message"); %>
            }
        }

        window.onload = function() {
            showMessage();
        };
    </script>
</head>
<body>
  <%
    Cookie[] cookies = request.getCookies();
    String email = null;
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("mail".equals(cookie.getName())) {
                email = cookie.getValue();
            }
        }
    }
    List<Alumni> alumniList = null;
    List<Event> eventList = null;
    try {
        alumniList = DataFetcher.fetchAlumniInfo(email);
        if (alumniList != null && !alumniList.isEmpty()) {
            int alumniId = alumniList.get(0).getAlumniId(); 
            eventList = DataFetcher.fetchEventsForAlumni(alumniId); 
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
%>

    <div class="container">
        <div class="header">
            <h1>Alumni Panel</h1>
        </div>
        <div class="row">
            <div class="profile-nav col-md-3">
                <div class="panel">
                    <div class="user-heading round">
                        <a href="#">
                            <img src="<%= (alumniList != null && !alumniList.isEmpty()) ? alumniList.get(0).getProfilePicture() : "https://www.pngarts.com/files/3/Avatar-PNG-High-Quality-Image.png" %>" alt="Profile Picture">
                        </a>
                        <h1><%= (alumniList != null && !alumniList.isEmpty()) ? alumniList.get(0).getName() : "Unknown" %></h1>
                        <p><%= (alumniList != null && !alumniList.isEmpty()) ? alumniList.get(0).getCurrentJobTitle() : "N/A" %></p>
                    </div>
                    <div class="panel-body">
                        <ul class="nav nav-pills nav-stacked">
                            <li><a href="#"><i class="fa fa-user"></i> Profile</a></li>
                            <li><a href="#" onclick="scrollToUpdateProfile()"><i class="fa fa-edit"></i> Edit Profile</a></li>
                            <li><a href="#" onclick="scrollToEvents()"><i class="fa fa-calendar"></i> View Events</a></li>
                            <li><a href="#" class="logout-btn" onclick="confirmLogout()"><i class="fa fa-sign-out-alt"></i> Logout</a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="profile-info col-md-9">
                <div class="bio-graph-info">
                    <h1>Profile Details</h1>
                    <div class="alumni-card">
                        <img src="<%= (alumniList != null && !alumniList.isEmpty()) ? alumniList.get(0).getProfilePicture() : "https://www.pngarts.com/files/3/Avatar-PNG-High-Quality-Image.png" %>" alt="Profile Picture">
                        <div class="alumni-details">
                            <p><span>Name:</span> <%= (alumniList != null && !alumniList.isEmpty()) ? alumniList.get(0).getName() : "N/A" %></p>
                            <p><span>Email:</span> <%= (alumniList != null && !alumniList.isEmpty()) ? alumniList.get(0).getEmail() : "N/A" %></p>
                            <p><span>Phone:</span> <%= (alumniList != null && !alumniList.isEmpty()) ? alumniList.get(0).getPhoneNumber() : "N/A" %></p>
                            <p><span>Company:</span> <%= (alumniList != null && !alumniList.isEmpty()) ? alumniList.get(0).getCurrentCompany() : "N/A" %></p>
                            <p><span>Job Title:</span> <%= (alumniList != null && !alumniList.isEmpty()) ? alumniList.get(0).getCurrentJobTitle() : "N/A" %></p>
                            <p><span>Location:</span> <%= (alumniList != null && !alumniList.isEmpty()) ? alumniList.get(0).getLocation() : "N/A" %></p>
                            <p><span>Bio:</span> <%= (alumniList != null && !alumniList.isEmpty()) ? alumniList.get(0).getBio() : "N/A" %></p>
                        </div>
                    </div>
                </div>

             <!-- Display Events Section -->
<div class="events-info" id="eventsSection">
    <h1>Your Events</h1>
    <div class="event-list">
        <%
            if (eventList != null && !eventList.isEmpty()) {
                for (Event event : eventList) {
        %>
            <div class="event-card">
                <h2><%= event.getEventName() %></h2>
                <p class="event-date"><%= event.getEventDate().format(formatter) %></p>
                <p class="event-description"><%= event.getEventDescription() %></p>
            </div>
        <%
                }
            } else {
        %>
            <p>No events available at the moment.</p>
        <%
            }
        %>
    </div>
</div>

                <div class="update-graph-info" id="updateProfile">
                    <h1>Update Details</h1>
                    <div class="update-card">
                        <form action="updateProfile" method="post" enctype="multipart/form-data">
                            <!-- Form fields -->
                            <div class="form-group field">
                                <label for="firstName"><i class="fa fa-user"></i> First Name:</label>
                                <input type="text" id="firstName" name="firstName" class="form-control" value="<%= (alumniList != null && !alumniList.isEmpty()) ? alumniList.get(0).getName() : "N/A" %>" required="required">
                            </div>
                            <div class="form-group field">
                                <label for="lastName"><i class="fa fa-user"></i> Last Name:</label>
                                <input type="text" id="lastName" name="lastName" class="form-control" value="" required="required">
                            </div>
                            <div class="form-group field">
                                <label for="email"><i class="fa fa-envelope"></i> Email:</label>
                                <input type="email" id="email" name="email" class="form-control" value="<%= (alumniList != null && !alumniList.isEmpty()) ? alumniList.get(0).getEmail() : "N/A" %>">
                            </div>
                            <div class="form-group field">
                                <label for="phoneNumber"><i class="fa fa-phone"></i> Phone Number:</label>
                                <input type="text" id="phoneNumber" name="phoneNumber" class="form-control" value="<%= (alumniList != null && !alumniList.isEmpty()) ? alumniList.get(0).getPhoneNumber() : "N/A" %>" required="required">
                            </div>
                            <div class="form-group field">
                                <label for="enrollmentYear"><i class="fa fa-calendar"></i> Enrollment Year:</label>
                                <input type="number" id="enrollmentYear" name="enrollmentYear" class="form-control" value="" required="required">
                            </div>
                            <div class="form-group field">
                                <label for="course"><i class="fa fa-book"></i> Course:</label>
                                <input type="text" id="course" name="course" class="form-control" value="<%= (alumniList != null && !alumniList.isEmpty()) ? alumniList.get(0).getCourse() : "N/A" %>" required="required">
                            </div>
                            <div class="form-group field">
                                <label for="currentStatus"><i class="fa fa-info-circle"></i> Current Status:</label>
                                <input type="text" id="currentStatus" name="currentStatus" class="form-control" value="<%= (alumniList != null && !alumniList.isEmpty()) ? alumniList.get(0).getCurrentJobTitle() : "N/A" %>" required="required">
                            </div>
                            <div class="form-group field">
                                <label for="graduationYear"><i class="fa fa-calendar"></i> Graduation Year:</label>
                                <input type="number" id="graduationYear" name="graduationYear" class="form-control" value="<%= (alumniList != null && !alumniList.isEmpty()) ? alumniList.get(0).getYearOfGraduation() : "N/A" %>" required="required">
                            </div>
                            <div class="form-group field">
                                <label for="profilePicture"><i class="fa fa-image"></i> Profile Picture:</label>
                                <input type="file" id="profilePicture" name="profilePicture" class="form-control" required="required">
                            </div>
                            <!-- More fields... -->
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary">Submit</button>
                            </div>
                        </form>
                    </div>
                </div>

            </div>
        </div>
    </div>
</body>
</html>
