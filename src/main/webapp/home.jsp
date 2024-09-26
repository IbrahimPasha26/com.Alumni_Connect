<%@page import="com.Alumni_Student.Student.Event"%>
<%@page import="com.Alumni_Connect.dbHandler.DataFetcher"%>
<%@page import="java.util.List"%>
<%@page import="com.Alumni_Connect.admin.Alumni"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Alumni Connect</title>
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"
      integrity="sha512-iBBXm8fW90+nuLcSKlbmrPcLa0OT92xO1BIsZ+ywDWZCvqsWgccV3gFoRBv0z+8dLJgyAHIhR35VZc2oM/gI1w=="
      crossorigin="anonymous"
      referrerpolicy="no-referrer"
    />
    <link rel="stylesheet" href="home.css" />
    <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css" />
    <style type="text/css">
 
/* Media Query for Mobile */
@media (max-width: 768px) {
    .menu-items {
        position: fixed;
        top: 70px;
        right: -250px;
        width: 200px;
        height: 100vh;
        background-color: rgba(255, 255, 255, 0.9);
        flex-direction: column;
        align-items: center;
        justify-content: center;
        gap: 2rem;
        transition: right 0.3s ease;
    }

    .menu-items.active {
        right: 0;
    }

    .hamburger-lines {
        display: flex;
    }
}
    /* Center align hero content */
.showcase-container {
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    height: 60vh; /* Full viewport height */
    background: url("https://i.postimg.cc/k4VBnMT7/pngtree-taobao-graduation-season-minimalistic-texture-poster-background-picture-image-1026563.png") no-repeat center center/cover; /* Optional background image */
}
    </style>
</head>
<body>
   <!-- Navbar -->
<nav class="navbar">
    <div class="navbar-container container">
        <div class="hamburger-lines">
            <span class="line line1"></span>
            <span class="line line2"></span>
            <span class="line line3"></span>
        </div>
        <ul class="menu-items">
            <li><a href="#about">About</a></li>
            <li><a href="login.html" id="user_login">Log In</a></li>
            <li><a href="#events">Events</a></li>
            <li><a href="#alumni">Our Alumni</a></li>
            <li><a href="#contact">Contact</a></li>
        </ul>
        <h1 class="logo">
            <img src="Images/logo.png" alt="Alumni Connect Logo" />
        </h1>
    </div>
</nav>


  <!-- Hero Section -->
<section class="showcase-area" id="showcase">
    <div class="showcase-container">
        <center>
            <h1 class="main-title">Connecting Alumni, Fostering Success</h1>
            <p>Your journey continues with us</p>
        </center>
    </div>
</section>


    <!-- About Section -->
    <section id="about">
        <div class="about-wrapper container">
            <div class="about-text">
                <h2>About Alumni Connect</h2><br>
                <p>Alumni Connect is dedicated to fostering lifelong relationships with our graduates. We offer a platform for alumni to connect, share experiences, and support each other professionally and personally.</p>
            </div>
            <div class="about-img">
                <img src="https://i.postimg.cc/2j1gVbsf/Wharton-Tops-Ivy-League-Rivals-for-MBA-Return-on-Investment.jpg" alt="Alumni Connect">
            </div>
        </div>
    </section>

    <!-- Alumni Section -->
    <section id="alumni">
        <div class="container">
            <h2>Our Distinguished Alumni</h2>
            <div class="alumni-cards">
                <!-- Fetch and display alumni data using JSP -->
                <% 
                    List<Alumni> alumniList = null;
                    try {
                        alumniList = DataFetcher.fetchAlumniInfo();
                    } catch (Exception e) {
                        out.println("<p>Failed to fetch alumni details: " + e.getMessage() + "</p>");
                    }

                    if (alumniList != null) {
                        for (Alumni alumni : alumniList) {
                %>
                    <div class="card">
                        <img src="<%= alumni.getProfilePicture() != null ? alumni.getProfilePicture() : "default.jpg" %>" alt="Alumni Picture">
                        <h3><%= alumni.getName() %></h3>
                        <p><span class="fa fa-envelope"></span> Email: <%= alumni.getEmail() %></p>
                        <p><span class="fa fa-calendar"></span> Year of Graduation: <%= alumni.getYearOfGraduation() %></p>
                        <p><span class="fa fa-briefcase"></span> Job Title: <%= alumni.getCurrentJobTitle() %></p>
                        <p><span class="fa fa-building"></span> Company: <%= alumni.getCurrentCompany() %></p>
                    </div>
                <% 
                        }
                    } else { 
                %>
                    <p>No alumni details available.</p>
                <% } %>
            </div>
        </div>
    </section> 

    <!-- Events Section -->
    <section id="events">
        <div class="container">
            <h2>Upcoming Events</h2>
            <div class="event-slider">
                <div class="swiper-container">
                    <div class="swiper-wrapper">
                        <!-- Fetch and display events with alumni details using JSP -->
                        <%
                            List<Event> eventList = null;
                            try {
                                eventList = DataFetcher.fetchAllEventsWithAlumni();
                            } catch (Exception e) {
                                out.println("<p>Failed to fetch event details: " + e.getMessage() + "</p>");
                            }

                            if (eventList != null) {
                                for (Event event : eventList) {
                        %>
                            <div class="swiper-slide card">
                                <div class="event-img-container">
                                    <img src="https://i.postimg.cc/76kkBZSh/3605351249b8b7a7d836808769518b28.jpg" alt="Event Image" class="event-img">
                                </div>
                                <div class="event-details">
                                    <h3><%= event.getEventName() %></h3>
                                    <p>Date: <%= event.getEventDate() %></p>
                                    <p>Description: <%= event.getEventDescription() %></p>
                                    <p>Conducted by: <%= event.getAlumni().getName() %></p>
                                    <p>Email: <%= event.getAlumni().getEmail() %></p>
                                    <p>Job Title: <%= event.getAlumni().getCurrentJobTitle() %></p>
                                    <p>Company: <%= event.getAlumni().getCurrentCompany() %></p>
                                </div>
                            </div>
                        <%
                                }
                            } else {
                        %>
                            <p>No events available.</p>
                        <%
                            }
                        %>
                    </div>
                    <!-- Add Pagination -->
                    <div class="swiper-pagination"></div>
                    <!-- Add Navigation -->
                    <div class="swiper-button-next"></div>
                    <div class="swiper-button-prev"></div>
                </div>
            </div>
        </div>
    </section>

    <!-- Contact Section -->
    <section id="contact">
        <div class="contact-container container">
            <div class="form-container">
                <h2>Contact Us</h2>
                <form action="contact" method="post">
                    <input type="text" id="name" name="name" placeholder="Your Name" />
                    <input type="email" id="email" name="email" placeholder="Your Email" />
                    <input type="phno" id="phno" name="phno" placeholder="Phone Number" />
                    <textarea id="message" cols="30" rows="6" name="msg" placeholder="Your Message"></textarea>
                    <input type="submit" value="Submit">
                </form>
            </div>
            <div class="contact-img">
                <img src="https://i.postimg.cc/MZD2kJJn/Time-for-an-omnichannel-retail-strategy-4-ways-to-implement-one.jpg" alt="Contact Us">
            </div>
        </div>
    </section>

    <!-- Footer -->
    <footer id="footer">
        <h2>&copy; 2024 Alumni Connect. All Rights Reserved.</h2>
    </footer>
    <script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
    <script>
        var swiper = new Swiper('.swiper-container', {
            loop: true,
            autoplay: {
                delay: 5000,
                disableOnInteraction: false,
            },
            pagination: {
                el: '.swiper-pagination',
                clickable: true,
            },
            navigation: {
                nextEl: '.swiper-button-next',
                prevEl: '.swiper-button-prev',
            },
            slidesPerView: 1,
            spaceBetween: 30,
            breakpoints: {
                640: {
                    slidesPerView: 1,
                    spaceBetween: 20,
                },
                768: {
                    slidesPerView: 2,
                    spaceBetween: 30,
                },
                1024: {
                    slidesPerView: 3,
                    spaceBetween: 40,
                },
            }
        });
     // JavaScript for Floating Navbar transitions on scroll
        window.addEventListener('scroll', function() {
            const navbar = document.querySelector('.navbar');
            if (window.scrollY > 50) {
                navbar.classList.add('scrolled');
            } else {
                navbar.classList.remove('scrolled');
            }
        });

        // JavaScript for Hamburger Menu Toggle
        const hamburger = document.querySelector('.hamburger-lines');
        const menuItems = document.querySelector('.menu-items');

        hamburger.addEventListener('click', () => {
            menuItems.classList.toggle('active');
        });

    </script>
</body>
</html>
