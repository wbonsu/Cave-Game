import java.util.*;
import java.io.*;
import fileChooser.*;

/**
 * a GameLayout class that allows us to create a new Game from the fils
 * iterate over all the locations, get a particular location description for a 
 * given location. update the description and write the current state of a specifiled 
 * file so that you may start a game from a saved point.
 * @version Dec. 4th 2017
 * @author Tyler Wilson & Wilmot Osei-Bonsu
 **/
@SuppressWarnings("unchecked")
public class GameLayout {
  public static String currentLocation = "", startLocation = "";
  public static int currentHealth=0;
  /** 
   * connections associates a given location label with a set of connected locations
   **/ 
  public HashMap<String, Set<String>> connections;
  
  /**
   * descriptions associates a given location with its description object
   **/
  public HashMap <String, LocationDescription> descriptions;
  
    /**
   * number of connections from each location
   **/
  public HashMap <String, Integer> numOfConnections;
  
  
  /**
   * a constructor method
   * @param connectionsFileName is the file with the connections
   * @param descriptionsFileName is the file with the description
   * @pre both files must exist
   * @post maps are made for inputs of connections and descriptions
   * @throws FileNotFoundException if the file is not found
   **/
  public GameLayout(String connectionsFileName, String descriptionsFileName){
    Scanner inputConnections =  null;
    Scanner inputDescriptions = null;
    try{
      this.connections=new HashMap<String, Set<String>>();
      this.descriptions=new HashMap <String, LocationDescription>();
      this.numOfConnections=new HashMap <String, Integer>();
      File c=new File(connectionsFileName);
      File d=new File(descriptionsFileName);
      inputConnections=new Scanner(c);
      inputDescriptions=new Scanner(d);
    }
    catch(FileNotFoundException fn){
      System.out.println("File not Found"); 
    }
    while (inputConnections.hasNext() ){
      Set setOfConnections = new HashSet<String>();
      String location = inputConnections.nextLine();
      Integer numConnections = Integer.parseInt(inputConnections.nextLine());
      this.numOfConnections.put(location, numConnections);
      while(numConnections > 0){
        setOfConnections.add(inputConnections.nextLine());
        numConnections--;
      }
      this.connections.put(location, setOfConnections);
      String remove = inputConnections.nextLine();
    }                  
    inputConnections.close();
    //this.currentLocation = inputDescriptions.nextLine();
    System.out.println();
    while (inputDescriptions.hasNext()){
      String location = inputDescriptions.nextLine();
      String item = inputDescriptions.nextLine();
      int health = Integer.parseInt(inputDescriptions.nextLine());
      startLocation = inputDescriptions.nextLine();
      currentHealth = Integer.parseInt(inputDescriptions.nextLine());
      LocationDescription descript = new LocationDescription(location, item, health, startLocation, currentHealth);
      this.descriptions.put(location,descript);
      String remove = inputDescriptions.nextLine();
    }
    this.startLocation=startLocation;
    inputDescriptions.close();
  }
  
  /**
   * an iterator method to return the locations
   * @returns an itterator of the locations
   **/
  public Iterator<String> locations(){
    return connections.keySet().iterator();
  }
  /**
   * an iterator method to return the connections at a given location
   * @param location is the location you would like to get the connections from
   * @returns an iterator of the connections at a location
   **/
  public Iterator<String> connections(String location){
    return connections.get(location).iterator();
  }
  
    /**
   * an iterator method to return the connections at a given location
   * @param location is the location you would like to get the connections from
   * @returns an iterator of the connections at a location
   **/
  public int numOfConnections(String location){
    return numOfConnections.get(location);
  }
  
  /**
   * a method to get a description from a given location
   * @param location is the location you would like to get a description of
   * @returns a location Description
   **/
  public LocationDescription getLocationDescription(String location){
    return descriptions.get(location);
  }
  /**
   * a method to add a property at a particular location
   * @param location is the location you would like to update
   **/
  public void updateItem(String location, String property){
    LocationDescription tempDescript  = descriptions.get(location);
    tempDescript.setItem(property);
    this.descriptions.put(location, tempDescript);
  }
  /**
   * a method to add a property at a particular location
   * @param location is the location you would like to update
   **/
  public void updateHealth(String location, int property){
    LocationDescription tempDescript  = descriptions.get(location);
    tempDescript.setHealthChange(property);
    this.descriptions.put(location, tempDescript);
  }
  
  /**
   * a method to write a collection to the file
   **/
  public void fileWriter(String location, int health){
    
    PrintWriter writer = FileChooser.openPrintWriter();
        //writer.print(location+"\n");
    for(Iterator<String> i = locations(); i.hasNext(); ){
      LocationDescription description = getLocationDescription(i.next());
      writer.print(description.toString(location, health));
    }

    writer.close();
  }
  /**
   * a method to search for a specific property within a Queue
   * @param location is the starting location
   * @param property is the property to search for
   * @returns a String location if it is found or null if it is not found
   **/
  public String search(String location, String property){
    Queue<String> locations = new PriorityQueue<String>();
    Queue<String> markedLocations = new PriorityQueue<String>();
    locations.add(location);
    markedLocations.add(location);
    while(!locations.isEmpty()){
      String currentLocation = locations.remove();
      LocationDescription currentLocationDescription = descriptions.get(currentLocation);
      if(currentLocationDescription.getItem().equals(property)){
        markedLocations = new PriorityQueue<String>();
        return currentLocation;
      }
      for(Iterator<String> i = connections(currentLocation); i.hasNext(); ){
        LocationDescription description = getLocationDescription(i.next());
        if(!markedLocations.contains(description.getLocation())){
        markedLocations.add(description.getLocation());
        locations.add(description.getLocation());
        }
      }
    }
    markedLocations = new PriorityQueue<String>();
    return null;
  }
  
  
  
  
  
}