package controller;

import domain.DonationCentre;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CellDonationCentreController implements Initializable {
    public Label donationCentreLabel;

    public void setDonationCentre(DonationCentre donationCentre) {
        donationCentreLabel.setText("Name: " + donationCentre.getName()
                + "\nOpens at: " + donationCentre.getOpenHour()
                + "\nCloses at: " + donationCentre.getCloseHour()
                + "\nMaximum capacity/hour: " + donationCentre.getMaximumCapacity()
                + "\nCounty: " + donationCentre.getAddress().getCounty()
                + "\nCity: " + donationCentre.getAddress().getLocality()
                + "\nStreet: " + donationCentre.getAddress().getStreet()
                + "\nNumber: " + donationCentre.getAddress().getNumber());
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
