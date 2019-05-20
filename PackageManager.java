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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.Stack;


/**
 * 
 * PackageManager is used to process json package dependency files
 * and provide function that make that information available to other users.
 * 
 * Each package that depends upon other packages has its own
 * entry in the json file.  
 * 
 * Package dependencies are important when building software, 
 * as you must install packages in an order such that each package 
 * is installed after all of the packages that it depends on 
 * have been installed.
 * 
 * For example: package A depends upon package B,
 * then package B must be installed before package A.
 * 
 * This program will read package information and 
 * provide information about the packages that must be 
 * installed before any given package can be installed.
 * all of the packages in
 * 
 * You may add a main method, but we will test all methods with
 * our own Test classes.
 */

public class PackageManager {
    
    private Graph graph;
    /*
     * Package Manager default no-argument constructor.
     */
    public PackageManager() {
       graph = new Graph(); 
    }
    
    /**
     * Takes in a file path for a json file and builds the
     * package dependency graph from it. 
     * 
     * @param jsonFilepath the name of json data file with package dependency information
     * @throws FileNotFoundException if file path is incorrect
     * @throws IOException if the given file cannot be read
     * @throws ParseException if the given json cannot be parsed 
     */
    public void constructGraph(String jsonFilepath) throws FileNotFoundException, IOException, ParseException {
      JSONObject objects = (JSONObject) new JSONParser().parse(new FileReader(jsonFilepath));
      JSONArray packs = (JSONArray) objects.get("packages");
      
      for(int i = 0; i < packs.size(); i++) {
        JSONObject newobj = (JSONObject) packs.get(i);
        String vert = (String) newobj.get("name");
        for(Object dep : ((JSONArray) newobj.get("dependencies"))) {
          graph.addEdge(vert, (String) dep); 
        }
      }
    }
    
    /**
     * Helper method to get all packages in the graph.
     * 
     * @return Set<String> of all the packages
     */
    public Set<String> getAllPackages() {
        return graph.getAllVertices(); 
    }
    
    private boolean objDectector(String vertex, Stack<String> stack) {
    	for(int i = 0; i < graph.getAdjacentVerticesOf(vertex).size(); i++) {
    		if(stack.contains(graph.getAdjacentVerticesOf(vertex).get(i)))
    			return true;
    	}
    	return false;
    } 
    
    private String Dependency(String vertex, Set<String> unvisitedVertices) {
    	List<String> dep = graph.getAdjacentVerticesOf(vertex);
    	for(int i = 0; i < dep.size(); i++) {
    		if(unvisitedVertices.contains(dep.get(i)))
    			return dep.get(i);
    	}
    	return null;
    }
    
    /**
     * Given a package name, returns a list of packages in a
     * valid installation order.  
     * 
     * Valid installation order means that each package is listed 
     * before any packages that depend upon that package.
     * 
     * @return List<String>, order in which the packages have to be installed
     * 
     * @throws CycleException if you encounter a cycle in the graph while finding
     * the installation order for a particular package. Tip: Cycles in some other
     * part of the graph that do not affect the installation order for the 
     * specified package, should not throw this exception.
     * 
     * @throws PackageNotFoundException if the package passed does not exist in the 
     * dependency graph.
     */
    public List<String> getInstallationOrder(String pkg) throws CycleException, PackageNotFoundException {
      
    	if(!graph.getAllVertices().contains(pkg)) {
    		throw new PackageNotFoundException();
    	}
    	List<String> Order=new ArrayList<String>();
    	List<String> Stack=new ArrayList<String>();
    	InstallationHelper(pkg,Stack,Order);
    	
      return Order;
    }
    
    private void InstallationHelper(String current,List<String>
    callstack,List<String> installOrder) throws CycleException{
    	
    	if(callstack.contains(current)) {
    		throw new CycleException();
    	}
    	
    	if(!installOrder.contains(current)) {
    		if(callstack.isEmpty()) {
    			installOrder.add(current);
    		}
    		else {
    			installOrder.add(0,current);
    		}
    	}
    	callstack.add(current);
    	
    	List<String> nxt= graph.getAdjacentVerticesOf(current);
    	Collections.sort(nxt);
    	
    	for(String newnxt : nxt) {
    		InstallationHelper(newnxt, callstack, installOrder);
    	}
    	callstack.remove(current);
    }
    
    
    /**
     * Given two packages - one to be installed and the other installed, 
     * return a List of the packages that need to be newly installed. 
     * 
     * For example, refer to shared_dependecies.json - toInstall("A","B") 
     * If package A needs to be installed and packageB is already installed, 
     * return the list ["A", "C"] since D will have been installed when 
     * B was previously installed.
     * 
     * @return List<String>, packages that need to be newly installed.
     * 
     * @throws CycleException if you encounter a cycle in the graph while finding
     * the dependencies of the given packages. If there is a cycle in some other
     * part of the graph that doesn't affect the parsing of these dependencies, 
     * cycle exception should not be thrown.
     * 
     * @throws PackageNotFoundException if any of the packages passed 
     * do not exist in the dependency graph.
     */
    public List<String> toInstall(String newPkg, String installedPkg) throws CycleException, PackageNotFoundException {
    	List<String> Inst = getInstallationOrder(newPkg);
    	List<String> oldInst = getInstallationOrder(installedPkg);
    	Inst.removeAll(oldInst);
    	return Inst;
    }
    
    /**
     * Return a valid global installation order of all the packages in the 
     * dependency graph.
     * 
     * assumes: no package has been installed and you are required to install 
     * all the packages
     * 
     * returns a valid installation order that will not violate any dependencies
     * 
     * @return List<String>, order in which all the packages have to be installed
     * @throws CycleException if you encounter a cycle in the graph
     */
    public List<String> getInstallationOrderForAllPackages() throws CycleException {
    	Set<String> pack = getAllPackages();
    	ArrayList<String> ordlist = new ArrayList<String>();
    	
    	if(pack.isEmpty())
    		return ordlist;
    	
    	for(String vert : getAllPackages()) {
    		for(String dep : graph.getAdjacentVerticesOf(vert)) {
    			pack.remove(dep);
    		}
    	}
    	
        Stack<String> newstack = new Stack<String>();
        Set<String> vert2 = getAllPackages();
        
        vert2.removeAll(pack);
        newstack.addAll(pack);
        
        while(!newstack.isEmpty()) {
          String current = newstack.peek();
          if(objDectector(current, newstack))
          	throw new CycleException();
          if(Dependency(current, vert2) == null) {
          	newstack.pop();
          	ordlist.add(current);
          } else {
          	newstack.push(Dependency(current, vert2));
          	vert2.remove(Dependency(current, vert2));
          }
        }
         
        if(ordlist.size() != getAllPackages().size())
        	throw new CycleException();
        
        return ordlist;
    }
    
    /**
     * Find and return the name of the package with the maximum number of dependencies.
     * 
     * Tip: it's not just the number of dependencies given in the json file.  
     * The number of dependencies includes the dependencies of its dependencies.  
     * But, if a package is listed in multiple places, it is only counted once.
     * 
     * Example: if A depends on B and C, and B depends on C, and C depends on D.  
     * Then,  A has 3 dependencies - B,C and D.
     * 
     * @return String, name of the package with most dependencies.
     * @throws CycleException if you encounter a cycle in the graph
     */
    public String getPackageWithMaxDependencies() throws CycleException {
    	String maxPack = null; 
    	int Size = 0;
    	
    	Set<String> Prev = getAllPackages();
    	if(Prev.isEmpty())
    		return null;
    	for(String vert : getAllPackages()) {
    		for(String dep : graph.getAdjacentVerticesOf(vert)) {
    			Prev.remove(dep);
    		}
    	}
    	if(Prev.isEmpty())
    		throw new CycleException();
        
    	for(String max : Prev) {
    		int currentSize = 0;
    		try {
    			currentSize = getInstallationOrder(max).size();
    		} catch(PackageNotFoundException e) {
    			// should never run
    		}
    		if(Size != currentSize) {
    			maxPack = max;
    			Size = currentSize;
    		}
    	}
        return maxPack;
    }

    public static void main (String [] args) {
        System.out.println("PackageManager.main()");
    }
}
