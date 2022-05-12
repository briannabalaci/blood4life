package controller;

import domain.User;
import domain.enums.BloodType;
import domain.enums.Gender;
import domain.enums.Rh;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import service.ServiceInterface;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShowUsersController implements Initializable {
    public GridPane usersGridPane;
    private ServiceInterface service;

    public void setService(ServiceInterface service) {
        this.service = service;
        getUsers();
    }

    private void getUsers() {
//        List<User> users = service.findAllUsers();
        List<User> users = new ArrayList<>();
        users.add(new User("dwd", "csda", BloodType.B, Rh.Positive, "bcijes", 24, 23.5, LocalDate.now(), Gender.Female, "knceq"));
        users.add(new User("vrsdvrs", "csda", BloodType.B, Rh.Positive, "bcijes", 24, 23.5, LocalDate.now(), Gender.Female, "knceq"));
        users.add(new User("dvsdfrvwd", "csda", BloodType.B, Rh.Positive, "bcijes", 24, 23.5, LocalDate.now(), Gender.Female, "knceq"));
        users.add(new User("dv sdfrvwd", "csda", BloodType.B, Rh.Positive, "bcijes", 24, 23.5, LocalDate.now(), Gender.Female, "knceq"));

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../cellUser-view.fxml"));
            try {
                Pane view = fxmlLoader.load();
                CellUserController cellUserController = fxmlLoader.getController();
                cellUserController.setUser(user);
                usersGridPane.add(view, 2 * (i / 2), i % 2);
            } catch (IOException ioException) {
                System.out.println(ioException.getMessage());
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
