package controller;

import domain.enums.BloodType;
import domain.enums.Severity;
import domain.enums.Rh;
import exception.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.Service;

import java.net.URL;
import java.util.ResourceBundle;

public class AddPatientController implements Initializable {
    public TextField cnpTextField;
    public TextField firstNameTextField;
    public TextField lastNameTextField;
    public TextField bloodQuantityNeededTextField;
    public ComboBox<BloodType> bloodTypeComboBox;
    public ComboBox<Rh> rhComboBox;
    public ComboBox<Severity> severityComboBox;
    public DatePicker birthdayDatePicker;
    public Button addPatientButton;
    public TextArea errorsTextArea;

    private Service service;
    private Stage stage;

    public void setService(Service service) {
        this.service = service;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void setBloodTypes() {
        ObservableList<BloodType> bloodTypes = FXCollections.observableArrayList();
        bloodTypes.add(BloodType.O);
        bloodTypes.add(BloodType.A);
        bloodTypes.add(BloodType.B);
        bloodTypes.add(BloodType.AB);
        bloodTypeComboBox.setItems(bloodTypes);
    }

    private void setRhs() {
        ObservableList<Rh> rhs = FXCollections.observableArrayList();
        rhs.add(Rh.Positive);
        rhs.add(Rh.Negative);
        rhs.add(Rh.Neutral);
        rhComboBox.setItems(rhs);
    }

    private void setSeverities() {
        ObservableList<Severity> severities = FXCollections.observableArrayList();
        severities.add(Severity.Minimal);
        severities.add(Severity.Medium);
        severities.add(Severity.Severe);
        severityComboBox.setItems(severities);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBloodTypes();
        setRhs();
        setSeverities();
        errorsTextArea.setVisible(false);
        errorsTextArea.setEditable(false);
    }

    public void onAddPatientButtonClick(ActionEvent actionEvent) {
        try {
            int bloodQuantityNeeded = Integer.parseInt(bloodQuantityNeededTextField.getText());
            try {
                service.addPatient(cnpTextField.getText(), firstNameTextField.getText(), lastNameTextField.getText(),
                        birthdayDatePicker.getValue(), bloodTypeComboBox.getValue(), rhComboBox.getValue(),
                        severityComboBox.getValue(), bloodQuantityNeeded);
            } catch (ValidationException validationException) {
                errorsTextArea.setVisible(true);
                errorsTextArea.setText(validationException.getMessage());
            }
        } catch (NumberFormatException numberFormatException) {
            errorsTextArea.setVisible(true);
            errorsTextArea.setText("Blood quantity must be a number");
        }
    }
}
