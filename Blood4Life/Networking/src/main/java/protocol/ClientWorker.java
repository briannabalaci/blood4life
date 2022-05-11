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

public class ClientWorker implements Runnable {
    private final ServiceInterface server;
    private final Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public ClientWorker(ServiceInterface server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(connected){
            try {
                Object request = input.readObject();
                Object response = handleRequest((Request)request);
                if (response != null) {
                   sendResponse((Response) response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error " + e);
        }
    }

    private Response handleRequest(Request request) {
        Response response = null;

        if (request instanceof FindCompatiblePatientsRequest) {
            FindCompatiblePatientsRequest findCompatiblePatientsRequest = (FindCompatiblePatientsRequest) request;
            BloodType bloodType = findCompatiblePatientsRequest.getBloodType();
            Rh rh = findCompatiblePatientsRequest.getRh();
            try {
                List<Patient> patients = server.findAllCompatiblePatients(bloodType, rh);
                return new FindCompatiblePatientsResponse(patients);
            } catch (ServerException serverException) {
                return new ErrorResponse(serverException.getMessage());
            }
        }

        if (request instanceof AddUserRequest) {
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
                return new AddUserResponse();
            } catch (ServerException serverException) {
                return new ErrorResponse(serverException.getMessage());
            }
        }

        return response;
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response " + response);
        synchronized (output) {
            output.writeObject(response);
            output.flush();
        }
    }
}
