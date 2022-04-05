package repository;

import domain.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressRepository implements AddressRepositoryInterface {
    private final String databaseURL;
    private final String databaseUsername;
    private final String databasePassword;

    public AddressRepository(String databaseURL, String databaseUsername, String databasePassword) {
        this.databaseURL = databaseURL;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
    }

    @Override
    public List<Address> findAddressesByCounty(String county) {
        List<Address> addresses = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Addresses\" WHERE county = ?");
            preparedStatement.setString(1, county);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (!resultSet.next())
                addresses.add(getEntityFromDatabase(resultSet));
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return addresses;
    }

    @Override
    public List<Address> findAddressesByCity(String city) {
        List<Address> addresses = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Addresses\" WHERE city = ?");
            preparedStatement.setString(1, city);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (!resultSet.next())
                addresses.add(getEntityFromDatabase(resultSet));
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return addresses;
    }

    @Override
    public Address findOne(Long id) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Addresses\" WHERE id = ?");
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

    private Address getEntityFromDatabase(ResultSet resultSet) throws SQLException {
        Address address = new Address(resultSet.getString("city"), resultSet.getString("county"), resultSet.getString("street"), resultSet.getInt("number"));
        address.setID(resultSet.getLong("id"));
        return address;
    }

    @Override
    public List<Address> findAll() {
        List<Address> addresses = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Addresses\"");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (!resultSet.next())
                addresses.add(getEntityFromDatabase(resultSet));
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return addresses;
    }

    @Override
    public void save(Address address) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO public.\"Adresses\"(county, city, street, number) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, address.getCounty());
            preparedStatement.setString(2, address.getLocality());
            preparedStatement.setString(3, address.getStreet());
            preparedStatement.setInt(4, address.getNumber());
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM public.\"Addresses\" WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void update(Address address) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE public.\"Adresses\" SET county = ?, city = ?, street = ?, number = ? WHERE id = ?");
            preparedStatement.setString(1, address.getCounty());
            preparedStatement.setString(2, address.getLocality());
            preparedStatement.setString(3, address.getStreet());
            preparedStatement.setInt(4, address.getNumber());
            preparedStatement.setLong(5, address.getID());
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
    }
}
