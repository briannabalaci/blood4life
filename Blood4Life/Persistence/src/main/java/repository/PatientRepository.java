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
import java.util.logging.Logger;

public class PatientRepository implements PatientRepositoryInterface {
    private final String url;
    private final String username;
    private final String password;
    private final Logger logger = Logger.getLogger("logging.txt");

    public PatientRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        logger.info("Initializing PatientRepository");
    }

    @Override
    public Patient findOne(Long aLong) {
        Patient patient;
        try(Connection connection = DriverManager.getConnection(url, username, password);
            ResultSet result = connection.createStatement().executeQuery(String.format("select * from \"Patients\" P where P.id =  '%d'", aLong))) {
            logger.info("Connecting to database in PatientRepository -> findOne");
            logger.info("Executing query in PatientRepository -> findOne");
            if (result.next()) {
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
                logger.info("Reading from database in PatientRepository -> findOne");
                return patient;
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting PatientRepository -> findOne with SQLException");
            System.exit(1);
        }
        return null;
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> patients = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("select * from \"Patients\"");
            ResultSet result = statement.executeQuery()) {
            logger.info("Connecting to database in PatientRepository -> findAll");
            logger.info("Executing query in PatientRepository -> findAll");
            while (result.next()) {
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
            logger.info("Reading from database in PatientRepository -> findAll");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting PatientRepository -> findAll with SQLException");
            System.exit(1);
        }
        return patients;
    }

    @Override
    public void save(Patient entity) {
        String sql = "insert into public.\"Patients\" (\"firstName\", \"lastName\", \"bloodType\", rh, \"bloodQuantity\", cnp, birthday, severity) values (?,?,?,?,?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preStm = connection.prepareStatement(sql)) {
            logger.info("Connecting to database in PatientRepository -> save");
            preStm.setString(1, entity.getFirstName());
            preStm.setString(2,entity.getLastName());
            preStm.setString(3,entity.getBloodType().toString());
            preStm.setString(4,entity.getRh().toString());
            preStm.setInt(5, entity.getBloodQuantityNeeded());
            preStm.setString(6, entity.getCnp());
            preStm.setDate(7, Date.valueOf(entity.getBirthday()));
            preStm.setString(8, entity.getSeverity().toString());
            preStm.executeUpdate();
            logger.info("Executing query in PatientRepository -> save");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting PatientRepository -> save with SQLException");
            System.exit(1);
        }
    }

    @Override
    public void delete(Long aLong) {
        String sql = "delete from \"Patients\" where id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preStm = connection.prepareStatement(sql)) {
            logger.info("Connecting to database in PatientRepository -> delete");
            preStm.setLong(1, aLong);
            preStm.executeUpdate();
            logger.info("Executing query in PatientRepository -> delete");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting PatientRepository -> delete with SQLException");
            System.exit(1);
        }
    }

    @Override
    public void update(Patient entity) {
        String sql = "update \"Patients\" set \"bloodQuantity\" = ? where id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preStm = connection.prepareStatement(sql)) {
            logger.info("Connecting to database in PatientRepository -> update");
            preStm.setLong(1, entity.getBloodQuantityNeeded());
            preStm.setLong(2,entity.getID());
            preStm.executeUpdate();
            logger.info("Executing query in PatientRepository -> update");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting PatientRepository -> update with SQLException");
            System.exit(1);
        }
    }


    @Override
    public Patient findPatientsByQuantity(Integer quantity) {
        Patient patient;
        try (Connection connection = DriverManager.getConnection(url, username, password);
            ResultSet result = connection.createStatement().executeQuery(String.format("select * from \"Patients\" P where P.bloodquantity =  '%d'", quantity))) {
            logger.info("Connecting to database in PatientRepository -> findPatientsByQuantity");
            logger.info("Executing query in PatientRepository -> findPatientsByQuantity");
            if (result.next()) {
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
                logger.info("Reading from database in PatientRepository -> findPatientsByQuantity");
                return patient;
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting PatientRepository -> findPatientsByQuantity with SQLException");
            System.exit(1);
        }
        return null;
    }

    @Override
    public Patient findPatientsBySeverity(Severity severity) {
        Patient patient;
        try (Connection connection = DriverManager.getConnection(url, username, password);
            ResultSet result = connection.createStatement().executeQuery(String.format("select * from \"Patients\" P where P.severity =  '%s'", severity))) {
            logger.info("Connecting to database in PatientRepository -> findPatientsBySeverity");
            logger.info("Executing query in PatientRepository -> findPatientsBySeverity");
            if (result.next()) {
                Long id = result.getLong("id");
                String firstName = result.getString("firstname");
                String lastName = result.getString("lastname");
                String bloodType = result.getString("bloodtype");
                String rh = result.getString("rh");
                int bloodQuantity = result.getInt("bloodquantity");
                String cnp = result.getString("cnp");
                LocalDate birthday =LocalDate.parse(result.getString("birthday"));
                String severity0 = result.getString("severity");
                patient = new Patient(cnp, firstName, lastName, birthday, BloodType.valueOf(bloodType), Rh.valueOf(rh), Severity.valueOf(severity0), bloodQuantity);
                patient.setID(id);
                logger.info("Reading from database in PatientRepository -> findPatientsBySeverity");
                return patient;
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting PatientRepository -> findPatientsBySeverity with SQLException");
            System.exit(1);
        }
        return null;
    }

    @Override
    public List<Patient> findPatientsByBloodTypeAndRh(BloodType bloodType, Rh rh) {
        List<Patient> patients = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url, username, password);
            ResultSet result = connection.createStatement().executeQuery(String.format("select * from \"Patients\"  where \"bloodType\" = '%s' and \"rh\" = '%s' ", bloodType,rh))) {
            logger.info("Connecting to database in PatientRepository -> findPatientsByBloodTypeAndRh");
            logger.info("Executing query in PatientRepository -> findPatientsByBloodTypeAndRh");
            while (result.next()) {
                Long id = result.getLong("id");
                String firstName = result.getString("firstname");
                String lastName = result.getString("lastname");
                String bloodType0 = result.getString("bloodType");
                String rh0 = result.getString("rh");
                int bloodQuantity = result.getInt("bloodQuantity");
                String cnp = result.getString("cnp");
                LocalDate birthday =LocalDate.parse(result.getString("birthday"));
                String severity = result.getString("severity");
                Patient patient = new Patient(cnp, firstName, lastName, birthday, BloodType.valueOf(bloodType0), Rh.valueOf(rh0), Severity.valueOf(severity), bloodQuantity);
                patient.setID(id);
                patients.add(patient);
            }
            logger.info("Reading from database in PatientRepository -> findPatientsByBloodTypeAndRh");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting PatientRepository -> findPatientsByBloodTypeAndRh with SQLException");
            System.exit(1);
        }
        return patients;
    }
}
