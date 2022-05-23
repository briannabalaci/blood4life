package domain;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "Addresses")
public class Address implements IEntity<Long>, Serializable {
    private Long addressID;
    private String locality;
    private String county;
    private String street;
    private int number;

    @Override
    public Long getID() {
        return addressID;
    }

    @Override
    public void setID(Long aLong) {
        this.addressID = aLong;
    }

    public Address(String locality, String county, String street, int number) {
        this.locality = locality;
        this.county = county;
        this.street = street;
        this.number = number;
    }

    @Column(name = "City")
    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    @Column(name = "County")
    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Column(name = "Street")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Column(name = "Number")
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
