package domain;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalTime;

@Entity
@Table(name = "DonationCentres")
public class DonationCentre implements IEntity<Long>, Serializable {
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

    @OneToOne
    @Column(name = "AddressId")
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "MaximumCapacity")
    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    public void setMaximumCapacity(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    @Column(name = "OpenHour")
    public LocalTime getOpenHour() {
        return openHour;
    }

    public void setOpenHour(LocalTime openHour) {
        this.openHour = openHour;
    }

    @Column(name = "CloseHour")
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
