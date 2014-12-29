/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flightscheduler;

import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author SCIENCE
 */
public class DaysPanel extends JPanel {
    JLabel label_day;
    JComboBox combo_day;
    public DaysPanel(){
        setLayout(new FlowLayout());
        
        label_day = new JLabel("Select Day:");
        combo_day = new JComboBox(new String[]{"Monday", "Teusday", "Wednesday", "Thursday", "Friday", "Satuday", "Sunday"});
        combo_day.addPopupMenuListener(Main.eventhandler);
        
        this.add(label_day);
        this.add(combo_day);
    }
}
