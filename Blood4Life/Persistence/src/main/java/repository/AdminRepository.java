package repository;

import domain.Admin;
import repository.abstractRepo.AdminRepositoryInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AdminRepository implements AdminRepositoryInterface {
    private final String url;
    private final String username;
    private final String password;
    private final Logger logger = Logger.getLogger("logging.txt");

    public AdminRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        logger.info("Initializing AdminRepository");
    }

    @Override
    public Admin findOne(String s) {
        Admin admin;
        try(Connection connection = DriverManager.getConnection(url, username, password);
            ResultSet result = connection.createStatement().executeQuery(String.format("select * from  \"Admins\" A where A.username =  '%s'", s))) {
            logger.info("Connecting to database in AdminRepository -> findOne");
            logger.info("Executing query in AdminRepository -> findOne");
            if (result.next()) {
                    String username = result.getString("username");
                    String password = result.getString("password");
                    admin = new Admin(username,password);
                    logger.info("Reading from database in AdminRepository -> findOne");
                    return admin;
                }
            } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting AdminRepository -> findOne with SQLException");
            System.exit(1);
            }
        return null;
    }

    @Override
    public List<Admin> findAll() {
        List<Admin> admins = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("select * from \"Admins\"");
            ResultSet result = statement.executeQuery()) {
            logger.info("Connecting to database in AdminRepository -> findAll");
            logger.info("Executing query in AdminRepository -> findAll");
            while( result.next() ){
                    String username = result.getString("username");
                    String password = result.getString("password");
                    Admin admin = new Admin(username,password);
                    admins.add(admin);
                }
            logger.info("Reading from database in AdminRepository -> findAll");
            } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting AdminRepository -> findAll with SQLException");
            System.exit(1);
            }
        return  admins;
    }

    @Override
    public void save(Admin entity) {
        String sql = "insert into \"Admins\" (username, password) values (?,?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preStm = connection.prepareStatement(sql)) {
            logger.info("Connecting to database in AdminRepository -> save");
            logger.info("Executing query in AdminRepository -> save");
            preStm.setString(1, entity.getID());
            preStm.setString(2, entity.getPassword());
            preStm.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting AdminRepository -> save with SQLException");
            System.exit(1);
        }

    }

    @Override
    public void delete(String s) {
        String sql = "delete from \"Admins\" where username = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preStm = connection.prepareStatement(sql)) {
            logger.info("Connecting to database in AdminRepository -> delete");
            logger.info("Executing query in AdminRepository -> delete");
            preStm.setString(1,s);
            preStm.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting AdminRepository -> delete with SQLException");
            System.exit(1);
        }
    }

    @Override
    public void update(Admin entity) {
        String sql = "update \"Admins\" set password = ?  where username = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preStm = connection.prepareStatement(sql)) {
            logger.info("Connecting to database in AdminRepository -> update");
            logger.info("Executing query in AdminRepository -> update");
            preStm.setString(1, entity.getPassword());
            preStm.setString(2, entity.getID());
            preStm.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting AdminRepository -> update with SQLException");
            System.exit(1);
        }
    }
}
