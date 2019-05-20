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

import static org.junit.jupiter.api.Assertions.fail;
import org.junit.Test;

public class GraphTest {

	

	  @Test
	  public void test_001() {
	    Graph g = new Graph();
	    for(int i=1; i<=5; i++) {
	      g.addVertex(Integer.toString(i));
	    }
	    g.addEdge("3", "4");
	    g.addEdge("1", "2");
	    if(!g.getAdjacentVerticesOf("1").contains("2")) {
	      fail("3 have vertex of 4 not "+g.getAdjacentVerticesOf("1"));
	    }
	  }

	  @Test
	  public void test_002() {
	    Graph g = new Graph();
	    for(int i=1; i<=5; i++) {
	      g.addVertex(Integer.toString(i));
	    }
	    g.addEdge("4", "3");
	    g.addEdge("2", "1");
	    g.removeEdge("1", "2");
	    if(g.size()!=1) {
	      fail("Graph's size be 1 not "+g.size());
	    }
	  }
	
  @Test
  public void test_003() {
    Graph g = new Graph();
    for(int i=1; i<=5; i++) {
      g.addVertex(Integer.toString(i));
    }
    if(g.order()!=5) {
      fail("Graph's order is 5 not "+g.order());
    }
  }
  

  @Test
  public void test_004() {
    Graph g = new Graph();
    for(int i=1; i<=5; i++) {
      g.addVertex(Integer.toString(i));
    }
    for(int i=1; i<=3; i++) {
      g.removeVertex(Integer.toString(i));
    }

    if(g.order()!=2) {
      fail("Graph's order be 2 not "+g.order());
    }
  }
  
 

  @Test
  public void test_005() {
    Graph graph = new Graph();
    for(int i=1; i<=5; i++) {
      graph.addVertex(Integer.toString(i));
    }
    graph.addEdge("1", "2");
    graph.addEdge("3", "4");
    if(graph.size()!=2) {
      fail("Graph's size should be 2 not "+graph.size());
    }
  }


}

