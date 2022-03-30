package domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;

public class User implements Entity<Integer>, Serializable {

    private Integer userID;
    private String firstName;
    private String lastName;
    private String bloodType;
    private String Rh;
    private String email;
    private Integer height;
    private Double weight;
    private LocalDate birthDate;
    private String gender;
    private String cnp;
    private Long points;

    @Override
    public Integer getID() {
        return userID;
    }

    @Override
    public void setID(Integer integer) {
        userID = integer;
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

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getRh() {
        return Rh;
    }

    public void setRh(String rh) {
        Rh = rh;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
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
