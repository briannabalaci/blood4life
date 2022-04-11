package service;

import domain.Address;
import domain.DonationCentre;
import domain.Patient;
import domain.enums.BloodType;
import domain.enums.Severity;
import domain.enums.Rh;
import repository.abstractRepo.*;
import validator.DonationCentreValidator;
import validator.PatientValidator;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class Service {
    private final UserRepositoryInterface userRepository;
    private final AppointmentRepositoryInterface appointmentRepository;
    private final DonationCentreRepositoryInterface donationCentreRepository;
    private final PatientRepositoryInterface patientRepository;
    private final AdminRepositoryInterface adminRepository;
    private final PatientValidator patientValidator;
    private final DonationCentreValidator donationCentreValidator;

    public Service(UserRepositoryInterface userRepository, AppointmentRepositoryInterface appointmentRepository, DonationCentreRepositoryInterface donationCentreRepository, PatientRepositoryInterface patientRepository, AdminRepositoryInterface adminRepository, PatientValidator patientValidator, DonationCentreValidator donationCentreValidator) {
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
        this.donationCentreRepository = donationCentreRepository;
        this.patientRepository = patientRepository;
        this.adminRepository = adminRepository;
        this.patientValidator = patientValidator;
        this.donationCentreValidator = donationCentreValidator;
    }

    public void addPatient(String cnp, String firstName, String lastName, LocalDate birthday, BloodType bloodType, Rh rh, Severity severity, int bloodQuantityNeeded) {
        Patient patient = new Patient(cnp, firstName, lastName, birthday, bloodType, rh, severity, bloodQuantityNeeded);
        patientValidator.validatePatient(patient);
        patientRepository.save(patient);
    }

    public void addDonationCentre(String county, String city, String street, int number, String name, int maximumCapacity, LocalTime openHour, LocalTime closeHour) {
        Address address = new Address(city, county, street, number);
        DonationCentre donationCentre = new DonationCentre(address, name, maximumCapacity, openHour, closeHour);
        donationCentreValidator.validateDonationCentre(donationCentre);
        donationCentreRepository.save(donationCentre);
    }
}
