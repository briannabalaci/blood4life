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
import validator.PatientValidator;

import java.time.LocalDate;

public class Service {
    private final UserRepositoryInterface userRepository;
    private final AppointmentRepositoryInterface appointmentRepository;
    private final DonationCentreRepositoryInterface donationCentreRepository;
    private final PatientRepository patientRepository;
    private final AdminRepository adminRepository;
    private final PatientValidator patientValidator;

    public Service(UserRepositoryInterface userRepository, AppointmentRepositoryInterface appointmentRepository, DonationCentreRepositoryInterface donationCentreRepository, PatientRepository patientRepository, AdminRepository adminRepository, PatientValidator patientValidator) {
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
        this.donationCentreRepository = donationCentreRepository;
        this.patientRepository = patientRepository;
        this.adminRepository = adminRepository;
        this.patientValidator = patientValidator;
    }

    public void addPatient(String cnp, String firstName, String lastName, LocalDate birthday, BloodType bloodType, Rh rh, Severity severity, int bloodQuantityNeeded) {
        Patient patient = new Patient(cnp, firstName, lastName, birthday, bloodType, rh, severity, bloodQuantityNeeded);
        patientValidator.validatePatient(patient);
        patientRepository.save(patient);
    }
}
