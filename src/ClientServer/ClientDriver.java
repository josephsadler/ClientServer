package ClientServer;

public class ClientDriver {

    public static void main(String[] args) {
        Client client = new Client();
        client.setVisible(true);

        client.startClient();
    }
}
