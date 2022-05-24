package repository;

import domain.Address;
import exception.DatabaseException;
import repository.abstractRepo.AddressRepositoryInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AddressRepository implements AddressRepositoryInterface {
    private final String databaseURL;
    private final String databaseUsername;
    private final String databasePassword;
    private final Logger logger = Logger.getLogger("logging.txt");

    public AddressRepository(String databaseURL, String databaseUsername, String databasePassword) {
        this.databaseURL = databaseURL;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        logger.info("Initializing AddressRepository");
    }

    @Override
    public List<Address> findAddressesByCounty(String county) {
        List<Address> addresses = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in AddressRepository -> findAddressesByCounty");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Addresses\" WHERE county = ?");
            preparedStatement.setString(1, county);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("Executing query in AddressRepository -> findAddressesByCounty");
            while (!resultSet.next())
                addresses.add(getEntityFromDatabase(resultSet));
            logger.info("Reading from database in AddressRepository -> findAddressesByCounty");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting AddressRepository -> findAddressesByCounty with SQLException");
            System.exit(1);
        }
        return addresses;
    }

    @Override
    public List<Address> findAddressesByCity(String city) {
        List<Address> addresses = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in AddressRepository -> findAddressesByCity");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Addresses\" WHERE city = ?");
            preparedStatement.setString(1, city);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("Executing query in AddressRepository -> findAddressesByCity");
            while (!resultSet.next())
                addresses.add(getEntityFromDatabase(resultSet));
            logger.info("Reading from database in AddressRepository -> findAddressesByCity");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return addresses;
    }

    @Override
    public Address findOne(String county, String city, String street, int number) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in AddressRepository -> findOne");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Addresses\" WHERE county = ? AND city = ? AND street = ? AND number = ?");
            preparedStatement.setString(1, county);
            preparedStatement.setString(2, city);
            preparedStatement.setString(3, street);
            preparedStatement.setInt(4, number);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("Executing query in AddressRepository -> findOne");
            if (!resultSet.next())
                return null;
            logger.info("Reading from database in AddressRepository -> findOne");
            return getEntityFromDatabase(resultSet);
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return null;
    }

    @Override
    public Address findOne(Long id) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in AddressRepository -> findOne");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Addresses\" WHERE id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("Executing query in AddressRepository -> findOne");
            if (!resultSet.next())
                return null;
            logger.info("Reading from database in AddressRepository -> findOne");
            return getEntityFromDatabase(resultSet);
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return null;
    }

    private Address getEntityFromDatabase(ResultSet resultSet) throws SQLException {
        Address address = new Address(resultSet.getString("city"), resultSet.getString("county"), resultSet.getString("street"), resultSet.getInt("number"));
        address.setID(resultSet.getLong("id"));
        return address;
    }

    @Override
    public List<Address> findAll() {
        List<Address> addresses = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in AddressRepository -> findAll");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Addresses\"");
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("Executing query in AddressRepository -> findAll");
            while (!resultSet.next())
                addresses.add(getEntityFromDatabase(resultSet));
            logger.info("Reading from database in AddressRepository -> findAll");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return addresses;
    }

    @Override
    public void save(Address address) {
        if (findOne(address.getCounty(), address.getLocality(), address.getStreet(), address.getNumber()) != null)
            throw new DatabaseException("\nThe address is duplicated");
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in AddressRepository -> save");
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO public.\"Addresses\"(county, city, street, number) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, address.getCounty());
            preparedStatement.setString(2, address.getLocality());
            preparedStatement.setString(3, address.getStreet());
            preparedStatement.setInt(4, address.getNumber());
            preparedStatement.execute();
            logger.info("Executing query in AddressRepository -> save");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in AddressRepository -> delete");
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM public.\"Addresses\" WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            logger.info("Executing query in AddressRepository -> delete");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void update(Address address) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in AddressRepository -> update");
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE public.\"Addresses\" SET county = ?, city = ?, street = ?, number = ? WHERE id = ?");
            preparedStatement.setString(1, address.getCounty());
            preparedStatement.setString(2, address.getLocality());
            preparedStatement.setString(3, address.getStreet());
            preparedStatement.setInt(4, address.getNumber());
            preparedStatement.setLong(5, address.getID());
            preparedStatement.execute();
            logger.info("Executing query in AddressRepository -> update");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
    }
}
