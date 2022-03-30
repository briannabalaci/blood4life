package domain;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class Appointment implements Entity<Long>, Serializable {
    private Long appointmentId;
    private Long userId;
    private Long patientId;
    private Long donationCentreId;
    private Date date;
    private Time time;

    public Appointment(Long userId, Long patientId, Long donationCentreId, Date date, Time time) {
        this.userId = userId;
        this.patientId = patientId;
        this.donationCentreId = donationCentreId;
        this.date = date;
        this.time = time;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDonationCentreId() {
        return donationCentreId;
    }

    public void setDonationCentreId(Long donationCentreId) {
        this.donationCentreId = donationCentreId;
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
