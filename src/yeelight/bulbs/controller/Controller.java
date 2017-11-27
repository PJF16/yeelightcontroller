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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Philipp
 */
public class Controller {

    private final int BULB_PORT = 55443;
    Socket clientSocket;
    private static int id = 1;

    public void toggleBulb(ArrayList<String> myargs) {

        for (int i = 0; i < myargs.size(); i++) {
            try {
                this.clientSocket = new Socket(myargs.get(i), BULB_PORT);
                DataOutputStream outdata = new DataOutputStream(clientSocket.getOutputStream());
                String command = "{\"id\":" + id + ",\"method\":\"toggle\",\"params\":[]}\r\n";
                outdata.writeBytes(command);
                id++;
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void setPower(ArrayList<String> myargs) {

        if (myargs.size() >= 2) {
            String state = myargs.get(0);
            myargs.remove(0);
            String duration = "500";

            //now we should check if next object is an IP or a parameter; comes in the future
            for (int i = 0; i < myargs.size(); i++) {
                try {
                    this.clientSocket = new Socket(myargs.get(i), BULB_PORT);
                    DataOutputStream outdata = new DataOutputStream(clientSocket.getOutputStream());
                    String command = "{\"id\":" + id + ",\"method\":\"set_power\",\"params\":[\""+state+"\",\"smooth\","+duration+"]}\r\n";
                    outdata.writeBytes(command);
                    id++;
                    clientSocket.close();
                } catch (IOException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            YeeLightBulbsController.printUsage();
        }
    }

    public void startController(ArrayList<String> myargs) {

        String operation = myargs.get(0);
        myargs.remove(0);

        this.executeOperation(operation, myargs);
    }

    private void executeOperation(String operation, ArrayList<String> myargs) {

        switch (operation) {
            case "t":
                this.toggleBulb(myargs);
                break;
            case "power":
            case "p":
                this.setPower(myargs);
                break;
            default:
                YeeLightBulbsController.printUsage();
        }
    }
}
