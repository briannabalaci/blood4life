package repository;

import domain.Address;
import domain.DonationCentre;
import exception.DatabaseException;
import repository.abstractRepo.AddressRepositoryInterface;
import repository.abstractRepo.DonationCentreRepositoryInterface;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DonationCentreRepository implements DonationCentreRepositoryInterface {
    private final String databaseURL;
    private final String databaseUsername;
    private final String databasePassword;
    private final AddressRepositoryInterface addressRepository;

    public DonationCentreRepository(String databaseURL, String databaseUsername, String databasePassword, AddressRepositoryInterface addressRepository) {
        this.databaseURL = databaseURL;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        this.addressRepository = addressRepository;
    }

    @Override
    public List<DonationCentre> findDonationCentresByCapacity(int capacity) {
        List<DonationCentre> donationCentres = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"DonationCentres\" WHERE capacity = ?");
            preparedStatement.setInt(1, capacity);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (!resultSet.next())
                donationCentres.add(getEntityFromDatabase(resultSet));
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return donationCentres;
    }

    @Override
    public List<DonationCentre> findDonationCentresByProgram(Time openHour, Time closeHour) {
        List<DonationCentre> donationCentres = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"DonationCentres\" WHERE openHour = ? AND closeHour = ?");
            preparedStatement.setTime(1, openHour);
            preparedStatement.setTime(2, closeHour);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (!resultSet.next())
                donationCentres.add(getEntityFromDatabase(resultSet));
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return donationCentres;
    }

    @Override
    public DonationCentre findDonationCentreByAddress(Address address) {
        DonationCentre donationCentre = null;
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"DonationCentres\" WHERE addressId = ?");
            preparedStatement.setLong(1, address.getID());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                donationCentre = getEntityFromDatabase(resultSet);
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return donationCentre;
    }

    @Override
    public DonationCentre findOne(Long id) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"DonationCentres\" WHERE id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                return null;
            return getEntityFromDatabase(resultSet);
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return null;
    }

    private DonationCentre getEntityFromDatabase(ResultSet resultSet) throws SQLException {
        Address address = addressRepository.findOne(resultSet.getLong("addressId"));
        Time openHour = resultSet.getTime("openHour");
        Time closeHour = resultSet.getTime("closeHour");
        DonationCentre donationCentre = new DonationCentre(address, resultSet.getString("name"), resultSet.getInt("capacity"),
                LocalTime.of(openHour.getHours(), openHour.getMinutes()),
                LocalTime.of(closeHour.getHours(), closeHour.getMinutes()));
        donationCentre.setID(resultSet.getLong("id"));
        return donationCentre;
    }

    @Override
    public List<DonationCentre> findAll() {
        List<DonationCentre> donationCentres = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"DonationCentres\"");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                donationCentres.add(getEntityFromDatabase(resultSet));
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return donationCentres;
    }

    @Override
    public void save(DonationCentre donationCentre) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO public.\"DonationCentres\"(\"addressId\", name, \"openHour\", \"closeHour\", capacity) VALUES (?, ?, ?, ?, ?)");
            addressRepository.save(donationCentre.getAddress());
            long addressId = addressRepository.findOne(donationCentre.getAddress().getCounty(),
                    donationCentre.getAddress().getLocality(), donationCentre.getAddress().getStreet(),
                    donationCentre.getAddress().getNumber()).getID();
            preparedStatement.setLong(1, addressId);
            preparedStatement.setString(2, donationCentre.getName());
            preparedStatement.setTime(3, Time.valueOf(donationCentre.getOpenHour()));
            preparedStatement.setTime(4, Time.valueOf(donationCentre.getCloseHour()));
            preparedStatement.setInt(5, donationCentre.getMaximumCapacity());
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            throw new DatabaseException("The donation centre cannot be added");
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM public.\"DonationCentres\" WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void update(DonationCentre donationCentre) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE public.\"DonationCentres\" SET addressId = ?, name = ?, openHour = ?, closeHour = ?, capacity = ? WHERE id = ?)");
            preparedStatement.setLong(1, donationCentre.getAddress().getID());
            preparedStatement.setString(2, donationCentre.getName());
            preparedStatement.setTime(3, Time.valueOf(donationCentre.getOpenHour()));
            preparedStatement.setTime(4, Time.valueOf(donationCentre.getCloseHour()));
            preparedStatement.setInt(5, donationCentre.getMaximumCapacity());
            preparedStatement.setLong(6, donationCentre.getID());
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
    }
}
