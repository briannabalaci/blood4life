package controller;

import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.ServiceInterface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserMainPageController implements Initializable {
    public BorderPane mainPageBorderPane;
    public Button showProfileButton;
    public Button createAppointmentButton;
    public Button showAppointmentsButton;
    
    private ServiceInterface service;
    private Stage stage;

    private User currentUser;

    public void setService(ServiceInterface service) {
        this.service = service;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUser(User user) {
        this.currentUser = user;
        try {
            loadProfile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onShowProfileButtonClick(ActionEvent actionEvent) throws IOException {
        loadProfile();
    }

    private void loadProfile() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../userProfilePage-view.fxml"));
        Pane view = fxmlLoader.load();
        UserProfileController userProfileController = fxmlLoader.getController();
        userProfileController.setService(service, currentUser);
        mainPageBorderPane.setCenter(view);
    }

    public void onCreateAppointmentButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("addAppointment-view.fxml"));
        Pane pane = fxmlLoader.load();
        AppointmentController appointmentController = fxmlLoader.getController();
        appointmentController.setUser(currentUser);
        appointmentController.setService(service);
        mainPageBorderPane.setCenter(pane);
    }

    public void onShowAppointmentsButtonClick(ActionEvent actionEvent) {
    }
}
