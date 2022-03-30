package domain;

import domain.enums.BloodType;
import domain.enums.Gravity;
import domain.enums.Rh;

import java.io.Serializable;
import java.util.Date;

public class Patient implements Entity<Long>, Serializable {
    private Long patientId;
    private String cnp;
    private String firstName;
    private String lastName;
    private Date birthday;
    private BloodType bloodType;
    private Rh rh;
    private Gravity gravity;
    private Integer bloodQuantityNeeded;

    public Patient(String cnp, String firstName, String lastName, Date birthday) {
        this.cnp = cnp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
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

}
