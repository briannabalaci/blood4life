package controller;

import domain.Appointment;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import service.Service;

import java.net.URL;
import java.sql.Time;
import java.util.Date;
import java.util.ResourceBundle;

public class ShowAppointmentsController implements Initializable {
    public TableView<Appointment> appointmentsTableView;
    public TableColumn<Appointment, String> donationCentreNameTableColumn;
    public TableColumn<Appointment, String> patientNameTableColumn;
    public TableColumn<Appointment, String> userNameTableColumn;
    public TableColumn<Appointment, Date> dateTableColumn;
    public TableColumn<Appointment, Time> hourTableColumn;

    private Service service;
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    public void setService(Service service) {
        this.service = service;
        getAppointments();
    }

    private void getAppointments() {
        appointments.addAll(service.findAllAppointments());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        donationCentreNameTableColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getDonationCentre().getName()));
        patientNameTableColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getPatient().getFirstName() + " " + value.getValue().getPatient().getLastName()));
        userNameTableColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getUser().getFirstName() + " " + value.getValue().getUser().getLastName()));
        dateTableColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getDate()));
        hourTableColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getTime()));

        appointmentsTableView.setItems(appointments);
    }
}
