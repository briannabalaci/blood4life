package controller;

import exception.ServerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.Service;

import java.io.IOException;

public class LoginUserController {
    public TextField usernameTextField;
    public TextField cnpTextField;
    public Label messageLabel;

    private Service service;
    private Stage stage;

    public void setService(Service service) {
        this.service = service;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void onUserLoginButtonClick(ActionEvent actionEvent) {
        try {
            String username = usernameTextField.getText();
            String cnp = cnpTextField.getText();
            service.loginUser(username, cnp);
            messageLabel.setText("Logare cu succes!");
        } catch (ServerException exception) {
           messageLabel.setText(exception.getMessage());
        }
    }

    public void onSignUpButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("signupUser-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),660, 500);
        SignupUserController signupUserController = fxmlLoader.getController();
        signupUserController.setController(service, stage);
        stage.setTitle("Blood4Life");
        stage.setScene(scene);
        stage.show();
    }

    public void onLoginAsAdminButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("loginAdmin-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 660, 500);
        LoginAdminController loginAdminController = fxmlLoader.getController();
        loginAdminController.setService(service);
        loginAdminController.setStage(stage);
        stage.setTitle("Blood4Life");
        stage.setScene(scene);
        stage.show();
    }
}
