package repository;

import domain.Appointment;
import domain.DonationCentre;
import domain.User;
import repository.abstractRepo.AppointmentRepositoryInterface;
import repository.abstractRepo.DonationCentreRepositoryInterface;
import repository.abstractRepo.PatientRepositoryInterface;
import repository.abstractRepo.UserRepositoryInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppointmentRepository implements AppointmentRepositoryInterface {
    private final String databaseURL;
    private final String databaseUsername;
    private final String databasePassword;
    private final UserRepositoryInterface userRepository;
    private final PatientRepositoryInterface patientRepository;
    private final DonationCentreRepositoryInterface donationCentreRepository;

    public AppointmentRepository(String databaseURL, String databaseUsername, String databasePassword, UserRepositoryInterface userRepository, PatientRepositoryInterface patientRepository, DonationCentreRepositoryInterface donationCentreRepository) {
        this.databaseURL = databaseURL;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.donationCentreRepository = donationCentreRepository;
    }

    @Override
    public List<Appointment> findAppointmentsByDateTime(Date date, Time time) {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Appointments\" WHERE date = ? AND time = ?");
            preparedStatement.setDate(1, (java.sql.Date) date);
            preparedStatement.setTime(2, time);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (!resultSet.next())
                appointments.add(getAppointmentFromDatabase(resultSet));
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return appointments;
    }

    @Override
    public List<Appointment> findAppointmentsByDonationCentre(DonationCentre donationCentre) {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Appointments\" WHERE donationCentreId = ?");
            preparedStatement.setLong(1, donationCentre.getID());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (!resultSet.next())
                appointments.add(getAppointmentFromDatabase(resultSet));
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return appointments;
    }

    @Override
    public List<Appointment> findAppointmentsByUser(User user) {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Appointments\" WHERE \"userId\" = ?");
            preparedStatement.setLong(1, user.getID());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                appointments.add(getAppointmentFromDatabase(resultSet));
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return appointments;
    }

    @Override
    public Integer findNumberAppointmentsAtCenterDate(DonationCentre donationCentre, Date date) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM public.\"Appointments\" WHERE \"donationCentreId\" = ? and date = ?");
            preparedStatement.setLong(1, donationCentre.getID());
            preparedStatement.setDate(2, (java.sql.Date) date);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                return resultSet.getInt(1);
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return null;
    }

    @Override
    public Integer findNumberAppointmentsAtCenterDateTime(DonationCentre donationCentre, Date date, Time time) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM public.\"Appointments\" WHERE \"donationCentreId\" = ? and date = ? and time = ?");
            preparedStatement.setLong(1, donationCentre.getID());
            preparedStatement.setDate(2, (java.sql.Date) date);
            preparedStatement.setTime(3, time);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                return resultSet.getInt(1);
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return null;
    }

    @Override
    public Appointment findOne(Long id) {
        Appointment appointment = null;
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Appointments\" WHERE id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                appointment = getAppointmentFromDatabase(resultSet);
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return appointment;
    }

    @Override
    public List<Appointment> findAll() {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Appointments\"");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (!resultSet.next())
                appointments.add(getAppointmentFromDatabase(resultSet));
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return appointments;
    }

    @Override
    public void save(Appointment appointment) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO public.\"Appointments\"(\"userId\", \"patientId\", \"donationCentreId\", date, time) VALUES (?, ?, ?, ?, ?)");
            setQueryStatement(appointment, preparedStatement);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM public.\"Appointments\" WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void update(Appointment appointment) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE public.\"Appointments\" SET userId = ?, pacientId = ?, donationCentreId = ?, date = ?, time = ? WHERE id = ?)");
            setQueryStatement(appointment, preparedStatement);
            preparedStatement.setLong(6, appointment.getID());
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
    }

    private void setQueryStatement(Appointment appointment, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, appointment.getUser().getID());
        preparedStatement.setLong(2, appointment.getPatient().getID());
        preparedStatement.setLong(3, appointment.getDonationCentre().getID());
        preparedStatement.setDate(4, (java.sql.Date) appointment.getDate());
        preparedStatement.setTime(5, appointment.getTime());
    }

    private Appointment getAppointmentFromDatabase(ResultSet resultSet) throws SQLException {
        long appointmentId = resultSet.getLong("id");
        long userId = resultSet.getLong("userId");
        long pacientId = resultSet.getLong("patientId");
        long donationCentreId = resultSet.getLong("donationCentreId");
        Date date = resultSet.getDate("date");
        Time time = resultSet.getTime("time");
        Appointment appointment = new Appointment(userRepository.findOne(userId), patientRepository.findOne(pacientId), donationCentreRepository.findOne(donationCentreId), date, time);
        appointment.setID(appointmentId);
        return appointment;
    }
}
