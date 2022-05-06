package controller;

import domain.Patient;
import domain.enums.BloodType;
import domain.enums.Rh;
import domain.enums.Severity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.ServiceInterface;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ShowPatientsController implements Initializable {
    public TableView<Patient> patientsTableView;
    public TableColumn<Patient,String> firstNameColumn;
    public TableColumn<Patient,String> lastNameColumn;
    public TableColumn<Patient, BloodType> bloodTypeColumn;
    public TableColumn<Patient, Rh> rhColumn;
    public TableColumn<Patient, Severity> severityColumn;
    public TableColumn<Patient, Integer> bloodQuantityColumn;
    public TableColumn<Patient, String> cnpColumn;
    public TableColumn<Patient, LocalDate> birthdayColumn;

    private ServiceInterface service;
    private final ObservableList<Patient> patients = FXCollections.observableArrayList();

    public void setService(ServiceInterface service) {
        this.service = service;
        getPatients();
    }
    private void getPatients() {
        patients.addAll(service.findAllPatients());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        bloodTypeColumn.setCellValueFactory(new PropertyValueFactory<>("bloodType"));
        rhColumn.setCellValueFactory(new PropertyValueFactory<>("rh"));
        severityColumn.setCellValueFactory(new PropertyValueFactory<>("severity"));
        bloodTypeColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        cnpColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        bloodQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("bloodQuantityNeeded"));
        patientsTableView.setItems(patients);
    }
}
