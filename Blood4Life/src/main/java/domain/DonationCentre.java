package domain;

import domain.Address;

import java.io.Serializable;
import java.sql.Time;
import java.util.Objects;

public class DonationCentre implements Entity<Long>, Serializable {
    private Long centreID;
    private Address address;
    private String name;
    private int maximumCapacity;
    private Time openHour;
    private Time closeHour;


    @Override
    public Long getID() {
        return centreID;
    }

    @Override
    public void setID(Long aLong) {
        this.centreID = aLong;
    }

    public DonationCentre(Address address, String name, int maximumCapacity, Time openHour, Time closeHour) {
        this.address = address;
        this.name = name;
        this.maximumCapacity = maximumCapacity;
        this.openHour = openHour;
        this.closeHour = closeHour;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    public void setMaximumCapacity(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    public Time getOpenHour() {
        return openHour;
    }

    public void setOpenHour(Time openHour) {
        this.openHour = openHour;
    }

    public Time getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(Time closeHour) {
        this.closeHour = closeHour;
    }
}
