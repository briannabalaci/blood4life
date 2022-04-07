package validator;

import domain.Patient;
import exception.ValidationException;

public class PatientValidator {
    private String errors;

    public PatientValidator() {
        errors = "";
    }

    private void validateCNP(String cnp) {
        if (cnp.isEmpty()) {
            errors = errors.concat("\nCNP must not be empty");
            return;
        }
        if (cnp.length() != 13)
            errors = errors.concat("\nCNP must have 13 digits");
        if (!cnp.matches("[0-9]+"))
            errors = errors.concat("\nCNP must contain digits only");
    }

    private void validateNames(String name, boolean firstOrLast) {
        if (name.isEmpty()) {
            if (firstOrLast)
                errors = errors.concat("\nFirst name must not be empty");
            else
                errors = errors.concat("\nLast name must not be empty");
            return;
        }
        if (name.length() == 1) {
            if (firstOrLast)
                errors = errors.concat("\nFirst name must have more than 1 letter");
            else
                errors = errors.concat("\nLast name must have more than 1 letter");
            return;
        }
        if (name.charAt(0) < 'A' || name.charAt(0) > 'Z')
            if (firstOrLast)
                errors = errors.concat("\nFirst name must begin with capital letter");
            else
                errors = errors.concat("\nLast name must begin with capital letter");
        if (!name.substring(1).matches("[a-z]+"))
            if (firstOrLast)
                errors = errors.concat("\nFirst name must contain letters only");
            else
                errors = errors.concat("\nLast name must contain letters only");
    }

    public void validatePatient(Patient patient) {
        errors = "";
        validateCNP(patient.getCnp());
        validateNames(patient.getFirstName(), true);
        validateNames(patient.getLastName(), false);
        if (!errors.isEmpty())
            throw new ValidationException(errors.substring(1));
    }
}
