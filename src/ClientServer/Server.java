
package ClientServer;

import java.io.*;
import java.net.*;

public class Server extends javax.swing.JFrame {

    /**
     * Creates new form ServerGUI
     */
    public Server() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        serverChatWindow = new javax.swing.JTextArea();
        messageBox = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server");
        setResizable(false);

        serverChatWindow.setEditable(false);
        serverChatWindow.setColumns(20);
        serverChatWindow.setRows(5);
        jScrollPane1.setViewportView(serverChatWindow);

        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(messageBox, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 13, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(messageBox)
                    .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                sendMessage(messageBox.getText());;
                messageBox.setText("");
            }
        });
    }//GEN-LAST:event_sendButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField messageBox;
    private javax.swing.JButton sendButton;
    private javax.swing.JTextArea serverChatWindow;
    // End of variables declaration//GEN-END:variables
    public static final int PORT = 1234;
    private ServerSocket server;
    private Socket client;
    private ObjectInputStream clientInput;
    private ObjectOutputStream clientOutput;

    public void startServer() {

        try {
            server = new ServerSocket(PORT);

            while (true) {
                createConnection();
                chat(); 
            }

        } catch (IOException ex) {
            //printToGUI("Client ended the connection");
        } finally {
            closeConnection();
        }
    }

    public void createConnection() throws IOException {
        messageBox.setEditable(false);
        printToGUI("Waiting for a client to connect...");
        client = server.accept();
        printToGUI("Now connected to " + client.getInetAddress().getHostName());
        messageBox.setEditable(true);
        clientInput = new ObjectInputStream(client.getInputStream());
        clientOutput = new ObjectOutputStream(client.getOutputStream());
        clientOutput.flush();
    }

    public void chat() throws IOException {
        String message = "";
        while (!message.equalsIgnoreCase("Client: exit")) {
            try {
                message = (String) clientInput.readObject();
                printToGUI(message);
            } catch (ClassNotFoundException ex) {
                System.out.println("Error receiving client message");
            }
        }
    }

    public void closeConnection() {
        try {
            clientInput.close();
            clientOutput.close();
            client.close();
            printToGUI("\nConnection has ended");
        } catch (IOException ex) {
            System.out.println("Error closing connection");
        }
    }

    private void sendMessage(String message) {
        try {
            message = "Server: " + message;
            clientOutput.writeObject(message);
            clientOutput.flush();
            printToGUI(message);
        } catch (IOException ex) {
            printToGUI("Error sending message, connection is not established");
        }
    }
    
    private void printToGUI(String message) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                serverChatWindow.append("\n" + message);
            }
        });
        
    }
}
