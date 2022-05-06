package validator;

import java.time.LocalDate;

public interface UserValidatorInterface {
    boolean validateName(String name);
    boolean validateEmail(String email);
    boolean validateCNP(String cnp);
    boolean validateHeight(Integer height);
    boolean validateWeight(Double weight);
    boolean validateBirthDateCNP(LocalDate date, String cnp);
}
