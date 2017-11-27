/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeelight.bulbs.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Philipp
 */
public class Controller {
    
    private final int BULB_PORT=55443;
    Socket clientSocket;
    private static int id=1;
    
    
    public void toggleBulb (String ip) {
        try {
            this.clientSocket = new Socket (ip,BULB_PORT);
            DataOutputStream outdata = new DataOutputStream (clientSocket.getOutputStream());
            String command = "{\"id\":"+id+",\"method\":\"toggle\",\"params\":[]}\r\n";
            outdata.writeBytes(command);
            id++;
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void startProgram (List<String> myargs) {
        switch (myargs.get(0)) {
            case "t":
                int i = 0;
                while (!myargs.isEmpty()) {
                    this.toggleBulb(myargs.get(i));
                    myargs.remove(i);
                    i++;
                }
                break;
            default:
                 YeeLightBulbsController.printUsage();
                 break;
                
        }
    }
    
}
