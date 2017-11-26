/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeelight.bulbs.controller;

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
        List<String> myargs = Arrays.asList(args);
        int i=0;
        
        while(!myargs.isEmpty()) {
            c.toggleBulb(myargs.get(i));
            myargs.remove(i);
        }
    }

}
