package domain;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class Appointment implements Entity<Long>, Serializable {
    private Long appointmentId;
    private User user;
    private Patient patient;
    private DonationCentre donationCentre;
    private Date date;
    private Time time;

    public Appointment(User user, Patient patient, DonationCentre donationCentre, Date date, Time time) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
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
