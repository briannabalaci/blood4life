package controller;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMainPageController implements Initializable {
    public BorderPane mainPageBorderPane;
    public Button addPatientButton;

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
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("../addPatient-view.fxml"));
//        AddPatientController addPatientController = fxmlLoader.getController();
//        addPatientController.setService(service);
//        addPatientController.setStage(stage);
//        Pane view = null;
//        try {
//            view = fxmlLoader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        mainPageBorderPane.setCenter(view);
    }

    public void onAddPatientButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../addPatient-view.fxml"));
        Pane view = fxmlLoader.load();
        AddPatientController addPatientController = fxmlLoader.getController();
        addPatientController.setService(service);
        addPatientController.setStage(stage);
        mainPageBorderPane.setCenter(view);
    }
}
