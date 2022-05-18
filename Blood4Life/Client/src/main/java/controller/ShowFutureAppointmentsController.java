package controller;

import domain.*;
import domain.enums.BloodType;
import domain.enums.Gender;
import domain.enums.Rh;
import domain.enums.Severity;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import service.ServiceInterface;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShowFutureAppointmentsController implements Initializable {
    public GridPane appointmentsGridPane;

    private ServiceInterface service;
    private User loggedUser;

    public void setService(ServiceInterface service, User loggedUser) {
        this.loggedUser = loggedUser;
        this.service = service;
        getAppointments();
    }

    private void getAppointments() {
//        List<Appointment> appointments = service.findAllAppointments();
        List<Appointment> appointments = new ArrayList<>();
        DonationCentre donationCentre = new DonationCentre(new Address("nbch", "bjcbuws", "bjucdbs", 3), "bujcw", 23, LocalTime.now(), LocalTime.now());
        Patient patient = new Patient("scwsc", "cesec", "casece", LocalDate.now(), BloodType.B, Rh.Positive, Severity.Severe, 200);
        User user = new User("dwd", "csda", BloodType.B, Rh.Positive, "bcijes", 24, 23.5, LocalDate.now(), Gender.Female, "knceq");

        appointments.add(new Appointment(user, patient, donationCentre, LocalDate.now(), LocalTime.now()));
        appointments.add(new Appointment(user, patient, donationCentre, LocalDate.now(), LocalTime.now()));
        appointments.add(new Appointment(user, patient, donationCentre, LocalDate.now(), LocalTime.now()));
        appointments.add(new Appointment(user, patient, donationCentre, LocalDate.now(), LocalTime.now()));

        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../cellAppointment-view.fxml"));
            try {
                Pane view = fxmlLoader.load();
                CellAppointmentController cellAppointmentController = fxmlLoader.getController();
                cellAppointmentController.setAppointment(appointment);
                appointmentsGridPane.add(view, 2 * (i / 2), i % 2);
            } catch (IOException ioException) {
                System.out.println(ioException.getMessage());
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}