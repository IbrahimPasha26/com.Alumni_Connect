package com.Alumni_Student.Student;

import java.time.LocalDate;

import com.Alumni_Connect.admin.Alumni;

public class Event {
    private int eventId;
    private String eventName;
    private LocalDate eventDate; // Changed to LocalDate
    private String eventDescription;
    private Alumni alumni;

    // Getters and Setters
    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDate getEventDate() { // Changed return type
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) { // Changed parameter type
        this.eventDate = eventDate;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

	public Alumni getAlumni() {
		return alumni;
	}

	public void setAlumni(Alumni alumni) {
		this.alumni = alumni;
	}
    
}
