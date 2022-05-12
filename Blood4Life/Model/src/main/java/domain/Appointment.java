package domain;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Appointment implements Entity<Long>, Serializable {
    private Long appointmentId;
    private User user;
    private Patient patient;
    private DonationCentre donationCentre;
    private LocalDate date;
    private LocalTime time;

    public Appointment(User user, Patient patient, DonationCentre donationCentre, LocalDate date, LocalTime time) {
        this.user = user;
        this.patient = patient;
        this.donationCentre = donationCentre;
        this.date = date;
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public DonationCentre getDonationCentre() {
        return donationCentre;
    }

    public void setDonationCentre(DonationCentre donationCentre) {
        this.donationCentre = donationCentre;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public Long getID() {
        return appointmentId;
    }

    @Override
    public void setID(Long appointmentId) {
        this.appointmentId = appointmentId;
    }
}
