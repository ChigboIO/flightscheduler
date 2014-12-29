/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flightscheduler;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author SCIENCE
 */
public class ControlPanel extends JPanel {
    private final JPanel panel_downControl;
    private final JLabel label_origin, label_destination, label_time;
    JComboBox combo_origin, combo_destination, combo_hour, combo_munite;
    JButton button_findRoute;
    Statement statement;
    ResultSet result;
    String[] locations;
    String[] hours;
    String[] munites;
    public ControlPanel(){
        setLayout(new BorderLayout());
        panel_downControl = new JPanel();
        panel_downControl.setLayout(new FlowLayout());
        
        hours = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", 
        "17", "18", "19", "20", "21", "22", "23"};
        munites = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", 
        "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", 
        "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" };
        
        label_origin = new JLabel("Origin: ");
        label_destination = new JLabel("Destination:");
        label_time = new JLabel("Time:");
        
        locations = null;
        
        updater();
        
        combo_hour = new JComboBox(hours);
        combo_munite = new JComboBox(munites);
        
        button_findRoute = new JButton("Find Route");
        button_findRoute.addActionListener(Main.eventhandler);
        
        panel_downControl.add(label_origin);
        panel_downControl.add(combo_origin);
        panel_downControl.add(label_destination);
        panel_downControl.add(combo_destination);
        panel_downControl.add(label_time);
        panel_downControl.add(combo_hour);
        panel_downControl.add(new JLabel(":"));
        panel_downControl.add(combo_munite);
        panel_downControl.add(button_findRoute);
        
        this.add(panel_downControl, BorderLayout.NORTH);
    }
    
    public void updater(){
        try
        {
            statement = Connect.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
            result = statement.executeQuery("SELECT * FROM airports");
            result.last();
            locations = new String[result.getRow()];

            result.beforeFirst();
            int i = 0;
            while(result.next())
            {
                locations[i] = result.getString("airport_name");
                i++;
            }

        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(Main.contentPane,"SQLException ::: could not retrive from the DB :: "+ex.getMessage());
        } 
        
        combo_origin = new JComboBox(locations);
        combo_destination = new JComboBox(locations);
        
    }
}
