package ClientServer;

public class ServerDriver {

    public static void main(String[] args) {
        Server server = new Server();
        server.setVisible(true);
        
        server.startServer();
    }

}
