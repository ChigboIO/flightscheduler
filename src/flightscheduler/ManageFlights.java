/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flightscheduler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SCIENCE
 */
public class ManageFlights extends JDialog implements ActionListener{
    static ResultSet result;
    Statement statement;
    private final JPanel contentPane;
    JScrollPane scroll_tableScroll;
    JComboBox combo_fromAirport, combo_toAirport, combo_departureHour, combo_departureMunite, combo_arrivalHour, combo_arrivalMunite;
    static JButton button_addFlight;
    public ManageFlights(){
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        
        try
        {
            statement = Connect.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(Main.contentPane,"SQLException ::: could not retrive from the DB :: "+ex.getMessage());
        }
        
        scroll_tableScroll = createTable();
        contentPane.add(scroll_tableScroll, BorderLayout.CENTER);
        //contentPane.add(createSouthPanel(), BorderLayout.SOUTH);
        
        setTitle("Flights Time - Table");
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        //setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
        
        getContentPane().add(this.contentPane);
        setSize(625, 500);
        //this.setLocation(200,200);
        setLocationRelativeTo(Main.contentPane);
        setResizable(false);
        //setUndecorated(true);
        setVisible(true);
    }
    
    private JScrollPane createTable(){
        JTable table;
        String[] header = null;
        Object[][] data = null;
        
        try
        {
            result = statement.executeQuery("SELECT * FROM flights");
            result.last();
            header = new String[]{"From", "To", "Departure Time", "Arrival Time" };
            data = new Object[result.getRow()][4];

            result.beforeFirst();
            int i = 0;
            while(result.next())
            {
                data[i][0] = result.getString("origin");
                data[i][1] = result.getString("destination");
                data[i][2] = result.getString("departure");
                data[i][3] = result.getString("arrival");

                i++;
            }

        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(Main.contentPane,"SQLException ::: could not retrive from the DB :: "+ex.getMessage());
        } 
       
        DefaultTableModel model = new DefaultTableModel(data, header);
        table = new JTable(model);
        
        table.setOpaque(false);
        ((DefaultTableCellRenderer)table.getDefaultRenderer(Object.class)).setOpaque(false);
        table.setRowHeight(30);
        table.setRowSelectionAllowed(true);
        table.setCellSelectionEnabled(false);
        //table.setFocusable(false);
        table.setSelectionBackground(Color.LIGHT_GRAY);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        
        
        JScrollPane spane = new JScrollPane(table);
        spane.setOpaque(false);
        spane.getViewport().setOpaque(false);
        spane.setViewportBorder(null);
        
        return spane;
    }
    
    private JPanel createSouthPanel(){
        
        combo_fromAirport = new JComboBox(Main.control.locations);
        combo_toAirport = new JComboBox(Main.control.locations);
        combo_departureHour = new JComboBox(Main.control.hours);
        combo_arrivalHour = new JComboBox(Main.control.hours);
        combo_departureMunite = new JComboBox(Main.control.munites);
        combo_arrivalMunite = new JComboBox(Main.control.munites);
        
        button_addFlight = new JButton("Add");
        button_addFlight.addActionListener(this);
        
        JPanel panel_south = new JPanel();
        panel_south.setLayout(new GridLayout(5,2,5,5));
        
        panel_south.add(new JLabel("From:"));
        panel_south.add(combo_fromAirport);
        panel_south.add(new JLabel("To:"));
        panel_south.add(combo_toAirport);
        panel_south.add(new JLabel("Departure Time:"));
        JPanel p1 = new JPanel();
        p1.add(combo_departureHour);
        p1.add(combo_departureMunite);
        panel_south.add(p1);
        panel_south.add(new JLabel("Arrival Time:"));
        JPanel p2 = new JPanel();
        p2.add(combo_arrivalHour);
        p2.add(combo_arrivalMunite);
        panel_south.add(p2);
        panel_south.add(new JLabel());
        panel_south.add(button_addFlight);
        
        panel_south.setBorder(new TitledBorder("Add new Flight"));
        
        return panel_south;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String from = combo_fromAirport.getSelectedItem().toString();
        String to = combo_toAirport.getSelectedItem().toString();
        String departure = combo_departureHour.getSelectedItem().toString() + ":" + combo_departureMunite.getSelectedItem().toString() + ":00";
        String arrival = combo_arrivalHour.getSelectedItem().toString() + ":" + combo_arrivalMunite.getSelectedItem().toString() + ":00";
        try{
            boolean inserted = statement.execute("INSERT INTO flights(origin, destination, departure, arrival) "
                    + "VALUES ('"+ from  +"', '"+ to +"', '"+ departure +"', '"+ arrival +"')");
            if(inserted)
                JOptionPane.showMessageDialog(Main.contentPane, "New Flight Added");
            
            contentPane.remove(scroll_tableScroll);
            scroll_tableScroll = createTable();
            contentPane.add(scroll_tableScroll, BorderLayout.CENTER);
            contentPane.validate();
            
            combo_fromAirport.setSelectedIndex(0);
            combo_toAirport.setSelectedIndex(0);
            combo_departureHour.setSelectedIndex(0);
            combo_departureMunite.setSelectedIndex(0);
            combo_arrivalHour.setSelectedIndex(0);
            combo_arrivalMunite.setSelectedIndex(0);

        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(Main.contentPane,"SQLException ::: could not retrive from the DB :: "+ex.getMessage());
        } 
    }
}
