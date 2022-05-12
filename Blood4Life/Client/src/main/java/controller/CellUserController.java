package controller;

import domain.User;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CellUserController implements Initializable {
    public Label userLabel;

    public void setUser(User user) {
        userLabel.setText("Name: " + user.getFirstName() + " " + user.getLastName()
                + "\nCNP: " + user.getCnp()
                + "\nBirthday: " + user.getBirthDate()
                + "\nEmail: " + user.getEmail()
                + "\nGender: " + user.getGender()
                + "\nBlood type: " + user.getBloodType()
                + "\nRh: " + user.getRh()
                + "\nHeight: " + user.getHeight()
                + "\nWeight: " + user.getWeight()
                + "\nPoints: " + user.getPoints());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
