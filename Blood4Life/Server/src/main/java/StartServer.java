import exception.ServerException;
import repository.*;
import server.Service;
import serverUtils.AbstractServer;
import serverUtils.ConcurrentServer;
import validator.AddressValidator;
import validator.DonationCentreValidator;
import validator.PatientValidator;

import java.io.IOException;
import java.util.Properties;

public class StartServer {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(StartServer.class.getResourceAsStream("server.properties"));
            System.out.println("Properties set.");
            properties.list(System.out);
        } catch (IOException e) {
            System.out.println("Cannot find server.properties " + e);
            return;
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

        try {
            int defaultPort = 55555;
            int serverPort = defaultPort;
            try {
                serverPort = Integer.parseInt(properties.getProperty("server.port"));
            } catch (NumberFormatException nef) {
                System.err.println("Wrong  Port Number " + nef.getMessage());
                System.err.println("Using default port " + defaultPort);
            }
            System.out.println("Starting server on port: " + serverPort);

            AbstractServer server = new ConcurrentServer(serverPort, service);

            try {
                server.start();
            } catch (ServerException e) {
                System.err.println("Error starting the server" + e.getMessage());
            } finally {
                try {
                    server.stop();
                } catch (ServerException e) {
                    System.err.println("Error stopping server " + e.getMessage());
                }
            }
        }
        catch (Exception e) {
            System.err.println("Exception " + e);
            e.printStackTrace();
        }
    }
}
