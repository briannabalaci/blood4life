package repository;

import domain.Address;
import domain.DonationCentre;

import java.sql.Time;
import java.util.List;

public class DonationCentreRepository implements DonationCentreRepositoryInterface{
    @Override
    public List<DonationCentre> findDonationCentresByCapacity(int capacity) {
        return null;
    }

    @Override
    public List<DonationCentre> findDonationCentresByProgram(Time openHour, Time closeHour) {
        return null;
    }

    @Override
    public DonationCentre findDonationCentreByAddress(Address address) {
        return null;
    }

    @Override
    public DonationCentre findOne(Long id) {
        return null;
    }

    @Override
    public List<DonationCentre> findAll() {
        return null;
    }

    @Override
    public void save(DonationCentre donationCentre) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(DonationCentre donationCentre) {

    }
}
