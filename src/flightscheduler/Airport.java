/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flightscheduler;

import java.sql.Time;
import java.util.Calendar;

/**
 *
 * @author SCIENCE
 */
public class Airport {
  final private String id;
  final private String name;
  final private int MCT;
  private int distance = Integer.MAX_VALUE;
  private Airport predecessor = null;
  private boolean visited = false;
  private Flight flightBy;
  
  
  public Airport(String id, String name, int MCT) {
    this.id = id;
    this.name = name;
    this.MCT = MCT;
  }
  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }
  
  public int getMCT() {
    return MCT;
  }
  
  public void setDistance(int distance){
      this.distance = distance;
  }
  
  public int getDistance(){
      return distance;
  }
  
  public void setPredecessor(Airport pred){
      this.predecessor = pred;
  }
  
  public Airport getPredecessor(){
      return predecessor;
  }
  
  public void setVisited(boolean visited){
      this.visited = visited;
  }
  
  public boolean getVisited(){
      return visited;
  }
  public void setFlightBy(Flight flight){
      this.flightBy = flight;
  }
  public Flight getFlightBy(){
      return flightBy;
  }
  /*
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }
  */
  @Override
  public String toString() {
    return name;
  }
  
} 

