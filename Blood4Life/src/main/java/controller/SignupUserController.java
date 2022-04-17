package controller;

import domain.enums.BloodType;
import domain.enums.Rh;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.Service;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;

public class SignupUserController implements Initializable {

    public ComboBox genderComboBox;
    public ComboBox bloodTypeComboBox;
    public ComboBox rhComboBox;
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
        ObservableList<String> genders = FXCollections.observableArrayList();
        genders.add("Female");
        genders.add("Male");
        genders.add("Prefer not to say");
        genderComboBox.setItems(genders);
    }

    public void onSignUpUserButtonClick(ActionEvent actionEvent) {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        String cnp = cnpTextField.getText();
        BloodType bloodType = (BloodType) bloodTypeComboBox.getValue();
        Rh rh = (Rh) rhComboBox.getValue();
        String gender = (String) genderComboBox.getValue();
        Double weight = Double.valueOf(weightTextField.getText());
        Integer height = Integer.valueOf(heightTextField.getText());
        LocalDate birthdate = birthDatePicker.getValue();

        service.addUser(firstName, lastName, email, cnp, birthdate, gender, bloodType, rh, weight, height);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Blood4Life");
        alert.setHeaderText("You signed up successfully!");
        alert.showAndWait();

        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void openLoginWindow(){

    }
}
