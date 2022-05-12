package protocol;

import domain.Patient;
import domain.User;
import domain.enums.BloodType;
import domain.enums.Gender;
import domain.enums.Rh;
import exception.ServerException;
import service.ServiceInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class ClientWorker implements Runnable {
    private final ServiceInterface server;
    private final Socket connection;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    private final Logger logger = Logger.getLogger("logging.txt");

    public ClientWorker(ServiceInterface server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
            logger.info("Initializing ClientWorker");
        } catch (IOException ioException) {
            logger.severe("Exiting ClientWorker with IOException");
            System.exit(1);
        }
    }

    public void run() {
        while(connected) {
            try {
                Object request = input.readObject();
                Response response = handleRequest((Request)request);
                logger.info("Handling request " + request + " in ClientWorker -> run");
                if (response != null) {
                   sendResponse(response);
                   logger.info("Sending response " + response + " in ClientWorker -> run");
                }
            } catch (IOException | ClassNotFoundException exception) {
                logger.severe("Exiting ClientWorker -> run with IOException or ClassNotFoundException");
                System.exit(1);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                logger.severe("Exiting ClientWorker -> run with InterruptedException");
                System.exit(1);
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
            logger.info("Closing connection in ClientWorker -> run");
        } catch (IOException ioException) {
            logger.severe("Exiting ClientWorker -> run with IOException");
            System.exit(1);
        }
    }

    private Response handleRequest(Request request) {
        Response response = null;
        if (request instanceof LoginUserRequest) {
            logger.info("Receiving LoginUserRequest in ClientWorker -> handleRequest");
            LoginUserRequest loginUserRequest = (LoginUserRequest)request;
            List<String> userInfo = loginUserRequest.getUserLoginInfo();
            String email = userInfo.get(0);
            String cnp = userInfo.get(1);
            try {
                User connectedUser = server.loginUser(email, cnp);
                logger.info("Sending LoginUserOkResponse in ClientWorker -> handleRequest");
                return new LoginUserOkResponse(connectedUser);
            } catch (ServerException e) {
                connected = false;
                logger.severe("Sending ErrorResponse in ClientWorker -> handleRequest");
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof FindCompatiblePatientsRequest) {
            logger.info("Receiving FindCompatiblePatientsRequest in ClientWorker -> handleRequest");
            FindCompatiblePatientsRequest findCompatiblePatientsRequest = (FindCompatiblePatientsRequest) request;
            BloodType bloodType = findCompatiblePatientsRequest.getBloodType();
            Rh rh = findCompatiblePatientsRequest.getRh();
            try {
                List<Patient> patients = server.findAllCompatiblePatients(bloodType, rh);
                logger.info("Sending FindCompatiblePatientsResponse in ClientWorker -> handleRequest");
                return new FindCompatiblePatientsResponse(patients);
            } catch (ServerException serverException) {
                logger.severe("Sending ErrorResponse in ClientWorker -> handleRequest");
                return new ErrorResponse(serverException.getMessage());
            }
        }

        if (request instanceof AddUserRequest) {
            logger.info("Receiving AddUserRequest in ClientWorker -> handleRequest");
            AddUserRequest addUserRequest = (AddUserRequest) request;
            String firstName = addUserRequest.getFirstName();
            String lastName = addUserRequest.getLastName();
            String cnp = addUserRequest.getCnp();
            String email = addUserRequest.getEmail();
            LocalDate birthday = addUserRequest.getBirthDate();
            int height = addUserRequest.getHeight();
            double weight = addUserRequest.getWeight();
            BloodType bloodType = addUserRequest.getBloodType();
            Rh rh = addUserRequest.getRh();
            Gender gender = addUserRequest.getGender();
            try {
                server.addUser(firstName, lastName, email, cnp, birthday, gender, bloodType, rh, weight, height);
                logger.info("Sending AddUserResponse in ClientWorker -> handleRequest");
                return new AddUserResponse();
            } catch (ServerException serverException) {
                logger.severe("Sending ErrorResponse in ClientWorker -> handleRequest");
                return new ErrorResponse(serverException.getMessage());
            }
        }

        return response;
    }

    private void sendResponse(Response response) throws IOException{
        synchronized (output) {
            output.writeObject(response);
            output.flush();
        }
    }
}
