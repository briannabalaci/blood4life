package controller;

import domain.enums.BloodType;
import domain.enums.Gender;
import domain.enums.Rh;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.Service;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;

public class SignupUserController implements Initializable {

    public ComboBox<Gender> genderComboBox;
    public ComboBox<BloodType> bloodTypeComboBox;
    public ComboBox<Rh> rhComboBox;
    public TextField cnpTextField;
    public TextField emailTextField;
    public DatePicker birthDatePicker;
    public TextField weightTextField;
    public TextField heightTextField;
    public TextField lastNameTextField;
    public TextField firstNameTextField;

    private Service service;
    private Stage root;

    public void setController(Service service, Stage stage){
        this.service = service;
        this.root = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBloodTypes();
        setRhs();
        setGender();
    }

    private void setBloodTypes() {
        ObservableList<BloodType> bloodTypes = FXCollections.observableArrayList();
        bloodTypes.addAll(Arrays.asList(BloodType.values()));
        bloodTypeComboBox.setItems(bloodTypes);
    }

    private void setRhs() {
        ObservableList<Rh> rhs = FXCollections.observableArrayList();
        rhs.addAll(Arrays.asList(Rh.values()));
        rhComboBox.setItems(rhs);
    }

    private void setGender() {
        ObservableList<Gender> genders = FXCollections.observableArrayList();
        genders.addAll(Arrays.asList(Gender.values()));
        genderComboBox.setItems(genders);
    }

    public void onSignUpUserButtonClick(ActionEvent actionEvent) throws IOException {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        String cnp = cnpTextField.getText();
        BloodType bloodType = bloodTypeComboBox.getValue();
        Rh rh = rhComboBox.getValue();
        Gender gender = genderComboBox.getValue();
        Double weight = Double.valueOf(weightTextField.getText());
        Integer height = Integer.valueOf(heightTextField.getText());
        LocalDate birthdate = birthDatePicker.getValue();

        service.addUser(firstName, lastName, email, cnp, birthdate, gender, bloodType, rh, weight, height);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("loginUser-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 660, 500);
        LoginUserController loginUserController = fxmlLoader.getController();
        loginUserController.setService(service);
        loginUserController.setStage(root);
        root.setTitle("Blood4Life");
        root.setScene(scene);
        root.show();
    }

    public void onBackButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("loginUser-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 660, 500);
        LoginUserController loginUserController = fxmlLoader.getController();
        loginUserController.setService(service);
        loginUserController.setStage(root);
        root.setTitle("Blood4Life");
        root.setScene(scene);
        root.show();
    }
}
