package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import service.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMainPageController implements Initializable {
    public BorderPane mainPageBorderPane;
    public Button addPatientButton;

    private Service service;

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("addPatient-view.fxml"));
        AddPatientController addPatientController = fxmlLoader.getController();
        addPatientController.setService(service);
        Pane view = null;
        try {
            view = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainPageBorderPane.setCenter(view);
    }

    public void onAddPatientButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("addPatient-view.fxml"));
        AddPatientController addPatientController = fxmlLoader.getController();
        addPatientController.setService(service);
        Pane view = fxmlLoader.load();
        mainPageBorderPane.setCenter(view);
    }
}
