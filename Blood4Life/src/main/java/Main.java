import controller.LoginAdminController;
import controller.LoginUserController;
import controller.ShowPatientsController;
import domain.enums.BloodType;
import domain.enums.Rh;
import domain.enums.Severity;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.*;
import service.Service;
import validator.AddressValidator;
import validator.DonationCentreValidator;
import validator.PatientValidator;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {
    public static void main(String[] args) throws IOException {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("db.properties"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }
        String databaseURL = properties.getProperty("jdbc.URL");
        String databaseUsername = properties.getProperty("jdbc.Username");
        String databasePassword = properties.getProperty("jdbc.Password");
        AddressRepository addressRepository = new AddressRepository(databaseURL, databaseUsername, databasePassword);
        DonationCentreRepository donationCentreRepository = new DonationCentreRepository(databaseURL, databaseUsername, databasePassword, addressRepository);
        UserRepository userRepository = new UserRepository(databaseURL, databaseUsername, databasePassword);
        AdminRepository adminRepository = new AdminRepository(databaseURL, databaseUsername, databasePassword);
        PatientRepository patientRepository = new PatientRepository(databaseURL, databaseUsername, databasePassword);
        AppointmentRepository appointmentRepository = new AppointmentRepository(databaseURL, databaseUsername, databasePassword, userRepository, patientRepository, donationCentreRepository);
        Service service = new Service(userRepository, appointmentRepository, donationCentreRepository, patientRepository, adminRepository, new PatientValidator(), new DonationCentreValidator(new AddressValidator()));

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("loginUser-view.fxml"));
        Parent parent = fxmlLoader.load();
        LoginUserController loginUserController = fxmlLoader.getController();
        loginUserController.setService(service);
        Scene scene = new Scene(parent);
        stage.setTitle("Blood4Life");
        stage.setScene(scene);
        stage.show();
    }
}
