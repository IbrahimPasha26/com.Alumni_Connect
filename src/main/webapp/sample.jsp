<%@page import="com.Alumni_Connect.dbHandler.DataFetcher"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.Alumni_Connect.admin.Alumni"%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Home Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Admin home page for Alumni Connect">
    <meta name="keywords" content="Admin, Home, Alumni Connect">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" integrity="sha512-iBBXm8fW90+nuLcSKlbmrPcLa0OT92xO1BIsZ+ywDWZCvqsWgccV3gFoRBv0z+8dLJgyAHIhR35VZc2oM/gI1w==" crossorigin="anonymous" />
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous" />
    <link href="https://fonts.googleapis.com/css?family=Dosis" rel="stylesheet" />
    <link rel="stylesheet" href="adminHome.css">
    <style>
        /* Inline some extra CSS for enhancements */
        .tooltip {
            position: relative;
            display: inline-block;
        }

        .tooltip .tooltiptext {
            visibility: hidden;
            width: 120px;
            background-color: #555;
            color: #fff;
            text-align: center;
            border-radius: 6px;
            padding: 5px;
            position: absolute;
            z-index: 1;
            bottom: 125%; 
            left: 50%;
            margin-left: -60px;
            opacity: 0;
            transition: opacity 0.3s;
        }

        .tooltip:hover .tooltiptext {
            visibility: visible;
            opacity: 1;
        }

        .alumni-card {
            border: 1px solid #ccc;
            padding: 10px;
            margin: 10px;
            display: flex;
            align-items: center;
            background-color: #f9f9f9;
            border-radius: 10px;
            transition: transform 0.3s;
        }

        .alumni-card:hover {
            transform: scale(1.02);
        }

        .alumni-card img {
            max-width: 100px;
            margin-right: 20px;
            border-radius: 50%;
        }

        .alumni-details {
            display: flex;
            flex-direction: column;
        }

        .logout-button {
            position: fixed;
            bottom: 20px;
            right: 20px;
            background-color: #f44336;
            color: white;
            border: none;
            border-radius: 50%;
            width: 50px;
            height: 50px;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.3s;
        }

        .logout-button:hover {
            background-color: #d32f2f;
            transform: scale(1.1);
        }

        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 5px;
            font-size: 1rem;
        }

        .alert-success {
            background-color: #d4edda;
            color: #155724;
        }

        .alert-error {
            background-color: #f8d7da;
            color: #721c24;
        }
    </style>
</head>
<body>
    <% 
        String message = (String) session.getAttribute("message");
        String messageType = (String) session.getAttribute("messageType");
        if (message != null && messageType != null) {
    %>
        <div class="alert <%= messageType.equals("success") ? "alert-success" : "alert-error" %>">
            <%= message %>
        </div>
    <%
            session.removeAttribute("message");
            session.removeAttribute("messageType");
        }

        List<Alumni> alumniList = null;
        try {
            alumniList = DataFetcher.fetchAlumniInfo();
        } catch (Exception e) {
            out.println("<p>Failed to fetch alumni details: " + e.getMessage() + "</p>");
        }
    %>

    <div class="bg-img">
        <div class="content">
            <header>
                <div class="header-left">
                    <i class="fas fa-user-shield"></i> Admin Panel
                </div>
            </header>
            <div class="panel">
                <div class="button-container">
                    <button class="active" data-tab="tab1" class="tooltip">
                        <i class="fas fa-plus"></i> Add Alumni Details
                        <span class="tooltiptext">Add new alumni details</span>
                    </button>
                    <button data-tab="tab2" class="tooltip">
                        <i class="fas fa-eye"></i> View Alumni Details
                        <span class="tooltiptext">View alumni details</span>
                    </button>
                    <button data-tab="tab3" class="tooltip">
                        <i class="fas fa-trash-alt"></i> Remove Alumni
                        <span class="tooltiptext">Remove alumni details</span>
                    </button>
                    <button data-tab="tab4" class="tooltip">
                        <i class="fas fa-calendar-plus"></i> Add Event
                        <span class="tooltiptext">Add and assign events</span>
                    </button>
                </div>
                <div class="content-container">
                    <div class="tab active" id="tab1">
                        <h2>Add Alumni Details</h2>
                        <form action="addAlumni" method="POST" enctype="multipart/form-data" onsubmit="return validateForm()">
                            <div class="field">
                                <label for="alumni_id">Alumni ID:</label>
                                <span class="fa fa-id-badge"></span>
                                <input type="text" id="alumni_id" name="alumni_id" required placeholder="Alumni ID">
                            </div>
                            <div class="field">
                                <label for="name">Name:</label>
                                <span class="fa fa-user"></span>
                                <input type="text" id="name" name="name" required placeholder="Name">
                            </div>
                            <div class="field">
                                <label for="email">Email:</label>
                                <span class="fa fa-envelope"></span>
                                <input type="email" id="email" name="email" required placeholder="Email">
                            </div>
                            <div class="field">
                                <label for="phone_number">Phone Number:</label>
                                <span class="fa fa-phone"></span>
                                <input type="text" id="phone_number" name="phone_number" required placeholder="Phone Number">
                            </div>
                            <div class="field">
                                <label for="year_of_graduation">Year of Graduation:</label>
                                <span class="fa fa-calendar"></span>
                                <input type="number" id="year_of_graduation" name="year_of_graduation" required placeholder="Year of Graduation">
                            </div>
                            <div class="field">
                                <label for="course">Course:</label>
                                <span class="fa fa-book"></span>
                                <input type="text" id="course" name="course" required placeholder="Course">
                            </div>
                            <div class="field">
                                <label for="current_job_title">Current Job Title:</label>
                                <span class="fa fa-briefcase"></span>
                                <input type="text" id="current_job_title" name="current_job_title" required placeholder="Current Job Title">
                            </div>
                            <div class="field">
                                <label for="current_company">Current Company:</label>
                                <span class="fa fa-building"></span>
                                <input type="text" id="current_company" name="current_company" required placeholder="Current Company">
                            </div>
                            <div class="field">
                                <label for="location">Location:</label>
                                <span class="fa fa-map-marker-alt"></span>
                                <input type="text" id="location" name="location" required placeholder="Location">
                            </div>
                            <div class="field">
                                <label for="bio">Bio:</label>
                                <span class="fa fa-info-circle"></span>
                                <textarea id="bio" name="bio" required placeholder="Bio"></textarea>
                            </div>
                            <div class="field">
                                <label for="profile_picture">Profile Picture:</label>
                                <span class="fa fa-image"></span>
                                <input type="file" id="profile_picture" name="profile_picture" required>
                            </div>
                            <div class="field">
                                <input type="submit" value="Add Alumni">
                            </div>
                        </form>
                    </div>
                    <div class="tab" id="tab2">
                        <h2>View Alumni Details</h2>
                        <% if (alumniList != null) { %>
                            <% for (Alumni alumni : alumniList) { %>
                                <div class="alumni-card">
                                    <img src="<%= alumni.getProfilePicture() %>" alt="<%= alumni.getName() %>">
                                    <div class="alumni-details">
                                        <h2><%= alumni.getName() %></h2>
                                        <p><span class="fa fa-id-badge"></span> Alumni ID: <%= alumni.getAlumniId() %></p>
                                        <p><span class="fa fa-envelope"></span> Email: <%= alumni.getEmail() %></p>
                                        <p><span class="fa fa-phone"></span> Phone: <%= alumni.getPhoneNumber() %></p>
                                        <p><span class="fa fa-calendar"></span> Year of Graduation: <%= alumni.getYearOfGraduation() %></p>
                                        <p><span class="fa fa-book"></span> Course: <%= alumni.getCourse() %></p>
                                        <p><span class="fa fa-briefcase"></span> Current Job Title: <%= alumni.getCurrentJobTitle() %></p>
                                        <p><span class="fa fa-building"></span> Current Company: <%= alumni.getCurrentCompany() %></p>
                                        <p><span class="fa fa-map-marker-alt"></span> Location: <%= alumni.getLocation() %></p>
                                        <p><span class="fa fa-info-circle"></span> Bio: <%= alumni.getBio() %></p>
                                    </div>
                                </div>
                            <% } %>
                        <% } else { %>
                            <p>No alumni details available.</p>
                        <% } %>
                    </div>
                    <div class="tab" id="tab3">
                        <h2>Remove Alumni</h2>
                        <form action="removeAlumni" method="POST">
                            <div class="field">
                                <label for="remove_alumni_id">Alumni ID to Remove:</label>
                                <input type="text" id="remove_alumni_id" name="remove_alumni_id" required placeholder="Alumni ID">
                            </div>
                            <div class="field">
                                <input type="submit" value="Remove Alumni">
                            </div>
                        </form>
                    </div>
                    <div class="tab" id="tab4">
                        <h2>Add Event</h2>
                        <form action="AddEvent" method="POST">
                            <div class="field">
                                <label for="event_name">Event Name:</label>
                                <span class="fa fa-calendar-check"></span>
                                <input type="text" id="event_name" name="event_name" required placeholder="Event Name">
                            </div>
                            <div class="field">
                                <label for="event_date">Event Date:</label>
                                <span class="fa fa-calendar-alt"></span>
                                <input type="date" id="event_date" name="event_date" required>
                            </div>
                            <div class="field">
                                <label for="event_description">Event Description:</label>
                                <span class="fa fa-info-circle"></span>
                                <textarea id="event_description" name="event_description" required placeholder="Event Description"></textarea>
                            </div>
                            <div class="field">
                                <label for="alumni_ids">Assign to Alumni:</label>
                                <span class="fa fa-users"></span>
                                <select id="alumni_ids" name="alumni_ids" multiple required>
                                    <% if (alumniList != null) { %>
                                        <% for (Alumni alumni : alumniList) { %>
                                            <option value="<%= alumni.getAlumniId() %>"><%= alumni.getName() %></option>
                                        <% } %>
                                    <% } %>
                                </select>
                            </div>
                            <div class="field">
                                <input type="submit" value="Add Event">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <button class="logout-button" onclick="window.location.href='logout'">
                <i class="fas fa-sign-out-alt"></i>
            </button>
        </div>
    </div>
    <script>
        // Handle tab switching
        const buttons = document.querySelectorAll('.button-container button');
        const tabs = document.querySelectorAll('.content-container .tab');

        buttons.forEach(button => {
            button.addEventListener('click', () => {
                const tabId = button.dataset.tab;
                const tab = document.getElementById(tabId);
                tabs.forEach(tab => tab.classList.remove('active'));
                buttons.forEach(button => button.classList.remove('active'));
                tab.classList.add('active');
                button.classList.add('active');
            });
        });

        function validateForm() {
            // Add validation logic if needed
            return true;
        }
    </script>
</body>
</html>
