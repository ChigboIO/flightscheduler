/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flightscheduler;

import java.util.Calendar;

/**
 *
 * @author SCIENCE
 */
public class Flight  {
  private final String id; 
  private final Airport origin;
  private final Airport destination;
  private final Calendar departure;
  private final Calendar arrival;
  private final int flightTime;
  
  public Flight(String id, Airport origin, Airport destination, String departure, String arrival) {
    this.id = id;
    
    this.origin = origin;
    this.destination = destination;
    this.departure = Calendar.getInstance();
    this.departure.set(Calendar.HOUR_OF_DAY, Integer.valueOf(departure.split(":")[0]));
    this.departure.set(Calendar.MINUTE, Integer.valueOf(departure.split(":")[1]));
    
    this.arrival = Calendar.getInstance();
    this.arrival.set(Calendar.HOUR_OF_DAY, Integer.valueOf(arrival.split(":")[0]));
    this.arrival.set(Calendar.MINUTE, Integer.valueOf(arrival.split(":")[1]));
    
    this.flightTime = (int) ((this.arrival.getTimeInMillis()/(1000*60)) - (this.departure.getTimeInMillis()/(1000*60)));
    
  }
  
  public String getId() {
    return id;
  }
  public Airport getDestination() {
    return destination;
  }

  public Airport getOrigin() {
    return origin;
  }
  public int getFlightTime() {
    return flightTime;
  }
  
  public Calendar getDeparture() {
    return departure;
  }
  public Calendar getArival() {
    return arrival;
  }
  
  @Override
  public String toString() {
    return origin.toString() + " " + destination.toString();
  }
  
} 

