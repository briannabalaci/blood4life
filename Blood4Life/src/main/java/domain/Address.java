package domain;

import java.io.Serializable;
import java.util.Objects;

public class Address implements Entity<Long>, Serializable {
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

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
