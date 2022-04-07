package repository;

import domain.Admin;
import repository.abstractRepo.AdminRepositoryInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminRepository implements AdminRepositoryInterface {
    private String url;
    private String username;
    private String password;

    public AdminRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Admin findOne(String s) {
        //commonUtils.logger.traceEntry();
        Admin admin;
        try(Connection connection = DriverManager.getConnection(url, username, password);
            ResultSet result = connection.createStatement().executeQuery(String.format("select * from admins P where P.username =  '%s'", s))) {
            if(result.next()){
                    String username = result.getString("username");
                    String password = result.getString("password");
                    admin = new Admin(username,password);
                    return admin;
                }
            } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Admin> findAll() {
        //commonUtils.logger.traceEntry();
        List<Admin> admins = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement statement = connection.prepareStatement("select * from admins");
            ResultSet result = statement.executeQuery()){
            while( result.next() ){
                    String username = result.getString("username");
                    String password = result.getString("password");
                    Admin admin = new Admin(username,password);
                    admins.add(admin);
                }
            } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  admins;
    }

    @Override
    public void save(Admin entity) {
        //commonUtils.logger.traceEntry("saving task {}",elem);
        String sql = "insert into Admins (username, password) values (?,?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preStm = connection.prepareStatement(sql)) {
            preStm.setString(1, entity.getID());
            preStm.setString(2, entity.getPassword());
            int result = preStm.executeUpdate();
            //commonUtils.logger.trace("Saved instances {}",result);
        } catch (SQLException ex) {
            //commonUtils.logger.error(ex);
            System.err.println("Error DB" + ex);
        }

    }

    @Override
    public void delete(String s) {
        //commonUtils.logger.traceEntry();
        String sql = "delete from admins where username = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preStm = connection.prepareStatement(sql)) {
            preStm.setString(1,s);
            int result = preStm.executeUpdate();
            //commonUtils.logger.trace("Deleted instances {}",result);
        } catch (SQLException ex) {
            //commonUtils.logger.error(ex);
            System.err.println("Error DB"+ex);
        }
        //commonUtils.logger.traceExit();
    }

    @Override
    public void update(Admin entity) {
        //        logger.traceEntry();
        String sql = "update admins set password = ?  where username = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preStm = connection.prepareStatement(sql)) {
            preStm.setString(1, entity.getPassword());
            preStm.setString(2, entity.getID());
            int result = preStm.executeUpdate();
            //logger.trace("Upadated instances {}",result);
        } catch (SQLException ex) {
            //logger.error(ex);
            System.err.println("Error DB"+ex);
        }
        //logger.traceExit();
    }
}
