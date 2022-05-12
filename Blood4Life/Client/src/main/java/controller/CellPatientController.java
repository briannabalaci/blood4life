package controller;

import domain.Patient;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CellPatientController implements Initializable {
    public Label patientLabel;

    public void setPatient(Patient patient) {
        patientLabel.setText("Name: " + patient.getFirstName()
                + patient.getLastName()
                + "\nCNP: " + patient.getCnp() + "\nBirthday: "
                + patient.getBirthday() + "\nSeverity: "
                + patient.getSeverity() + "\nBlood type: "
                + patient.getBloodType()
                + "\nRh: " + patient.getRh()
                + "\nBlood needed: " + patient.getBloodQuantityNeeded());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
