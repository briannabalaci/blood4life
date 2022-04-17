package repository.abstractRepo;

import domain.Patient;
import domain.enums.BloodType;
import domain.enums.Rh;
import domain.enums.Severity;

public interface PatientRepositoryInterface extends RepositoryInterface<Long, Patient> {
    Patient findPatientsByQuantity(Integer quantity);
    Patient findPatientsBySeverity(Severity severity);
    Patient findPatientsByBloodTypeAndRh(BloodType bloodType, Rh rh);
}
