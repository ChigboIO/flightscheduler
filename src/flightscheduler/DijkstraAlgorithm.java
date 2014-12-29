/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightscheduler;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author SCIENCE
 */
public class DijkstraAlgorithm {

    private final List<Airport> airports;
    private final List<Flight> flights;
    private final Calendar startTime;
    private final String timeString;
    private Set<Airport> unVisitedAirports;
    private Flight currentFlight;
  //private Map<Airport, Airport> predecessors;
    //private Map<Airport, Integer> distance;

    public DijkstraAlgorithm(Graph graph, String timeString) {
        // create a copy of the array so that we can operate on this array
        this.airports = new ArrayList<Airport>(graph.getVertexes());
        this.flights = new ArrayList<Flight>(graph.getEdges());
        //this.time = Time.valueOf(timeString);
        this.timeString = timeString;
        startTime = Calendar.getInstance();
        try{
            startTime.set(Calendar.HOUR_OF_DAY, Integer.valueOf(timeString.split(":")[0]));
            startTime.set(Calendar.MINUTE, Integer.valueOf(timeString.split(":")[1]));
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Please select a valid time from the drop down list.");
        }
        //startTime = currentTime.get(Calendar.MINUTE);
        //currentTime = (Calendar) startTime.clone();

        //timeMap = new HashMap<>();
    //JOptionPane.showMessageDialog(null, "Starting Time: "+ startTime.get(Calendar.HOUR_OF_DAY) + " : " + startTime.get(Calendar.MINUTE)
      //   + " on "+ currentTime.get(Calendar.DAY_OF_WEEK));
    }

    public void execute(Airport source, Airport dest) {
        //this.origin = source;
        //settledAirports = new HashSet<Airport>();
        unVisitedAirports = new HashSet<Airport>();
    //distance = new HashMap<Airport, Integer>();
        //predecessors = new HashMap<Airport, Airport>();
        //distance.put(source, 0);
        source.setDistance(0);
        source.setFlightBy(new Flight(null, null, source, this.timeString, this.timeString));
        //source.setArrivedAt(startTime);
        //timeMap.put(source, currentTime);
        unVisitedAirports.add(source);
        //int departureTime = 0;
        while (unVisitedAirports.size() > 0) {
            Airport airport = getMinimum(unVisitedAirports);
            //currentTime = (Calendar) this.startTime.clone();
            //currentTime.add(Calendar.MINUTE, airport.getDistance());
            //currentTime.add(Calendar.MINUTE, airport.getMCT());
            //airport.setArrivedAt(currentTime);
            //timeMap.put(airport, currentTime);
            airport.setVisited(true);
            unVisitedAirports.remove(airport);
      //JOptionPane.showMessageDialog(null, "Distance of "+airport.getName()+" is: "+ airport.getDistance());
            //JOptionPane.showMessageDialog(null, "Adding to minute " + String.valueOf(airport.getDistance() - ((airport.getPredecessor() != null) ? airport.getPredecessor().getDistance() : 0)));
            //currentTime.add(Calendar.MINUTE, airport.getDistance() - ((airport.getPredecessor() != null) ? airport.getPredecessor().getDistance() : 0));
            //JOptionPane.showMessageDialog(null, "Landing in "+ airport.getName() +" by: "+ currentTime.get(Calendar.HOUR_OF_DAY) + " : " + 
            //       currentTime.get(Calendar.MINUTE) + " on "+ currentTime.get(Calendar.DAY_OF_WEEK));

            findMinimalDistances(airport);
        }
        showPath(dest);
    //JOptionPane.showMessageDialog(null, "Arrival Time: "+ currentTime.get(Calendar.HOUR_OF_DAY) + " : " + currentTime.get(Calendar.MINUTE)
        // + " on "+ currentTime.get(Calendar.DAY_OF_WEEK));
    }

    private void findMinimalDistances(Airport airport) {
        List<Airport> adjacentAirports = getNeighbors(airport);
        for (Airport target : adjacentAirports) {
            int time = findWaitingAndFlightTime(airport, target);
            if (target.getDistance() > (airport.getDistance() + time)) {
                target.setDistance(airport.getDistance() + time);
                //target.setArrivedAt(currentTime);
                target.setPredecessor(airport);
                target.setFlightBy(currentFlight);
                unVisitedAirports.add(target);
            }
        }

    }

    private int findWaitingAndFlightTime(Airport airport, Airport target) {
        for (Flight flight : flights) {
            if (flight.getOrigin().equals(airport) && flight.getDestination().equals(target) && 
                    flight.getDeparture().after(airport.getFlightBy().getArival())) {
                int waiting = (int) ((flight.getDeparture().getTimeInMillis() / (1000 * 60)) - (airport.getFlightBy().getArival().getTimeInMillis() / (1000 * 60)));
                //JOptionPane.showMessageDialog(null, "Total waiting time from " + airport.getName() + " To " + target.getName() + " is " + waiting);
                currentFlight = flight; //flight.getDestination().setFlightBy(flight);
                return flight.getFlightTime() + waiting;
            }
        }
        //JOptionPane.showMessageDialog(null, "Sorry, there is no route to your destination from your origin");
        return 0;
    }

    private List<Airport> getNeighbors(Airport airport) {
        List<Airport> neighbors = new ArrayList<Airport>();
        for (Flight flight : flights) {
        //if(flight.getOrigin().equals(this.origin))
            //    this.startTime = flight.getDeparture();
            Calendar newTime = (Calendar) this.startTime.clone();
            newTime.add(Calendar.MINUTE, airport.getDistance());
            newTime.add(Calendar.MINUTE, airport.getMCT());
            if (flight.getOrigin().equals(airport) && (flight.getDeparture().compareTo(newTime) >= 0) && !flight.getDestination().getVisited()) {
                
                neighbors.add(flight.getDestination());

            }
        }
        return neighbors;
    }

    private Airport getMinimum(Set<Airport> vertexes) {
        Airport minimum = null;
        for (Airport vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (vertex.getDistance() < minimum.getDistance()) {
                    minimum = vertex;

                }
            }
        }
        return minimum;
    }
    /*
     private boolean isSettled(Airport vertex) {
     return vertex.getVisited();
     }

     private int getShortestDistance(Airport destination) {
     Integer d = destination.getDistance();
    
     return d;
     }
     */
    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */

    public void showPath(Airport target) {
        LinkedList<Airport> path = new LinkedList<Airport>();
        String description = "";
        
        for(Airport step = target; step != null; step = step.getPredecessor()){
        
        path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        //return path;
        
        String p = "";
        Main.graphPanel.pathString = p;
        Main.graphPanel.repaint();
        
        if(path.size() > 1){
            for (Airport step : path) {
                
                p += step.toString() + "-";
                Main.graphPanel.pathString = p;
                Main.graphPanel.repaint();
                
                if(step.getPredecessor() != null){
                    description += step.getFlightBy().getDeparture().get(Calendar.HOUR_OF_DAY) + ":" +
                            step.getFlightBy().getDeparture().get(Calendar.MINUTE)+
                            "\t-\t Departure from " + step.getPredecessor().getName()+"\r\n";
                    
                }
                description += step.getFlightBy().getArival().get(Calendar.HOUR_OF_DAY) + ":" + step.getFlightBy().getArival().get(Calendar.MINUTE)+
                        "\t-\t Arrival at " + step.getName()+"\r\n";
                
                //JOptionPane.showMessageDialog(null, "Arrived at " + vertex.getName() + " By " + vertex.getFlightBy().getArival().get(Calendar.HOUR_OF_DAY) + ":"
                  //  + vertex.getFlightBy().getArival().get(Calendar.MINUTE));
        
                //System.out.println(vertex);
            }
            JTextArea tf = new JTextArea(description);
            tf.setEditable(false);
            tf.setFocusable(false);
            tf.setOpaque(false);
            JOptionPane.showMessageDialog(null, tf, "Flights Description", JOptionPane.PLAIN_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Sorry, your journey cannot be completed from the time you chose. Try leaving earlier");
            //System.out.println("Sorry, there is no route to your destination from your origin");
        }
        //System.out.println(p);
    }

}
