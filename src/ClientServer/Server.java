package ClientServer;

import java.io.*;
import java.net.*;

public class Server {

    public static int PORT = 1234;
    private ServerSocket server;
    private Socket client;
    private ObjectInputStream clientInput;
    private ObjectOutputStream clientOutput;

    public Server() {

        try {
            server = new ServerSocket(PORT);

            while (true) {
                createConnection();
                chat();
                closeConnection();
            }

        } catch (IOException ex) {
            System.out.println("Error initializing server");
            System.exit(1);
        }
    }

    public void createConnection() throws IOException {
        printToGUI("Waiting for a client to connect...");
        client = server.accept();
        printToGUI("Now connected to " + client.getInetAddress().getHostName());
        clientInput = new ObjectInputStream(client.getInputStream());
        clientOutput = new ObjectOutputStream(client.getOutputStream());
        clientOutput.flush();
    }

    public void chat() throws IOException {
        String message = "";
        while (!message.equalsIgnoreCase("Client: exit")) {
            try {
                message = (String) clientInput.readObject();
                printToGUI("\n" + message);
            } catch (ClassNotFoundException ex) {
                System.out.println("Error receiving client message");
            }
        }
    }

    public void closeConnection() throws IOException {
        clientInput.close();
        clientOutput.close();
        client.close();
        printToGUI("\nConnection has ended\n\n");
    }

    private void sendMessage(String message) {
        try {
            clientOutput.writeObject("Server: " + message);
            clientOutput.flush();
            printToGUI("Server: " + message);
        } catch (IOException ex) {
            printToGUI("Error sending message");
        }
    }
    
    private void printToGUI(String message) {
        ServerGUI.serverChatWindow.append(message);
    }
}
