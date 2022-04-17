package controller;

import exception.ServerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    public void setService(Service service) {
        this.service = service;
    }


    public void onUserLoginButtonClick(ActionEvent actionEvent) {
        try {
            String username = usernameTextField.getText();
            String cnp =cnpTextField.getText();
            service.loginUser(username, cnp);
            messageLabel.setText("Logare cu succes!");
        } catch (ServerException exception) {
           messageLabel.setText(exception.getMessage());
        }
    }
}
