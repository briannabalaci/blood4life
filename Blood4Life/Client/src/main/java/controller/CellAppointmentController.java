package controller;

import domain.Appointment;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CellAppointmentController implements Initializable {
    public Label appointmentLabel;

    public void setAppointment(Appointment appointment) {
        appointmentLabel.setText("Patient's CNP: " + appointment.getPatient().getCnp()
                + "\nUser's CNP: " + appointment.getUser().getCnp()
                + "\nDonation centre's name: " + appointment.getDonationCentre().getName()
                + "\nDate: " + appointment.getDate()
                + "\nTime: " + appointment.getTime());
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
