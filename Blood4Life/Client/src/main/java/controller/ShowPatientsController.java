package controller;

import domain.Patient;
import domain.enums.BloodType;
import domain.enums.Rh;
import domain.enums.Severity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import service.ServiceInterface;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShowPatientsController implements Initializable {
    public GridPane patientsGridPane;
    private ServiceInterface service;

    public void setService(ServiceInterface service) {
        this.service = service;
        getPatients();
    }
    private void getPatients() {
//        List<Patient> patients = service.findAllPatients();
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient("scwsc", "cesec", "casece", LocalDate.now(), BloodType.B, Rh.Positive, Severity.Severe, 200));
        patients.add(new Patient("scwsvdesvvc", "cesec", "casece", LocalDate.now(), BloodType.B, Rh.Positive, Severity.Severe, 200));
        patients.add(new Patient("scwedssvsc", "cesec", "casece", LocalDate.now(), BloodType.B, Rh.Positive, Severity.Severe, 200));
        patients.add(new Patient("scwssdvsc", "cesec", "casece", LocalDate.now(), BloodType.B, Rh.Positive, Severity.Severe, 200));

        for (int i = 0; i < patients.size(); i++) {
            Patient patient = patients.get(i);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../cellPatient-view.fxml"));
            try {
                Pane view = fxmlLoader.load();
                CellPatientController cellPatientController = fxmlLoader.getController();
                cellPatientController.setPatient(patient);
                patientsGridPane.add(view, 2 * (i / 2), i % 2);
            } catch (IOException ioException) {
                System.out.println(ioException.getMessage());
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
