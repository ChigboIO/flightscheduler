/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flightscheduler;

import java.util.List;

/**
 *
 * @author SCIENCE
 */
public class Graph {
  private final List<Airport> vertexes;
  private final List<Flight> edges;

  public Graph(List<Airport> vertexes, List<Flight> edges) {
    this.vertexes = vertexes;
    this.edges = edges;
  }

  public List<Airport> getVertexes() {
    return vertexes;
  }

  public List<Flight> getEdges() {
    return edges;
  }  
} 

