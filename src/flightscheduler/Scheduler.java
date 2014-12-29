/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightscheduler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author SCIENCE
 */
public class Scheduler {

    private Statement statement;
    private ResultSet result;
    private final ArrayList<Airport> airports;
    private final ArrayList<Flight> flights;
    private final HashMap<String, Airport> map;

    public Scheduler(String src, String dest, String time) {
        airports = new ArrayList<>();
        flights = new ArrayList<>();
        map = new HashMap<>();
        
        try
        {
            statement = Connect.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
            result = statement.executeQuery("SELECT * FROM airports");
            
            result.beforeFirst();
            int i = 1;
            while(result.next())
            {
                Airport location = new Airport("Airport_" + i, result.getString("airport_name"), result.getInt("mct"));
                airports.add(location);
                map.put(result.getString("airport_name"), location);
                i++;
            }

            result = statement.executeQuery("SELECT * FROM flights ORDER BY departure");
            
            result.beforeFirst();
            int j = 1;
            while(result.next())
            {
                Flight lane = new Flight("Flight_"+ j, map.get(result.getString("origin")), map.get(result.getString("destination")), 
                        result.getString("departure"), result.getString("arrival"));
                flights.add(lane);
                j++;
            }

        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(Main.contentPane,"SQLException ::: could not retrive from the DB :: "+ex.getMessage());
        }
        // Lets check from location Loc_1 to Loc_10
        Graph graph = new Graph(airports, flights);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph, time);
        dijkstra.execute(map.get(src), map.get(dest));
        //LinkedList<Airport> path = dijkstra.getPath();

        //assertNotNull(path);
        //assertTrue(path.size() > 0);

    }


}
