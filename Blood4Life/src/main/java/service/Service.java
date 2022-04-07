package service;

import domain.Patient;
import domain.enums.BloodType;
import domain.enums.Severity;
import domain.enums.Rh;
import repository.AdminRepository;
import repository.PatientRepository;
import repository.abstractRepo.AppointmentRepositoryInterface;
import repository.abstractRepo.DonationCentreRepositoryInterface;
import repository.abstractRepo.UserRepositoryInterface;

import java.time.LocalDate;

public class Service {
    private final UserRepositoryInterface userRepository;
    private final AppointmentRepositoryInterface appointmentRepository;
    private final DonationCentreRepositoryInterface donationCentreRepository;
    private final PatientRepository patientDBRepository;
    private final AdminRepository adminDBRepository;

    public Service(UserRepositoryInterface userRepository, AppointmentRepositoryInterface appointmentRepository, DonationCentreRepositoryInterface donationCentreRepository, PatientRepository patientDBRepository, AdminRepository adminDBRepository) {
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
        this.donationCentreRepository = donationCentreRepository;
        this.patientDBRepository = patientDBRepository;
        this.adminDBRepository = adminDBRepository;
    }

    public void addPatient(String cnp, String firstName, String lastName, LocalDate birthday, BloodType bloodType, Rh rh, Severity severity, int bloodQuantityNeeded) {
        Patient patient = new Patient(cnp, firstName, lastName, birthday, bloodType, rh, severity, bloodQuantityNeeded);
        patientDBRepository.save(patient);
    }
}
