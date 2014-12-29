/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flightscheduler;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author SCIENCE
 */
public class MyMenu extends JMenuBar{
    JMenu menu_file, menu_advance;
    JMenuItem menu_exit, menu_airports, menu_flights;
    public MyMenu(){
        menu_file = new JMenu("File");
        menu_advance = new JMenu("View");
        
        menu_exit = new JMenuItem("Exit");
        menu_exit.addActionListener(Main.eventhandler);
        menu_airports = new JMenuItem("Operational Airports");
        menu_airports.addActionListener(Main.eventhandler);
        menu_flights = new JMenuItem("Flights Table");
        menu_flights.addActionListener(Main.eventhandler);
        
        menu_file.add(menu_exit);
        menu_advance.add(menu_airports);
        menu_advance.add(menu_flights);
        
        this.add(menu_file);
        this.add(menu_advance);
    }
}
