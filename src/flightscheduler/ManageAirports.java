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
public class ManageAirports extends JDialog implements ActionListener{
    static ResultSet result;
    Statement statement;
    private final JPanel contentPane;
    JScrollPane scroll_tableScroll;
    JTextField text_airportName, text_mct, text_xPos, text_yPos;
    static JButton button_addAirport;
    public ManageAirports(){
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
        
        setTitle("Operational Airports");
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        //setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
        
        getContentPane().add(this.contentPane);
        setSize(425, 465);
        //this.setLocation(200,200);
        setLocationRelativeTo(Main.contentPane);
        setResizable(false);
        //setUndecorated(true);
        setVisible(true);
        this.addWindowListener(Main.eventhandler);
    }
    
    private JScrollPane createTable(){
        JTable table;
        String[] header = null;
        Object[][] data = null;
        
        try
        {
            result = statement.executeQuery("SELECT * FROM airports");
            result.last();
            header = new String[]{"Airport Name","MCT(mins)" };
            data = new Object[result.getRow()][2];

            result.beforeFirst();
            int i = 0;
            while(result.next())
            {
                data[i][0] = result.getString("airport_name");
                data[i][1] = result.getInt("mct");

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
        text_airportName = new JTextField();
        text_mct = new JTextField();
        text_xPos = new JTextField(5);
        text_yPos = new JTextField(5);
        JPanel panel_loc = new JPanel();
        panel_loc.add(new JLabel("Long."));
        panel_loc.add(text_xPos);
        panel_loc.add(new JLabel("Lat."));
        panel_loc.add(text_yPos);
        button_addAirport = new JButton("Add");
        button_addAirport.addActionListener(this);
        
        JPanel panel_south = new JPanel();
        panel_south.setLayout(new GridLayout(4,2,5,5));
        
        panel_south.add(new JLabel("Airport Name:"));
        panel_south.add(text_airportName);
        panel_south.add(new JLabel("Minimum Conection Time(mins):"));
        panel_south.add(text_mct);
        panel_south.add(new JLabel("Map Location :"));
        panel_south.add(panel_loc);
        panel_south.add(new JLabel());
        panel_south.add(button_addAirport);
        
        panel_south.setBorder(new TitledBorder("Add new Airport"));
        
        return panel_south;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String name = text_airportName.getText().toUpperCase();
        int mct = Integer.parseInt(text_mct.getText());
        int x = Integer.parseInt(text_xPos.getText());
        int y = Integer.parseInt(text_yPos.getText());
        try{
            ResultSet r = statement.executeQuery("SELECT max(airport_id) AS maxid FROM airports");
            int nextId = r.first()? r.getInt("maxid") + 1 : 1;
            boolean inserted = (boolean) statement.execute("INSERT INTO airports(airport_id, airport_name, mct, xpos, ypos) "
                    + "VALUES ("+ nextId  +", '"+ name +"', "+ mct +", "+ x +", "+ y +")");
            if(inserted)
                JOptionPane.showMessageDialog(Main.contentPane, "New Airport Added");
            
            contentPane.remove(scroll_tableScroll);
            scroll_tableScroll = createTable();
            contentPane.add(scroll_tableScroll, BorderLayout.CENTER);
            contentPane.validate();
            
            text_airportName.setText("");
            text_mct.setText("");
            text_xPos.setText("");
            text_yPos.setText("");
        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(Main.contentPane,"SQLException ::: could not retrive from the DB :: "+ex.getMessage());
        } 
    }
}
