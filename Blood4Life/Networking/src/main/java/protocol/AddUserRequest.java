package protocol;

import domain.User;

public class AddUserRequest implements Request {
    private final User user;

    public AddUserRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
