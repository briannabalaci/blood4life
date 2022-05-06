package controller;

import domain.User;
import domain.enums.BloodType;
import domain.enums.Gender;
import domain.enums.Rh;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import service.ServiceInterface;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ShowUsersController implements Initializable {

    public AnchorPane parentBorderPane;
    public TableView<User> usersTableView;
    public TableColumn<User, String> firstNameColumn;
    public TableColumn<User, String> lastNameColumn;
    public TableColumn<User, String> emailColumn;
    public TableColumn<User, String> cnpColumn;
    public TableColumn<User, LocalDate> birthdateColumn;
    public TableColumn<User, Gender> genderColumn;
    public TableColumn<User, BloodType> bloodTypeColumn;
    public TableColumn<User, Double> weightColumn;
    public TableColumn<User, Rh> rhColumn;
    public TableColumn<User, Integer> heightColumn;
    private ServiceInterface service;
    private final ObservableList<User> users = FXCollections.observableArrayList();
    private int pageNumber;

    public void setService(ServiceInterface service) {
        this.service = service;
        getUsers();
        pageNumber = service.findAllUsers().size();
    }

    private void getUsers() {
        users.addAll(service.findAllUsers());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        bloodTypeColumn.setCellValueFactory(new PropertyValueFactory<>("bloodType"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        rhColumn.setCellValueFactory(new PropertyValueFactory<>("Rh"));
        cnpColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        birthdateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        usersTableView.setItems(users);
    }

}
