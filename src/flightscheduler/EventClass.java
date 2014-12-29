/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flightscheduler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JOptionPane;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 *
 * @author SCIENCE
 */
public class EventClass implements ActionListener, PopupMenuListener, WindowListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(Main.menu.menu_exit)){
            System.exit(0);
        }
        else if(e.getSource().equals(Main.menu.menu_airports)){
            new ManageAirports();
        }
        else if(e.getSource().equals(Main.menu.menu_flights)){
            new ManageFlights();
        }
        else if(e.getSource().equals(Main.control.button_findRoute)){
            new Scheduler(Main.control.combo_origin.getSelectedItem().toString(), Main.control.combo_destination.getSelectedItem().toString(),
             Main.control.combo_hour.getSelectedItem().toString()+":"+ Main.control.combo_munite.getSelectedItem().toString()+":00");
        }
    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        //do combo selection task here
    }

    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
    }

    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
        //noting happens
    }

    @Override
    public void windowClosing(WindowEvent e) {
        //something happens here
        Main.control.updater();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        //or here
        JOptionPane.showMessageDialog(null, "closing window");
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

}
