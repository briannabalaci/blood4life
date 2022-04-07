package repository;

import domain.Patient;
import domain.enums.BloodType;
import domain.enums.Severity;
import domain.enums.Rh;
import repository.abstractRepo.PatientRepositoryInterface;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientRepository implements PatientRepositoryInterface {

    private final String url;
    private final String username;
    private final String password;

    public PatientRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Patient findOne(Long aLong) {
        //commonUtils.logger.traceEntry();
        Patient patient;
        try(Connection connection = DriverManager.getConnection(url, username, password);
            ResultSet result = connection.createStatement().executeQuery(String.format("select * from \"Patients\" P where P.id =  '%d'", aLong))) {
            if(result.next()){
                Long id = result.getLong("id");
                String firstName = result.getString("firstname");
                String lastName = result.getString("lastname");
                String bloodType = result.getString("bloodtype");
                String rh = result.getString("rh");
                int bloodQuantity = result.getInt("bloodquantity");
                String cnp = result.getString("cnp");
                LocalDate birthday =LocalDate.parse(result.getString("birthday"));
                String severity = result.getString("severity");
                patient = new Patient(cnp, firstName, lastName, birthday, BloodType.valueOf(bloodType), Rh.valueOf(rh), Severity.valueOf(severity), bloodQuantity);
                patient.setID(id);
                patient.setBloodQuantityNeeded(bloodQuantity);
                patient.setGravity(Severity.valueOf(severity));
                patient.setBloodType(BloodType.valueOf(bloodType));
                patient.setRh(Rh.valueOf(rh));
                return patient;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Patient> findAll() {
        //commonUtils.logger.traceEntry();
        List<Patient> patients = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement statement = connection.prepareStatement("select * from Patients");
            ResultSet result = statement.executeQuery()){
            while( result.next() ){
                Long id = result.getLong("id");
                String firstName = result.getString("firstname");
                String lastName = result.getString("lastname");
                String bloodType = result.getString("bloodtype");
                String rh = result.getString("rh");
                int bloodQuantity = result.getInt("bloodquantity");
                String cnp = result.getString("cnp");
                LocalDate birthday = LocalDate.parse(result.getString("birthday"));
                String severity = result.getString("severity");
                Patient patient = new Patient(cnp, firstName, lastName, birthday, BloodType.valueOf(bloodType), Rh.valueOf(rh), Severity.valueOf(severity), bloodQuantity);
                patient.setID(id);
                patient.setBloodQuantityNeeded(bloodQuantity);
                patient.setGravity(Severity.valueOf(severity));
                patient.setBloodType(BloodType.valueOf(bloodType));
                patient.setRh(Rh.valueOf(rh));
                patients.add(patient);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    @Override
    public void save(Patient entity) {
        //commonUtils.logger.traceEntry("saving task {}",entity);
        String sql = "insert into public.\"Patients\" (\"firstName\", \"lastName\", \"bloodType\", rh, \"bloodQuantity\", cnp, birthday, severity) values (?,?,?,?,?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preStm = connection.prepareStatement(sql)) {
            preStm.setString(1, entity.getFirstName());
            preStm.setString(2,entity.getLastName());
            preStm.setString(3,entity.getBloodType().toString());
            preStm.setString(4,entity.getRh().toString());
            preStm.setInt(5, entity.getBloodQuantityNeeded());
            preStm.setString(6, entity.getCnp());
            preStm.setDate(7, Date.valueOf(entity.getBirthday()));
            preStm.setString(8, entity.getSeverity().toString());
            int result = preStm.executeUpdate();
            //commonUtils.logger.trace("Saved instances {}",result);
        } catch (SQLException ex) {
            //commonUtils.logger.error(ex);
            System.err.println("Error DB"+ex);
        }
        //commonUtils.logger.traceExit();
    }

    @Override
    public void delete(Long aLong) {
        //commonUtils.logger.traceEntry();
        String sql = "delete from patients where id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preStm = connection.prepareStatement(sql)) {
            preStm.setLong(1,aLong);
            int result = preStm.executeUpdate();
            //commonUtils.logger.trace("Deleted instances {}",result);
        } catch (SQLException ex) {
            //commonUtils.logger.error(ex);
            System.err.println("Error DB"+ex);
        }
        //commonUtils.logger.traceExit();
    }

    @Override
    public void update(Patient entity) {
        //        logger.traceEntry();
        String sql = "update patients set firstname = ? , lastname = ?   where id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preStm = connection.prepareStatement(sql)) {
            preStm.setString(1, entity.getFirstName());
            preStm.setString(2, entity.getLastName());
            preStm.setLong(3,entity.getID());
            int result = preStm.executeUpdate();
            //logger.trace("Upadated instances {}",result);
        } catch (SQLException ex) {
            //logger.error(ex);
            System.err.println("Error DB"+ex);
        }
        //logger.traceExit();
    }
}
