package protocol;

import domain.Appointment;
import domain.DonationCentre;
import domain.Patient;
import domain.User;
import domain.enums.BloodType;
import domain.enums.Gender;
import domain.enums.Rh;
import domain.enums.Severity;
import exception.ServerException;
import service.ServiceInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServiceProxy implements ServiceInterface {
    private final String host;
    private final int port;
    private ClientWorker client;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;
    private final BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public ServiceProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<>();
    }

    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRequest(Request request) throws ServerException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new ServerException("Error sending object " + e);
        }
    }

    private Response readResponse() throws ServerException {
        Response response = null;
        try {
            response = qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() throws ServerException {
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }


    private void handleUpdate(UpdateResponse update) {

    }

    @Override
    public void loginAdmin(String username, String password) {

    }

    @Override
    public void addPatient(String cnp, String firstName, String lastName, LocalDate birthday, BloodType bloodType, Rh rh, Severity severity, int bloodQuantityNeeded) {

    }

    @Override
    public void addDonationCentre(String county, String city, String street, int number, String name, int maximumCapacity, LocalTime openHour, LocalTime closeHour) {

    }

    @Override
    public User loginUser(String username, String cnp) {
        return null;
    }

    @Override
    public void addUser(String firstName, String lastName, String email, String cnp, LocalDate birthdate, Gender gender, BloodType bloodType, Rh rh, Double weight, Integer height) {

    }

    @Override
    public List<DonationCentre> findAllDonationCentres() {
        return null;
    }

    @Override
    public List<Patient> findAllPatients() {
        return null;
    }

    @Override
    public List<Patient> findAllCompatiblePatients(BloodType type, Rh rh) {
        return null;
    }

    @Override
    public void addAppointment(User user, Patient patient, DonationCentre centre, Date date, Time time) {

    }

    @Override
    public List<Appointment> findAllAppointments() {
        return null;
    }

    @Override
    public List<User> findAllUsers() {
        return null;
    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    Object response = input.readObject();
                    System.out.println("response received " + response);
                    if (response instanceof UpdateResponse) {
                        handleUpdate((UpdateResponse) response);
                    } else {
                        try {
                            qresponses.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }
}
