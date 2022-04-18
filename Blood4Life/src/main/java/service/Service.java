package service;

import domain.*;
import domain.enums.BloodType;
import domain.enums.Gender;
import domain.enums.Severity;
import domain.enums.Rh;
import exception.ServerException;
import repository.abstractRepo.*;
import validator.DonationCentreValidator;
import validator.PatientValidator;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    public void loginAdmin(String username, String password) {
        Admin admin = adminRepository.findOne(username);
        if(admin == null)
            throw new ServerException("Invalid username!");
        if(!admin.getPassword().equals(password))
            throw new ServerException("Incorrect password!");
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

    public void loginUser(String username, String cnp) {
        User user = userRepository.findUserByEmail(username);
        if(user == null)
            throw new ServerException("Invalid email!");
        if(!user.getCnp().equals(cnp))
            throw new ServerException("Incorrect password!");
    }

    public void addUser(String firstName, String lastName, String email, String cnp, LocalDate birthdate, Gender gender, BloodType bloodType, Rh rh, Double weight, Integer height){
        User user = new User(firstName, lastName, bloodType, rh, email, height, weight, birthdate, gender, cnp);
        userRepository.save(user);
    }

    public List<DonationCentre> findAllDonationCentres() {
        return donationCentreRepository.findAll();
    }
    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }
}


