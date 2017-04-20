package ClientServer;

public class ClientServerDriver {

    public static void main(String[] args) {
        ServerGUI server = new ServerGUI();
        server.setVisible(true);
        server.startServer();
    }

}
