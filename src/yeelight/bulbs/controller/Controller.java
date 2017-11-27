package yeelight.bulbs.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
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
    private DataOutputStream outdata;

    public void toggleBulb(ArrayList<String> myargs) {

        for (int i = 0; i < myargs.size(); i++) {

            this.createConnection(myargs.get(i));
            String command = "{\"id\":" + id + ",\"method\":\"toggle\",\"params\":[]}\r\n";
            this.writeToBulb(command);

        }
    }

    private void setPower(ArrayList<String> myargs) {

        if (myargs.size() >= 2) {
            String state = myargs.get(0);
            myargs.remove(0);
            String duration = "500";

            for (int i = 0; i < myargs.size(); i++) {
                this.createConnection(myargs.get(i));
                String command = "{\"id\":" + id + ",\"method\":\"set_power\",\"params\":[\"" + state + "\",\"smooth\"," + duration + "]}\r\n";
                this.writeToBulb(command);
            }
        } else {
            YeeLightBulbsController.printUsage();
        }
    }

    private void createConnection(String ip) {
        try {
            this.clientSocket = new Socket(ip, BULB_PORT);
            this.outdata = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException ex) {

            if (ex instanceof ConnectException) {
                System.out.println("error - connection timed out");
                System.out.println("is the bulb connected to the wifi? did you specify the right ip?");
            } else 
                if (ex instanceof UnknownHostException)
                    System.out.println("error - unknown host - please check your specified ip");
            else
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            
            System.exit(0);
        }

    }

    private void writeToBulb(String command) {
        try {
            this.outdata.writeBytes(command);
            id++;
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startController(ArrayList<String> myargs) {

        String operation = myargs.get(0);
        myargs.remove(0);

        this.executeOperation(operation, myargs);
    }

    private void executeOperation(String operation, ArrayList<String> myargs) {

        switch (operation) {
            case "toggle":
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
