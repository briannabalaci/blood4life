package repository;

import domain.Appointment;
import domain.DonationCentre;
import domain.User;
import repository.abstractRepo.AppointmentRepositoryInterface;
import repository.abstractRepo.DonationCentreRepositoryInterface;
import repository.abstractRepo.PatientRepositoryInterface;
import repository.abstractRepo.UserRepositoryInterface;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class AppointmentRepository implements AppointmentRepositoryInterface {
    private final String databaseURL;
    private final String databaseUsername;
    private final String databasePassword;
    private final UserRepositoryInterface userRepository;
    private final PatientRepositoryInterface patientRepository;
    private final DonationCentreRepositoryInterface donationCentreRepository;
    private final Logger logger = Logger.getLogger("logging.txt");

    public AppointmentRepository(String databaseURL, String databaseUsername, String databasePassword, UserRepositoryInterface userRepository, PatientRepositoryInterface patientRepository, DonationCentreRepositoryInterface donationCentreRepository) {
        this.databaseURL = databaseURL;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.donationCentreRepository = donationCentreRepository;
        logger.info("Initializing AppointmentRepository");
    }

    @Override
    public List<Appointment> findAppointmentsByDateTime(Date date, Time time) {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in AppointmentRepository -> findAppointmentsByDateTime");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Appointments\" WHERE date = ? AND time = ?");
            preparedStatement.setDate(1, (java.sql.Date) date);
            preparedStatement.setTime(2, time);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("Executing query in AppointmentRepository -> findAppointmentsByDateTime");
            while (resultSet.next())
                appointments.add(getAppointmentFromDatabase(resultSet));
            logger.info("Reading from database in AppointmentRepository -> findAppointmentsByDateTime");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting AppointmentRepository -> findAppointmentsByDateTime with SQLException");
            System.exit(1);
        }
        return appointments;
    }

    @Override
    public List<Appointment> findAppointmentsByDonationCentre(DonationCentre donationCentre) {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in AppointmentRepository -> findAppointmentsByDonationCentre");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Appointments\" WHERE donationCentreId = ?");
            preparedStatement.setLong(1, donationCentre.getID());
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("Executing query in AppointmentRepository -> findAppointmentsByDonationCentre");
            while (resultSet.next())
                appointments.add(getAppointmentFromDatabase(resultSet));
            logger.info("Reading from database in AppointmentRepository -> findAppointmentsByDonationCentre");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting AppointmentRepository -> findAppointmentsByDonationCentre with SQLException");
            System.exit(1);
        }
        return appointments;
    }

    @Override
    public List<Appointment> findAppointmentsByUser(User user) {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in AppointmentRepository -> findAppointmentsByUser");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Appointments\" WHERE \"userId\" = ?");
            preparedStatement.setLong(1, user.getID());
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("Executing query in AppointmentRepository -> findAppointmentsByUser");
            while (resultSet.next())
                appointments.add(getAppointmentFromDatabase(resultSet));
            logger.info("Reading from database in AppointmentRepository -> findAppointmentsByUser");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting AppointmentRepository -> findAppointmentsByUser with SQLException");
            System.exit(1);
        }
        return appointments;
    }

    @Override
    public List<Appointment> findPreviousAppointmentsByUser(User user, int startPosition, int returnedRowsNo) {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in AppointmentRepository -> findPreviousAppointmentsByUser");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Appointments\" WHERE \"userId\" = ? AND \"date\" < now() LIMIT ? OFFSET ?");
            preparedStatement.setLong(1, user.getID());
            preparedStatement.setInt(2, returnedRowsNo);
            preparedStatement.setInt(3, startPosition);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("Executing query in AppointmentRepository -> findPreviousAppointmentsByUser");
            while (resultSet.next())
                appointments.add(getAppointmentFromDatabase(resultSet));
            logger.info("Reading from database in AppointmentRepository -> findPreviousAppointmentsByUser");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting AppointmentRepository -> findPreviousAppointmentsByUser with SQLException");
            System.exit(1);
        }
        return appointments;
    }

    @Override
    public int countPreviousAppointmentsByUser(User user) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in AppointmentRepository -> countPreviousAppointmentsByUser");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM public.\"Appointments\" WHERE \"userId\" = ? AND \"date\" < now()");
            preparedStatement.setLong(1, user.getID());
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("Executing query in AppointmentRepository -> countPreviousAppointmentsByUser");
            if (resultSet.next()) {
                logger.info("Reading from database in AppointmentRepository -> countPreviousAppointmentsByUser");
                return resultSet.getInt(1);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting AppointmentRepository -> countPreviousAppointmentsByUser with SQLException");
            System.exit(1);
        }
        return 0;
    }

    @Override
    public int countFutureAppointmentsByUser(User user) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in AppointmentRepository -> countPreviousAppointmentsByUser");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM public.\"Appointments\" WHERE \"userId\" = ? AND \"date\" >= now()");
            preparedStatement.setLong(1, user.getID());
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("Executing query in AppointmentRepository -> countFutureAppointmentsByUser");
            if (resultSet.next()) {
                logger.info("Reading from database in AppointmentRepository -> countFutureAppointmentsByUser");
                return resultSet.getInt(1);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting AppointmentRepository -> countFutureAppointmentsByUser with SQLException");
            System.exit(1);
        }
        return 0;
    }

    @Override
    public List<Appointment> findFutureAppointmentsByUser(User user, int startPosition, int returnedRowsNo) {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in AppointmentRepository -> findFutureAppointmentsByUser");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Appointments\" WHERE \"userId\" = ?  AND \"date\" >= now() LIMIT ? OFFSET ?");
            preparedStatement.setLong(1, user.getID());
            preparedStatement.setInt(2, returnedRowsNo);
            preparedStatement.setInt(3, startPosition);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("Executing query in AppointmentRepository -> findFutureAppointmentsByUser");
            while (resultSet.next())
                appointments.add(getAppointmentFromDatabase(resultSet));
            logger.info("Reading from database in AppointmentRepository -> findFutureAppointmentsByUser");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting AppointmentRepository -> findFutureAppointmentsByUser with SQLException");
            System.exit(1);
        }
        return appointments;
    }

    @Override
    public Integer findNumberAppointmentsAtCenterDate(DonationCentre donationCentre, Date date) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in AppointmentRepository -> findNumberAppointmentsAtCenterDate");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM public.\"Appointments\" WHERE \"donationCentreId\" = ? and date = ?");
            preparedStatement.setLong(1, donationCentre.getID());
            preparedStatement.setDate(2, (java.sql.Date) date);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("Executing query in AppointmentRepository -> findNumberAppointmentsAtCenterDate");
            if (resultSet.next()) {
                logger.info("Reading from database in AppointmentRepository -> findNumberAppointmentsAtCenterDate");
                return resultSet.getInt(1);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting AppointmentRepository -> findNumberAppointmentsAtCenterDate with SQLException");
            System.exit(1);
        }
        return null;
    }

    @Override
    public Integer findNumberAppointmentsAtCenterDateTime(DonationCentre donationCentre, Date date, Time time) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in AppointmentRepository -> findNumberAppointmentsAtCenterDateTime");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM public.\"Appointments\" WHERE \"donationCentreId\" = ? and date = ? and time = ?");
            preparedStatement.setLong(1, donationCentre.getID());
            preparedStatement.setDate(2, (java.sql.Date) date);
            preparedStatement.setTime(3, time);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("Executing query in AppointmentRepository -> findNumberAppointmentsAtCenterDateTime");
            if (resultSet.next()) {
                logger.info("Reading from database in AppointmentRepository -> findNumberAppointmentsAtCenterDateTime");
                return resultSet.getInt(1);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting AppointmentRepository -> findNumberAppointmentsAtCenterDateTime with SQLException");
            System.exit(1);
        }
        return null;
    }

    @Override
    public Appointment findOne(Long id) {
        Appointment appointment = null;
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in AppointmentRepository -> findOne");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Appointments\" WHERE id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("Executing query in AppointmentRepository -> findOne");
            if (resultSet.next())
                appointment = getAppointmentFromDatabase(resultSet);
            logger.info("Reading from database in AppointmentRepository -> findOne");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting AppointmentRepository -> findOne with SQLException");
            System.exit(1);
        }
        return appointment;
    }

    @Override
    public List<Appointment> findAll() {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in AppointmentRepository -> findAll");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Appointments\"");
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("Executing query in AppointmentRepository -> findAll");
            while (resultSet.next())
                appointments.add(getAppointmentFromDatabase(resultSet));
            logger.info("Reading from database in AppointmentRepository -> findAll");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting AppointmentRepository -> findAll with SQLException");
            System.exit(1);
        }
        return appointments;
    }

    @Override
    public void save(Appointment appointment) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in AppointmentRepository -> save");
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO public.\"Appointments\"(\"userId\", \"patientId\", \"donationCentreId\", date, time) VALUES (?, ?, ?, ?, ?)");
            setQueryStatement(appointment, preparedStatement);
            preparedStatement.execute();
            logger.info("Executing query in AppointmentRepository -> save");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting AppointmentRepository -> save with SQLException");
            System.exit(1);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in AppointmentRepository -> delete");
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM public.\"Appointments\" WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            logger.info("Executing query in AppointmentRepository -> delete");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting AppointmentRepository -> delete with SQLException");
            System.exit(1);
        }
    }

    @Override
    public void update(Appointment appointment) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            logger.info("Connecting to database in AppointmentRepository -> update");
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE public.\"Appointments\" SET userId = ?, pacientId = ?, donationCentreId = ?, date = ?, time = ? WHERE id = ?)");
            setQueryStatement(appointment, preparedStatement);
            preparedStatement.setLong(6, appointment.getID());
            preparedStatement.execute();logger.info("Executing query in AppointmentRepository -> update");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            logger.severe("Exiting AppointmentRepository -> update with SQLException");
            System.exit(1);
        }
    }

    private void setQueryStatement(Appointment appointment, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, appointment.getUser().getID());
        preparedStatement.setLong(2, appointment.getPatient().getID());
        preparedStatement.setLong(3, appointment.getDonationCentre().getID());
        preparedStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.of(appointment.getDate(), LocalTime.now())));
        preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), appointment.getTime())));
    }

    private Appointment getAppointmentFromDatabase(ResultSet resultSet) throws SQLException {
        long appointmentId = resultSet.getLong("id");
        long userId = resultSet.getLong("userId");
        long patientId = resultSet.getLong("patientId");
        long donationCentreId = resultSet.getLong("donationCentreId");
        Timestamp date = resultSet.getTimestamp("date");
        Timestamp time = resultSet.getTimestamp("time");
        Appointment appointment = new Appointment(userRepository.findOne(userId), patientRepository.findOne(patientId), donationCentreRepository.findOne(donationCentreId), date.toLocalDateTime().toLocalDate(), time.toLocalDateTime().toLocalTime());
        appointment.setID(appointmentId);
        return appointment;
    }
}
