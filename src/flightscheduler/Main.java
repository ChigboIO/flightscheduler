/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightscheduler;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * 
 */
public class Main extends JFrame{
    static Connect connect;
    static EventClass eventhandler;
    static JPanel contentPane;
    static MyMenu menu;
    static ControlPanel control;
    static DaysPanel daysPanel;
    static GraphPanel graphPanel;
    public Main() {
        super("Flight Scheduler");
        
        initComponents();
        
        //contentPane.setBackground(Color.BLUE);
        contentPane.setLayout(new BorderLayout());
        //contentPane.add(daysPanel, BorderLayout.NORTH);
        contentPane.add(control, BorderLayout.NORTH);
        contentPane.add(graphPanel, BorderLayout.CENTER);
        
        setJMenuBar(menu);
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850,600);
        setLocationRelativeTo(null);
        setVisible(true);
        //revalidate();
    }

    private void initComponents(){
        connect = new Connect();
        eventhandler = new EventClass();
        contentPane = new JPanel();
        control = new ControlPanel();
        daysPanel = new DaysPanel();
        menu = new MyMenu();
        graphPanel = new GraphPanel();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            JOptionPane.showMessageDialog(null, "Error setting UI to JTatoo -> TextureLookAndFeel ::: "+ ex.getMessage());
        }
        new Main();
    }
}
