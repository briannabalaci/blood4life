package repository.abstractRepo;

import domain.User;
import domain.enums.BloodType;
import domain.enums.Rh;
import repository.abstractRepo.RepositoryInterface;

import java.util.List;

public interface UserRepositoryInterface extends RepositoryInterface<Long, User> {
    User findUserByCNP(String cnp);
    List<User> findUsersByBloodTypeAndRh(BloodType bloodType, Rh rh);
}
