/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientServer;

/**
 *
 * @author tsad2
 */
public class ClientDriver {
    
    
    public static void main(String[] args) {
        Client client = new Client();
    client.setVisible(true);
    
    client.startClient();
    }
}
