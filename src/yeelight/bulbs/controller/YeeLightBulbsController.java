package yeelight.bulbs.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class YeeLightBulbsController {

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Controller c = new Controller();
        ArrayList<String> myargs = new ArrayList<String> ();
        
        myargs.addAll(Arrays.asList(args));
        
        if (myargs.size()<2)
            YeeLightBulbsController.printUsage();
        else
            c.startController(myargs);

       
    }

    public static void printUsage() {
        System.out.println("Usage: java -jar [options] [ip_bulb1] [ip_bulb2]...");
        System.out.println("Available options:");
        System.out.println("t - toggle");
        System.out.println("p - power	off/on e.g. java -jar p on [ip_bulb16]");
    }

}
