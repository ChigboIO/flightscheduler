/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flightscheduler;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author comapq presario
 */
public class GraphPanel extends JPanel{
    //Toolkit kit = Toolkit.getDefaultToolkit();
    //Image img = kit.createImage("resources/images/onlinebackup.jpg");
    int[][] airportPos;
    ArrayList<String> airportNames;
    String[][] directFlights;
    Statement statement;
    ResultSet result;
    String pathString;
    public GraphPanel()
    {
        //EmptyBorder border = new EmptyBorder(5,10,15,10);
        //this.setBorder(border);
        try
        {
            statement = Connect.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
            result = statement.executeQuery("SELECT * FROM airports");
            result.last();
            airportPos = new int[result.getRow()][2];
            airportNames = new ArrayList<String>();
            result.beforeFirst();
            int i = 0;
            //int[][] x = new int[5][3];
            while(result.next())
            {
                airportNames.add(result.getString("airport_name").toUpperCase());
                airportPos[i][0] = result.getInt("xpos");
                airportPos[i][1] = result.getInt("ypos");
                i++;
            }

            statement = Connect.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
            result = statement.executeQuery("SELECT * FROM flights");
            result.last();
            directFlights = new String[result.getRow()][2];

            result.beforeFirst();
            int j = 0;
            while(result.next())
            {
                directFlights[j][0] = result.getString("origin").toUpperCase();
                directFlights[j][1] = result.getString("destination").toUpperCase();
                j++;
            }

        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(Main.contentPane,"SQLException ::: could not retrive from the DB :: "+ex.getMessage());
        } 
        
        pathString = "";
        
    }
    @Override
    public void paintComponent(Graphics g2)
    {
        Graphics2D g = (Graphics2D)g2;
        //super.setBackground(Color.CYAN);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        //g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
        g.setStroke(new BasicStroke(2.0f));
        
        g.setColor(Color.DARK_GRAY);
        for(int j = 0; j < directFlights.length; j++){
                int x1 = airportPos[airportNames.indexOf(directFlights[j][0])][0];
                int y1 = airportPos[airportNames.indexOf(directFlights[j][0])][1];
                int x2 = airportPos[airportNames.indexOf(directFlights[j][1])][0];
                int y2 = airportPos[airportNames.indexOf(directFlights[j][1])][1];
                
            g.drawLine(x1, y1, x2, y2);
        }
        
        String[] pathArray = pathString.split("-");
        g.setStroke(new BasicStroke(3.0f));
        g.setColor(Color.GREEN);
        for(int k = 0; k < pathArray.length-1; k++){
            int x1 = airportPos[airportNames.indexOf(pathArray[k])][0];
            int y1 = airportPos[airportNames.indexOf(pathArray[k])][1];
            int x2 = airportPos[airportNames.indexOf(pathArray[k+1])][0];
            int y2 = airportPos[airportNames.indexOf(pathArray[k+1])][1];
            g.drawLine(x1, y1, x2, y2);
        }
        
        
        for(int i = 0; i < airportNames.size(); i++){
            int x = airportPos[i][0]-10;
            int y = airportPos[i][1]-10;
            int width = 20;
            int height = 20;
            g.setColor(Color.red);
            g.fillOval(x, y, width, height);
            
            g.setColor(Color.WHITE);
            g.setFont(new Font(Font.SERIF, Font.BOLD + Font.ITALIC, 14));
            g.drawString(airportNames.get(i), airportPos[i][0]-15, airportPos[i][1]+22);
        }
    }
}
