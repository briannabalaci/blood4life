package domain;

import domain.Address;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Objects;

public class DonationCentre implements Entity<Long>, Serializable {
    private Long centreID;
    private Address address;
    private String name;
    private int maximumCapacity;
    private LocalTime openHour;
    private LocalTime closeHour;


    @Override
    public Long getID() {
        return centreID;
    }

    @Override
    public void setID(Long aLong) {
        this.centreID = aLong;
    }

    public DonationCentre(Address address, String name, int maximumCapacity, LocalTime openHour, LocalTime closeHour) {
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

    public LocalTime getOpenHour() {
        return openHour;
    }

    public void setOpenHour(LocalTime openHour) {
        this.openHour = openHour;
    }

    public LocalTime getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(LocalTime closeHour) {
        this.closeHour = closeHour;
    }

    @Override
    public String toString() {
        return name + " (" + address.getCounty() + ", " + address.getLocality() + ", " + address.getStreet() + ", " + address.getNumber() + ")";
    }
}
