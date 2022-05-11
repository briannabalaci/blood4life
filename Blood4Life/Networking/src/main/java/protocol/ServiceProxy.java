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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServiceProxy implements ServiceInterface {
    private final String host;
    private final int port;
    private ClientWorker client;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Socket socket;
    private final BlockingQueue<Response> responses;
    private volatile boolean finished;

    public ServiceProxy(String host, int port) {
        this.host = host;
        this.port = port;
        responses = new LinkedBlockingQueue<>();
    }

    private void closeConnection() {
        finished = true;
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRequest(Request request) throws ServerException {
        try {
            outputStream.writeObject(request);
            outputStream.flush();
        } catch (IOException e) {
            throw new ServerException("Error sending object " + e);
        }
    }

    private Response readResponse() throws ServerException {
        Response response = null;
        try {
            response = responses.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() throws ServerException {
        try {
            socket = new Socket(host, port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.flush();
            inputStream = new ObjectInputStream(socket.getInputStream());
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
        initializeConnection();
        User connectedUser = null;
        List<String> info = new ArrayList<>();
        info.add(username);
        info.add(cnp);
        sendRequest(new LoginUserRequest(info));
        Response response = readResponse();
        if (response instanceof LoginUserOkResponse){
            connectedUser = ((LoginUserOkResponse) response).getUser();
        }
        if (response instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) response;
            closeConnection();
            throw new ServerException(errorResponse.getMessage());
        }
        return connectedUser;
    }

    @Override
    public void addUser(String firstName, String lastName, String email, String cnp, LocalDate birthdate, Gender gender, BloodType bloodType, Rh rh, Double weight, Integer height) {
        sendRequest(new AddUserRequest(firstName, lastName, bloodType, rh, email, height, weight, birthdate, gender, cnp));
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) response;
            throw new ServerException(errorResponse.getMessage());
        }
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
    public List<Patient> findAllCompatiblePatients(BloodType bloodType, Rh rh) {
        sendRequest(new FindCompatiblePatientsRequest(bloodType, rh));
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) response;
            throw new ServerException(errorResponse.getMessage());
        }
        FindCompatiblePatientsResponse findCompatiblePatientsResponse = (FindCompatiblePatientsResponse) response;
        return findCompatiblePatientsResponse.getPatients();
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
                    Object response = inputStream.readObject();
                    System.out.println("Response received " + response);
                    if (response instanceof UpdateResponse) {
                        handleUpdate((UpdateResponse) response);
                    } else {
                        try {
                            responses.put((Response) response);
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
