package controller;

import domain.DonationCentre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.Service;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class ShowDonationCentresController implements Initializable {
    public TableView<DonationCentre> donationCentresTableView;
    public TableColumn<DonationCentre, String> nameTableColumn;
    public TableColumn<DonationCentre, Integer> maximumCapacityTableColumn;
    public TableColumn<DonationCentre, LocalTime> openHourTableColumn;
    public TableColumn<DonationCentre, LocalTime> closeHourTableColumn;
    public TableColumn<DonationCentre, String> countyTableColumn;
    public TableColumn<DonationCentre, String> cityTableColumn;
    public TableColumn<DonationCentre, String> streetTableColumn;
    public TableColumn<DonationCentre, Integer> numberTableColumn;

    private Service service;
    private final ObservableList<DonationCentre> donationCentres = FXCollections.observableArrayList();

    public void setService(Service service) {
        this.service = service;
        getDonationCentres();
    }

    private void getDonationCentres() {
        donationCentres.addAll(service.findAllDonationCentres());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        maximumCapacityTableColumn.setCellValueFactory(new PropertyValueFactory<>("maximumCapacity"));
        openHourTableColumn.setCellValueFactory(new PropertyValueFactory<>("openHour"));
        closeHourTableColumn.setCellValueFactory(new PropertyValueFactory<>("closeHour"));
        countyTableColumn.setCellValueFactory(new PropertyValueFactory<>("county"));
        cityTableColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        streetTableColumn.setCellValueFactory(new PropertyValueFactory<>("street"));
        numberTableColumn.setCellValueFactory(new PropertyValueFactory<>("number"));

        donationCentresTableView.setItems(donationCentres);
    }
}
