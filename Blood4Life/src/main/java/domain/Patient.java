package domain;

import domain.enums.BloodType;
import domain.enums.Gravity;
import domain.enums.Rh;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Patient implements Entity<Long>, Serializable {
    private Long patientId;
    private String cnp;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private BloodType bloodType;
    private Rh rh;
    private Gravity gravity;
    private Integer bloodQuantityNeeded;

    public Patient( String cnp, String firstName, String lastName, LocalDate birthday, BloodType bloodType, Rh rh, Gravity gravity, Integer bloodQuantityNeeded) {
        this.cnp = cnp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.bloodType = bloodType;
        this.rh = rh;
        this.gravity = gravity;
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

    public Gravity getGravity() {
        return gravity;
    }

    public void setGravity(Gravity gravity) {
        this.gravity = gravity;
    }

    public Integer getBloodQuantityNeeded() {
        return bloodQuantityNeeded;
    }

    public void setBloodQuantityNeeded(Integer bloodQuantityNeeded) {
        this.bloodQuantityNeeded = bloodQuantityNeeded;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientId=" + patientId +
                ", cnp='" + cnp + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                ", bloodType=" + bloodType +
                ", rh=" + rh +
                ", gravity=" + gravity +
                ", bloodQuantityNeeded=" + bloodQuantityNeeded +
                '}';
    }
}
