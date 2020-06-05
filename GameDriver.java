import javax.swing.*;
import java.util.*;
import java.io.*;
import fileChooser.*;

/**
 * A Gui Class
 * @Version Dec 6, 2017
 * @author Tyler Wilson & Wilmot Osei-Bonsu
 */
public class GameDriver {
  /** the current location the player is in**/
  public static String currentLocation="",itemLocation = " ";
  public static int currentHealth = 5;
  public static String currentItem = "";
  protected static boolean win = false, lose=false;
  protected static String[] listOfConnections = new String[64];
  protected static ArrayList<String> listOfItems = new ArrayList<String>();
  protected static ArrayList<String> listOfPossibleItems = new ArrayList<String>();
  protected static Stack<String> myList = new Stack<String>();
  /**
   * the gameLayout we are using
   **/
  public static GameLayout testGameLayout;
  
  /**
   * the main method that creates the game and culls the run manager method
   **/
  public static void main(String[] args) {
    //if(!args.equals(null))
    testGameLayout = new GameLayout("GetOutConnections", args[0]);
    //testGameLayout = new GameLayout("GetOutConnections", "GetOutProperties");
    
    currentLocation = testGameLayout.startLocation;
    currentHealth = testGameLayout.currentHealth;
    
  }
  
  /**
   * a method to run the command from a user
   * @param cmd is the command recieved from accept command
   **/
  protected static void doCommand(char cmd, String location, String destination) {
    
    switch (cmd)
    {
      case 'a': case 'A':
        
        for(Iterator<String> i = testGameLayout.locations(); i.hasNext(); ){
        LocationDescription description = testGameLayout.getLocationDescription(i.next());
        if(!description.getItem().equals("X"))
          listOfPossibleItems.add(description.getItem());
        
      }
        break; 
      case 'b': case 'B':
        System.out.println(testGameLayout.getLocationDescription(location));
        break;
      case 'c': case 'C':
        int count = 0;
        for(Iterator<String> i = testGameLayout.connections(location); i.hasNext(); ){
          LocationDescription description = testGameLayout.getLocationDescription(i.next());
          listOfConnections[count++]=description.getLocation();   
        }
        break;
      case 'd': case 'D':
        Boolean found = false;
        String newLocation = destination;
        for(Iterator<String> i = testGameLayout.connections(location); i.hasNext(); ){
          LocationDescription description = testGameLayout.getLocationDescription(i.next());
          String possibleLocation = description.getLocation();
          if(possibleLocation.equals(newLocation)){
            currentLocation = newLocation;
            //currentHealth+=description.getHealthChange();
//            if(currentHealth>10)
//              currentHealth=10;
            if(currentLocation.equals("Out"))
              win=true;
            currentHealth--;
            if(currentHealth==0 || getNumConnections(currentLocation)==0)
              lose=true;
            return;
          }
        }        
        //System.out.println("Sorry that is not an option.");
        break;
      case 'e': case 'E':
        LocationDescription description = testGameLayout.getLocationDescription(currentLocation);
        if(!description.getItem().equals("X")){
          listOfItems.add(description.getItem());
          myList.push(description.getItem());
          description.setItem("X");
          
          currentHealth+=description.getHealthChange();
          if(currentHealth>10)
            currentHealth=10;
        }
        currentHealth--;
        if(currentHealth==0)
          lose=true;
        break; 
        
      case 'f': case 'F':
        String property = destination;
        itemLocation = testGameLayout.search(currentLocation, property);
        break;
      case 'g': case 'G':
//        PrintWriter writer = FileChooser.openPrintWriter();
//        for(Iterator<String> i = testGameLayout.locations(); i.hasNext(); ){
//          LocationDescription description = testGameLayout.getLocationDescription(i.next());
//          for(Iterator<String> title = titles(author); title.hasNext(); ){
//            writer.print(library.get(author).get(title.next()).toString() + "\n" + "_" + "\n");
//          }
//        }
//        writer.close();
        testGameLayout.fileWriter(currentLocation, currentHealth);
        break;
      case 'q': case 'Q':
        break;
      default:
    }
  }
  
  protected static int getNumConnections(String location){
    return testGameLayout.numOfConnections(location);    
  }
  
//  private static void bearFight(){
//    GameGUI gui = new GameGUI();
//    System.out.println(gui.myItems);
//    if(!listOfItems.contains("Gun"))
//      lose=true;    
//  }
  
  
  
  
  
}
