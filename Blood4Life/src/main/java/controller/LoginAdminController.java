package controller;

import exception.ServerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.Service;

import java.io.IOException;

public class LoginAdminController {
    public TextField usernameTextField;
    public PasswordField passwordTextField;
    public Label errorLabel;

    private Service service;

    public void setService(Service service) {
        this.service = service;
    }


    public void onAdminLoginButtonClick(ActionEvent actionEvent) {
        try {
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();
            service.loginAdmin(username, password);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("adminMainPage-view.fxml"));
            Parent parent = fxmlLoader.load();
            AdminMainPageController adminMainPageController = fxmlLoader.getController();
            adminMainPageController.setService(service);
            Stage stage = (Stage) usernameTextField.getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
        } catch (IOException | ServerException exception) {
            errorLabel.setText(exception.getMessage());
        }
    }
}
