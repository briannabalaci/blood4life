package controller;

import domain.Address;
import domain.DonationCentre;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import service.ServiceInterface;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShowDonationCentresController implements Initializable {
    public GridPane donationCentresGridPane;

    private ServiceInterface service;

    public void setService(ServiceInterface service) {
        this.service = service;
        getDonationCentres();
    }

    private void getDonationCentres() {
        //List<DonationCentre> donationCentreList = service.findAllDonationCentres();
        //List<DonationCentre> donationCentres = new ArrayList<>();
        List<DonationCentre> donationCentres = service.findAllDonationCentres();

//        donationCentres.add(new DonationCentre(new Address("nbch", "bjcbuws", "bjucdbs", 3), "bujcw", 23, LocalTime.now(), LocalTime.now()));
//        donationCentres.add(new DonationCentre(new Address("nbch", "bjcbuws", "bjucdbs", 3), "buvfsdvjcw", 23, LocalTime.now(), LocalTime.now()));
//        donationCentres.add(new DonationCentre(new Address("nbch", "bjcbuws", "bjucdbs", 3), "bvdfsv efsrvsrvujcw", 23, LocalTime.now(), LocalTime.now()));
//        donationCentres.add(new DonationCentre(new Address("nbch", "bjcbuws", "bjucdbs", 3), "sfvdfvfsdd", 23, LocalTime.now(), LocalTime.now()));

//        for (DonationCentre donationCentre: donationCentres){
//            donationCentres.add(donationCentre);
//        }

        if (donationCentres.size() != 0) {
            for (int i = 0; i < donationCentres.size(); i++) {
                DonationCentre donationCentre = donationCentres.get(i);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../cellDonationCentre-view.fxml"));
                try {
                    Pane view = fxmlLoader.load();
                    CellDonationCentreController cellDonationCentreController = fxmlLoader.getController();
                    cellDonationCentreController.setDonationCentre(donationCentre);
                    donationCentresGridPane.add(view, 2 * (i / 2), i % 2);
                } catch (IOException ioException) {
                    System.out.println(ioException.getMessage());
                }
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
