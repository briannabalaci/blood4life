package domain;

import domain.enums.BloodType;
import domain.enums.Gender;
import domain.enums.Rh;

import java.io.Serializable;
import java.time.LocalDate;

public class User implements IEntity<Long>, Serializable {

    private Long userID;
    private String firstName;
    private String lastName;
    private BloodType bloodType;
    private Rh rh;
    private String email;
    private Integer height;
    private Double weight;
    private LocalDate birthDate;
    private Gender gender;
    private String cnp;
    private Long points;

    public User(String firstName, String lastName, LocalDate birthDate, Gender gender, String cnp) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.cnp = cnp;
    }

    public User(String firstName, String lastName, BloodType bloodType, Rh rh, String email, Integer height, Double weight, LocalDate birthDate, Gender gender, String cnp) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bloodType = bloodType;
        this.rh = rh;
        this.email = email;
        this.height = height;
        this.weight = weight;
        this.birthDate = birthDate;
        this.gender = gender;
        this.cnp = cnp;
        this.points = 0L;
    }

    @Override
    public Long getID() {
        return userID;
    }

    @Override
    public void setID(Long id) {
        userID = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public Long getPoints() { return points; }

    public void setPoints(Long points) { this.points = points; }
}
