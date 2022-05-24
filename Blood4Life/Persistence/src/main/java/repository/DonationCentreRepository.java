package repository;

import domain.Address;
import domain.DonationCentre;
import exception.DatabaseException;
import repository.abstractRepo.AddressRepositoryInterface;
import repository.abstractRepo.DonationCentreRepositoryInterface;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DonationCentreRepository implements DonationCentreRepositoryInterface {
    private final String databaseURL;
    private final String databaseUsername;
    private final String databasePassword;
    private final AddressRepositoryInterface addressRepository;
    private final Logger logger = Logger.getLogger("logging.txt");

    public DonationCentreRepository(String databaseURL, String databaseUsername, String databasePassword, AddressRepositoryInterface addressRepository) {
        this.databaseURL = databaseURL;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        this.addressRepository = addressRepository;
        logger.info("Initializing DonationCentreRepository");
    }

    @Override
    public List<DonationCentre> findDonationCentresByCapacity(int capacity) {
        List<DonationCentre> donationCentres = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in DonationCentreRepository -> findDonationCentresByCapacity");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"DonationCentres\" WHERE capacity = ?");
            preparedStatement.setInt(1, capacity);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("Executing query in DonationCentreRepository -> findDonationCentresByCapacity");
            while (!resultSet.next())
                donationCentres.add(getEntityFromDatabase(resultSet));
            logger.info("Executing query in DonationCentreRepository -> findDonationCentresByCapacity");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting DonationCentreRepository -> findDonationCentresByCapacity with SQLException");
            System.exit(1);
        }
        return donationCentres;
    }

    @Override
    public List<DonationCentre> findDonationCentresByProgram(Time openHour, Time closeHour) {
        List<DonationCentre> donationCentres = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in DonationCentreRepository -> findDonationCentresByProgram");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"DonationCentres\" WHERE openHour = ? AND closeHour = ?");
            preparedStatement.setTime(1, openHour);
            preparedStatement.setTime(2, closeHour);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("Executing query in DonationCentreRepository -> findDonationCentresByProgram");
            while (!resultSet.next())
                donationCentres.add(getEntityFromDatabase(resultSet));
            logger.info("Executing query in DonationCentreRepository -> findDonationCentresByProgram");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting DonationCentreRepository -> findDonationCentresByProgram with SQLException");
            System.exit(1);
        }
        return donationCentres;
    }

    @Override
    public DonationCentre findDonationCentreByAddress(Address address) {
        DonationCentre donationCentre = null;
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in DonationCentreRepository -> findDonationCentreByAddress");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"DonationCentres\" WHERE addressId = ?");
            preparedStatement.setLong(1, address.getID());
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("Executing query in DonationCentreRepository -> findDonationCentreByAddress");
            if (!resultSet.next())
                donationCentre = getEntityFromDatabase(resultSet);
            logger.info("Executing query in DonationCentreRepository -> findDonationCentreByAddress");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting DonationCentreRepository -> findDonationCentreByAddress with SQLException");
            System.exit(1);
        }
        return donationCentre;
    }

    @Override
    public DonationCentre findOne(Long id) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in DonationCentreRepository -> findOne");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"DonationCentres\" WHERE id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("Executing query in DonationCentreRepository -> findOne");
            if (!resultSet.next())
                return null;
            logger.info("Executing query in DonationCentreRepository -> findOne");
            return getEntityFromDatabase(resultSet);
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting DonationCentreRepository -> findOne with SQLException");
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
            logger.info("Connecting to database in DonationCentreRepository -> findAll");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"DonationCentres\"");
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("Executing query in DonationCentreRepository -> findAll");
            while (resultSet.next())
                donationCentres.add(getEntityFromDatabase(resultSet));
            logger.info("Executing query in DonationCentreRepository -> findAll");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting DonationCentreRepository -> findAll with SQLException");
            System.exit(1);
        }
        return donationCentres;
    }

    @Override
    public void save(DonationCentre donationCentre) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in DonationCentreRepository -> save");
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
            logger.info("Executing query in DonationCentreRepository -> save");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting DonationCentreRepository -> save with SQLException");
            throw new DatabaseException("The donation centre cannot be added");
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in DonationCentreRepository -> delete");
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM public.\"DonationCentres\" WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            logger.info("Executing query in DonationCentreRepository -> delete");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting DonationCentreRepository -> delete with SQLException");
            System.exit(1);
        }
    }

    @Override
    public void update(DonationCentre donationCentre) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in DonationCentreRepository -> update");
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE public.\"DonationCentres\" SET addressId = ?, name = ?, openHour = ?, closeHour = ?, capacity = ? WHERE id = ?)");
            preparedStatement.setLong(1, donationCentre.getAddress().getID());
            preparedStatement.setString(2, donationCentre.getName());
            preparedStatement.setTime(3, Time.valueOf(donationCentre.getOpenHour()));
            preparedStatement.setTime(4, Time.valueOf(donationCentre.getCloseHour()));
            preparedStatement.setInt(5, donationCentre.getMaximumCapacity());
            preparedStatement.setLong(6, donationCentre.getID());
            preparedStatement.execute();
            logger.info("Executing query in DonationCentreRepository -> update");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting DonationCentreRepository -> update with SQLException");
            System.exit(1);
        }
    }
}
