package repository;

import domain.User;
import domain.enums.BloodType;
import domain.enums.Rh;
import repository.abstractRepo.UserRepositoryInterface;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements UserRepositoryInterface {
    private final String databaseURL;
    private final String databaseUsername;
    private final String databasePassword;

    public UserRepository(String databaseURL, String databaseUsername, String databasePassword) {
        this.databaseURL = databaseURL;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
    }

    @Override
    public User findOne(Long id) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Users\" WHERE id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                return null;
            return getUserFromResultSet(resultSet);
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return null;
    }


    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Users\"");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                users.add(getUserFromResultSet(resultSet));
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return users;
    }

    @Override
    public void save(User user) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO public.\"Users\"(\"firstName\", \"lastName\", \"bloodType\", rh, email, height, weight, birthday, gender, cnp, points) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getBloodType().toString());
            preparedStatement.setString(4, user.getRh().toString());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setInt(6, user.getHeight());
            preparedStatement.setDouble(7, user.getWeight());
            preparedStatement.setDate(8, Date.valueOf(user.getBirthDate()));
            preparedStatement.setString(9, user.getGender());
            preparedStatement.setString(10, user.getCnp());
            preparedStatement.setLong(11, user.getPoints());
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM public.\"Users\" WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void update(User user) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE public.\"Users\" SET \"firstName\" = ?, \"lastName\" = ?, \"bloodType\" = ?, rh = ?, email = ?, height = ?, weight = ?, birthday = ?, gender = ?, cnp = ?, points = ? where id = ?");
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getBloodType().toString());
            preparedStatement.setString(4, user.getRh().toString());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setInt(6, user.getHeight());
            preparedStatement.setDouble(7, user.getWeight());
            preparedStatement.setDate(8, Date.valueOf(user.getBirthDate()));
            preparedStatement.setString(9, user.getGender());
            preparedStatement.setString(10, user.getCnp());
            preparedStatement.setLong(11, user.getPoints());
            preparedStatement.setLong(12, user.getID());
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
    }

    @Override
    public User findUserByCNP(String cnp) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Users\" WHERE cnp = ?");
            preparedStatement.setString(1, cnp);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                return null;
            return getUserFromResultSet(resultSet);
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return null;
    }

    @Override
    public List<User> findUsersByBloodTypeAndRh(BloodType bloodType, Rh rh) {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Users\" WHERE \"bloodType\" = ? AND rh = ?");
            preparedStatement.setString(1, bloodType.toString());
            preparedStatement.setString(2, rh.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(getUserFromResultSet(resultSet));
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return users;
    }

    private LocalDate convertToLocalDate(Date dateToConvert) {
        System.out.println(dateToConvert);
        return  Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User(resultSet.getString("firstName"), resultSet.getString("lastName"), convertToLocalDate(resultSet.getDate("birthday")), resultSet.getString("gender"), resultSet.getString("cnp"));
        user.setID(resultSet.getLong("id"));
        user.setBloodType(BloodType.valueOf(resultSet.getString("bloodType")));
        user.setRh(Rh.valueOf(resultSet.getString("rh")));
        user.setEmail(resultSet.getString("email"));
        user.setHeight(resultSet.getInt("height"));
        user.setWeight(resultSet.getDouble("weight"));
        user.setPoints(resultSet.getLong("points"));
        return user;
    }
}
