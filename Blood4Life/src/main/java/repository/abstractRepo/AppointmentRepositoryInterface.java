package repository.abstractRepo;

import domain.Appointment;
import domain.DonationCentre;
import domain.User;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface AppointmentRepositoryInterface extends RepositoryInterface<Long, Appointment> {
    List<Appointment> findAppointmentsByDateTime(Date date, Time time);
    List<Appointment> findAppointmentsByDonationCentre(DonationCentre donationCentre);
    List<Appointment> findAppointmentsByUser(User user);
}
