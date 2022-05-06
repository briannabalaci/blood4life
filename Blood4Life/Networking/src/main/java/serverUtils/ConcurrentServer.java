package serverUtils;

import protocol.ClientWorker;
import service.ServiceInterface;

import java.net.Socket;

public class ConcurrentServer extends AbstractConcurrentServer {
    private ServiceInterface server;

    public ConcurrentServer(Integer port, ServiceInterface server) {
        super(port);
        this.server = server;
        System.out.println("Constructor ConcurrentServer(rpc)");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ClientWorker worker = new ClientWorker(server, client);
        Thread thread = new Thread(worker);
        return thread;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}
