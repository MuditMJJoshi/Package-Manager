//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           P4: Package Manager
//
// Author:          Mudit Joshi
//
// Course:          CS 400 2019
//
// Lecture:         Lec 001
//
// Email:           mjoshi6@wisc.edu 
//
// Due Date:        04/19/2019
//
// Files:           PackageManager.java
//					Graph.java
//					
//
// Lecturer's Name: Debra Deplar
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////


import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * A class to represent a directed graph, implements the GraphADT interface.
 * @author Mudit Joshi
 */
public class Graph implements GraphADT {
	private ArrayList<String> vertLst; 
	private int GraphS;
	private ArrayList<LinkedList<String>> edgLst; 
	private int GraphO; 
	
	/*
	 * Constructor method, initializes the private class variables.
	 */ 
	public Graph() {
		vertLst = new ArrayList<String>();
		GraphS = 0;
		edgLst = new ArrayList<LinkedList<String>>();
		GraphO = 0;
	}
	
	/**
     * Add new vertex to the graph.
     *
     * If vertex is null or already exists,
     * method ends without adding a vertex or 
     * throwing an exception.
     * 
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is not already in the graph 
     */
	public void addVertex(String vert) { 
		if(vert == null) {
		  return;
		}
		if(vertLst.contains(vert))
		  return;
		
		vertLst.add(vert);
		edgLst.add(new LinkedList<String>());
		GraphO++;
	}
	
	/**
     * Remove the edge from vertex1 to vertex2
     * from this graph.  (edge is directed and unweighted)
     * If either vertex does not exist,
     * or if an edge from vertex1 to vertex2 does not exist,
     * no edge is removed and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. both vertices are in the graph 
     * 3. the edge from vertex1 to vertex2 is in the graph
     */
	public void removeEdge(String vert1, String vert2) { 
	  if(vert1 == null || vert2 == null)
        return;
      
	  if(!(vertLst.contains(vert1) && vertLst.contains(vert2)))
	    return;
      
      if(!edgLst.get(vertLst.indexOf(vert1)).contains(vert2))
    	return;
      
      edgLst.get(vertLst.indexOf(vert1)).remove(vert2);
      GraphS--;
	}	

	/**
     * Returns a Set that contains all the vertices
     * 
	 */
	public Set<String> getAllVertices() {
		return new HashSet<String>(vertLst);
	}


	/**
     * Remove a vertex and all associated 
     * edges from the graph.
     * 
     * If vertex is null or does not exist,
     * method ends without removing a vertex, edges, 
     * or throwing an exception.
     * 
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is in the graph 
     */
	public void removeVertex(String vert) {
		
	  if(vert == null)
        return;
	  
	  if(!vertLst.contains(vert))
	    return;
	  
	  for(int i = 0; i < vertLst.size(); i++) {
	    edgLst.get(i).remove(vert);
	  }
	  edgLst.remove(vertLst.indexOf(vert));
	  vertLst.remove(vert);
	  GraphO--;
	}
	
	
	/**
     * Get all the neighbor (adjacent) vertices of a vertex
     *
	 */
	public List<String> getAdjacentVerticesOf(String vert) { 
	  if(vertLst.contains(vert))
	    return edgLst.get(vertLst.indexOf(vert));
	  else
	    return null;
	}
	
	/**
     * Returns the number of edges in this graph.
     */
    public int size() {
        return GraphS;
    }

	/**
     * Returns the number of vertices in this graph.
     */
	public int order() {
        return GraphO;
    }

	

	/**
	* Add the edge from vertex1 to vertex2
	* to this graph. (edge is directed and unweighted)
	* If either vertex does not exist, add the non-existing vertex to the graph and then create an edge.
	* If the edge exists in the graph,
	* no edge is added and no exception is thrown.
	* 
	* Valid argument conditions:
	* 1. neither vertex is null
	* 2. the edge is not in the graph
	*/
	public void addEdge(String vert1, String vert2) { 
	  if(vert1 == null || vert2 == null)
	    return;
	  
	  addVertex(vert1); // checks if already exists
	  addVertex(vert2); // checks if already exists
		
      if(edgLst.get(vertLst.indexOf(vert1)).contains(vert2))
		return;
	
      edgLst.get(vertLst.indexOf(vert1)).add(vert2);
      GraphS++;
	}
	
	
}
