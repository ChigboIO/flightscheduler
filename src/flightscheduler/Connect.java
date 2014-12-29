package flightscheduler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class Connect {

    public static Connection connection;
    private final String serverName;
    private final String DBPort;
    private final String mydatabase;
    private final String DATABASE_URL;
    private final String USERNAME;
    private final String PASSWORD;

    public Connect() {
        this.serverName = "localhost";
        this.DBPort = "3306";
        this.mydatabase = "flight_scheduler";
        
        //this.DATABASE_URL = "jdbc:derby:resources/flight_scheduler;create=true";
        this.DATABASE_URL = "jdbc:mysql://"+ this.serverName +":"+ this.DBPort +"/"+ this.mydatabase;
        this.USERNAME = "root";
        this.PASSWORD = "";
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            
            connection = DriverManager.getConnection(this.DATABASE_URL, this.USERNAME, this.PASSWORD);

        }/* catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "JDBC Driver not found ::: ClassNotFoundException : " + ex.getMessage());
            System.exit(0);
        }*/ catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQLException occured ::: SQLException : " + ex.getMessage());
        }
    }

}
