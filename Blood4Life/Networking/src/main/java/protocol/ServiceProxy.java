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
import java.util.logging.Logger;

public class ServiceProxy implements ServiceInterface {
    private final String host;
    private final int port;
    private ClientWorker client;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Socket socket;
    private final BlockingQueue<Response> responses;
    private volatile boolean finished;
    private final Logger logger = Logger.getLogger("logging.txt");

    public ServiceProxy(String host, int port) {
        this.host = host;
        this.port = port;
        responses = new LinkedBlockingQueue<>();
        logger.info("Initializing ServiceProxy");
    }

    private void closeConnection() {
        finished = true;
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
            client = null;
            logger.info("Closing connection in ServiceProxy -> closeConnection");
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
            logger.severe("Exiting ServiceProxy -> closeConnection with IOException");
            System.exit(1);
        }
    }

    private void sendRequest(Request request) throws ServerException {
        try {
            outputStream.writeObject(request);
            outputStream.flush();
        } catch (IOException ioException) {
            logger.severe("Exiting ServiceProxy -> sendRequest with IOException");
            throw new ServerException("Error while sending object " + ioException.getMessage());
        }
    }

    private Response readResponse() throws ServerException {
        Response response;
        try {
            response = responses.take();
            logger.info("Reading response in ServiceProxy -> readResponse");
        } catch (InterruptedException interruptedException) {
            logger.severe("Exiting ServiceProxy -> sendRequest with InterruptedException");
            throw new ServerException("Error while reading response " + interruptedException.getMessage());
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
            logger.info("Initializing connection in ServiceProxy -> initializeConnection");
            startReader();
        } catch (IOException ioException) {
            logger.severe("Exiting ServiceProxy -> initializeConnection with IOException");
            System.exit(1);
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
        logger.info("Logging in admin in ServiceProxy -> loginAdmin");
    }

    @Override
    public void addPatient(String cnp, String firstName, String lastName, LocalDate birthday, BloodType bloodType, Rh rh, Severity severity, int bloodQuantityNeeded) {
        logger.info("Adding patient in ServiceProxy -> addPatient");
    }

    @Override
    public void addDonationCentre(String county, String city, String street, int number, String name, int maximumCapacity, LocalTime openHour, LocalTime closeHour) {
        logger.info("Adding donation centre in ServiceProxy -> addDonationCentre");
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
        if (response instanceof LoginUserOkResponse)
            connectedUser = ((LoginUserOkResponse) response).getUser();
        if (response instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) response;
            closeConnection();
            throw new ServerException(errorResponse.getMessage());
        }
        logger.info("Logging in user in ServiceProxy -> loginUser");
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
        logger.info("Adding user in ServiceProxy -> addUser");
    }

    @Override
    public List<DonationCentre> findAllDonationCentres() {
        logger.info("Finding donation centres in ServiceProxy -> findAllDonationCentres");
        return null;
    }

    @Override
    public List<Patient> findAllPatients() {
        logger.info("Finding patients in ServiceProxy -> findAllPatients");
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
        logger.info("Finding compatible patients in ServiceProxy -> findAllCompatiblePatients");
        return findCompatiblePatientsResponse.getPatients();
    }

    @Override
    public void addAppointment(User user, Patient patient, DonationCentre centre, Date date, Time time) {
        logger.info("Adding appointment in ServiceProxy -> addAppointment");
    }

    @Override
    public List<Appointment> findAllAppointments() {
        logger.info("Finding appointments in ServiceProxy -> findAllAppointments");
        return null;
    }

    @Override
    public List<User> findAllUsers() {
        logger.info("Finding users in ServiceProxy -> findAllUsers");
        return null;
    }

    @Override
    public List<Appointment> findPreviousAppointmentsByUser(User user) {
        sendRequest(new FindPreviousAppointmentsByUserRequest(user));
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) response;
            throw new ServerException(errorResponse.getMessage());
        }
        FindPreviousAppointmentsByUserResponse findPreviousAppointmentsByUserResponse = (FindPreviousAppointmentsByUserResponse) response;
        logger.info("Finding previous appointments of a user in ServiceProxy -> findPreviousAppointmentsByUser");
        return findPreviousAppointmentsByUserResponse.getPatients();
    }

    @Override
    public List<Appointment> findFutureAppointmentsByUser(User user) {
        return null;
    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    Object response = inputStream.readObject();
                    logger.info("Receiving response " + response + " in ServiceProxy -> run");
                    if (response instanceof UpdateResponse) {
                        handleUpdate((UpdateResponse) response);
                        logger.info("Handling update in ServiceProxy -> run");
                    } else {
                        try {
                            responses.put((Response) response);
                            logger.info("Adding new response " + response);
                        } catch (InterruptedException interruptedException) {
                            logger.severe("Exiting ServiceProxy with InterruptedException");
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    logger.severe("Exiting ServiceProxy with IOException or ClassNotFoundException");
                }
            }
        }
    }
}
