package protocol;

import domain.User;

public class FindPreviousAppointmentsByUserRequest implements Request{
    private final User user;

    public FindPreviousAppointmentsByUserRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
