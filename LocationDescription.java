/*
 * File: BookDescription.java
 */

/**
 * A Location Description Class for our get out game
 * @Author Tyler Wilson and Wilmot Osei-bonsu
 * @Version 11/12/2017
 */
public class LocationDescription {
  /**
   * location is the title for a given location
   * item is a item found at a location
   **/
  public String location, item, startLocation;
  /**
   * healthChange is the amount your health is changed at this location
   **/
  public int healthChange, currentHealth;
  
  /**
   * a setter method for the location
   * @param location is the name of the location
   **/
  public void setLocation(String location){
    this.location=location;
  }
  /**
   * a getter method for the location
   * @returns the location
   **/
  public String getLocation(){
    return this.location;
  }
  
  public void setStart(String startLocation){
    this.startLocation=startLocation;
  }
  
  
  public String getStart(){
    return this.startLocation;
  }
  
  public void setCurrentHealth(int health){
    this.currentHealth = health;
  }
  
  public int getCurrentHealth(){
    return this.currentHealth;
  }
  /**
   * a setter method for an item
   * @param item is the item at the location
   **/
  public void setItem(String item){
    this.item=item;
  }
  /**
   * a getter method for an item
   * @returns the item at the location
   **/
  public String getItem(){
    return this.item;
  }
  /**
   * a setter method for the HealthChange at a location
   * @param healthChange is the amount of health gained or lost at this location
   **/
  public void setHealthChange(int healthChange){
    this.healthChange=healthChange;
  }
  /**
   * a getter method for the healthChange
   * @returns the health changed at this location
   **/
  public int getHealthChange(){
    return this.healthChange;
  }
  /**
   * a toString method to return the values in a preformatted way
   * @returns a toString
   **/
  public String toString(String currentLocation, int currentHealth){
    return this.location + "\n" + this.item + "\n" + this.healthChange + "\n"+ currentLocation + "\n"+currentHealth+"\n_\n";
  }
  /**
   * a constructor method
   * @param location is the location value
   * @param item is the item at the location
   * @param healthChange is the amount of health changed at the location
   **/
  public LocationDescription(String location, String item, int healthChange, String startLocation, int currentHealth){
    this.location=location;
    this.item=item;
    this.healthChange=healthChange;
    this.startLocation = startLocation;
    this.currentHealth = currentHealth;
  }

}
