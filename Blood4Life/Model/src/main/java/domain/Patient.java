package domain;

import domain.enums.BloodType;
import domain.enums.Severity;
import domain.enums.Rh;

import java.io.Serializable;
import java.time.LocalDate;

public class Patient implements Entity<Long>, Serializable {
    private Long patientId;
    private String cnp;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private BloodType bloodType;
    private Rh rh;
    private Severity severity;
    private Integer bloodQuantityNeeded;

    public Patient(String cnp, String firstName, String lastName, LocalDate birthday, BloodType bloodType, Rh rh, Severity severity, Integer bloodQuantityNeeded) {
        this.cnp = cnp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.bloodType = bloodType;
        this.rh = rh;
        this.severity = severity;
        this.bloodQuantityNeeded = bloodQuantityNeeded;
    }

    @Override
    public Long getID() {
        return patientId;
    }

    @Override
    public void setID(Long patientId) {
        this.patientId = patientId;
    }
    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public Rh getRh() {
        return rh;
    }

    public void setRh(Rh rh) {
        this.rh = rh;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setGravity(Severity severity) {
        this.severity = severity;
    }

    public Integer getBloodQuantityNeeded() {
        return bloodQuantityNeeded;
    }

    public void setBloodQuantityNeeded(Integer bloodQuantityNeeded) {
        this.bloodQuantityNeeded = bloodQuantityNeeded;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + severity + " gravity, " + bloodQuantityNeeded + " ml blood needed)";
    }
}
