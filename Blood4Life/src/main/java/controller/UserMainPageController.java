package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import service.Service;

import java.net.URL;
import java.util.ResourceBundle;

public class UserMainPageController implements Initializable {
    public Button showProfileButton;
    public Button createAppointmentButton;
    public Button showAppointmentsButton;
    
    private Service service;
    private Stage stage;

    public void setService(Service service) {
        this.service = service;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void onShowProfileButtonClick(ActionEvent actionEvent) {
    }

    public void onCreateAppointmentButtonClick(ActionEvent actionEvent) {
    }

    public void onShowAppointmentsButtonClick(ActionEvent actionEvent) {
    }
}
