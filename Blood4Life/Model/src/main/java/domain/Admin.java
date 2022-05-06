package domain;

import java.io.Serializable;

public class Admin implements Entity<String>, Serializable {

    private String username;
    private String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getID() {
        return username;
    }

    @Override
    public void setID(String s) {
        this.username = s;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
