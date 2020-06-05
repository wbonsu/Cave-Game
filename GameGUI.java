import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;
import java.io.*;

/**
 * A GUI class for our Game
 * @version Dec. 4th 2017
 * @author Tyler Wilson & Wilmot Osei-Bonsu
 */
public class GameGUI extends JFrame implements ActionListener{
  
  private JFrame mainFrame;
  private JPanel statusPanel,inventoryPanel,upperPanel,lowerPanel, actionPanel, imagePanel;
  private JButton exploreButton, travelButton, searchButton, saveButton;
  private JLabel healthStatus, locationStatus, itemsLabel,win, lose, possibleItemsLabel;
  private String currentItems = "", currentLocation = "Start", previousLocation = "";
  private Integer currentHealth = 0, numConnections=0;
  private GameDriver testGame;
  private JButton[] listOfTravelButtons = new JButton[10];
  private ArrayList<JLabel> possibleItems = new ArrayList<JLabel>();
  
  /**
   * a main method
   **/
  public static void main(String[] args) { 
    
    new GameGUI();
    
  }
  /**
   * the gui class that creates the layout of the game
   **/
  public GameGUI(){
    
    this.testGame = new GameDriver();
    Object saveValue = JOptionPane.showConfirmDialog(null, "Would you like to load the a previously saved game?","Save?", JOptionPane.YES_NO_OPTION);
    String[] objectFileName = new String[1];
    if(saveValue.equals(JOptionPane.YES_OPTION)){
      
      objectFileName[0]=JOptionPane.showInputDialog("Enter file name you would like to read: ");
      testGame.main(objectFileName);
    }
    else{
      objectFileName[0]="GetOutProperties";
      testGame.main(objectFileName);
    }
    this.currentLocation=testGame.currentLocation;
    this.currentHealth=testGame.currentHealth;
    
    //testGame.main(null);
    
    this.setLayout(new GridLayout(2,0));
    
    upperPanel = new JPanel();
    lowerPanel = new JPanel();
    statusPanel = new JPanel();
    inventoryPanel = new JPanel();
    actionPanel = new JPanel();
    imagePanel = new JPanel();
    ImageIcon startCave= new ImageIcon(getClass().getResource("cave.jpg"));
    JLabel startCaveLabel = new JLabel(startCave);
    imagePanel.add(startCaveLabel);
    
    upperPanel.setLayout(new GridLayout(0,2));
    lowerPanel.setLayout(new GridLayout(0,2));
    actionPanel.setLayout(new GridLayout(10,0));
    statusPanel.setLayout(new GridLayout(10,0));
    inventoryPanel.setLayout(new GridLayout(20,0));
    
    statusPanel.setBackground(Color.darkGray);
    inventoryPanel.setBackground(Color.PINK);
    actionPanel.setBackground(Color.CYAN);
    imagePanel.setBackground(Color.darkGray);
    
    upperPanel.add(statusPanel);
    upperPanel.add(inventoryPanel);
    lowerPanel.add(imagePanel);
    lowerPanel.add(actionPanel);
    
    //statusPanel.setBorder(new TitledBorder("Status"));
    statusPanel.setBorder(new TitledBorder("<html><font color='white'>Status</font></html>"));
    inventoryPanel.setBorder(new TitledBorder("Inventory"));
    actionPanel.setBorder(new TitledBorder("What would you like to do?"));
    
    healthStatus = new JLabel("<html><font color='white'>My Health is: " + currentHealth+"</font></html>");
    locationStatus = new JLabel("<html><font color='white'>My Location is: " + currentLocation +"</font></html>" );
    saveButton = new JButton("Save");
    statusPanel.add(healthStatus);
    statusPanel.add(locationStatus);
    for(int i=0;i<=6;i++)
      statusPanel.add(new JLabel(" "));
    statusPanel.add(saveButton);
    saveButton.addActionListener(this);
    
    itemsLabel = new JLabel("My Items are: ");
    possibleItemsLabel = new JLabel("Possible Items are: " );
    inventoryPanel.add(possibleItemsLabel);
    testGame.doCommand('a',"","");
    for(String i: testGame.listOfPossibleItems){
      this.inventoryPanel.add(new JLabel(i));
    }
    inventoryPanel.add(new JLabel("---------------------------"));
    inventoryPanel.add(itemsLabel);
    
    exploreButton = new JButton("Explore This Area \n(-1 Health)");
    travelButton = new JButton("Travel \n(-1 Health)");
    searchButton = new JButton("Search memory for item");
    actionPanel.add(searchButton);
    actionPanel.add(exploreButton);
    exploreButton.addActionListener(this);
    actionPanel.add(travelButton);
    travelButton.addActionListener(this);
    
    searchButton.addActionListener(this);
    
    this.add(upperPanel);
    this.add(lowerPanel);
    this.setSize(600, 800);
    this.setResizable(true);
    this.setVisible(true);
    
    
    
    JOptionPane.showMessageDialog(this.getContentPane(), "You open your eyes and remember almost nothing... Why is it so dark?");
    JOptionPane.showMessageDialog(this.getContentPane(), "You feel the cold hard ground and you realize you are in a cave...");
    JOptionPane.showMessageDialog(this.getContentPane(), "All I have on me is this list of items that I lost in this cave, and a list of locations... Who put me here?");
    JOptionPane.showMessageDialog(this.getContentPane(), "Hopefully I can ... Get Out");
    
  }
  
  
  public void makeTravelButtons(){
    this.numConnections = testGame.getNumConnections(currentLocation);
    testGame.doCommand('c',currentLocation,"");
    for(int i=0;i<numConnections;i++){
      this.listOfTravelButtons[i] = new JButton(testGame.listOfConnections[i]);
      
    }
    for(int b = 0; b < numConnections; b++){
      this.actionPanel.add(listOfTravelButtons[b]);
      int currentHealth = testGame.currentHealth + 1;
      //this.healthStatus.setText("My Health is: " + (currentHealth - 1));
      this.revalidate();
      this.repaint();
      listOfTravelButtons[b].addActionListener(this);
    }
    
  }
  
  /**
   * our action listener method
   **/
  public void actionPerformed(ActionEvent e){
    Object source =e.getSource();
    if(source.equals(saveButton)){
      testGame.doCommand('g',"","");
    }
    if(source.equals(exploreButton)){
      testGame.doCommand('e',currentLocation,"");
      this.currentItems = this.currentItems +"\n"+ testGame.currentItem;
      this.currentHealth = testGame.currentHealth;
      this.healthStatus.setText("<html><font color='white'>My Health is: " + currentHealth+"</font></html>");
      //this.itemsLabel.setText("My Items are: " + this.currentItems + "\n");
      for(String k: testGame.myList){
        this.inventoryPanel.add(new JLabel(k));
        
      }
      if(!testGame.myList.isEmpty())
        testGame.myList.pop();
      if(testGame.lose){
        this.upperPanel.setVisible(false);
        this.lowerPanel.setVisible(false);
        ImageIcon loseImage = new ImageIcon(getClass().getResource("fail.jpg"));
        JLabel losingLabel=new JLabel(loseImage);
        this.setLayout(new FlowLayout());
        this.add(losingLabel);
        this.getContentPane().setBackground(Color.BLACK);
        lose=new JLabel("<html><font color='white'>Maybe next time don't wake up in a cave alone...</font></html>");
        this.add(lose);
      }
      
    }
    if(source.equals(travelButton)){
      makeTravelButtons();
    }
    
    if(source.equals(searchButton)){
      String s=JOptionPane.showInputDialog("What would you like to search for?");
      if(testGame.listOfItems.contains(s))
        JOptionPane.showMessageDialog(this.getContentPane(), "Looks like I already have that!");
      else{
        testGame.doCommand('f',currentLocation,s);
        JOptionPane.showMessageDialog(this.getContentPane(), "I remember being somewhere that looked like a "+ testGame.itemLocation + "... if only I knew where that was.");
      }
      
    }
    
    for(int i=0;i<numConnections; i++){
      if(source.equals(listOfTravelButtons[i])){
        if(testGame.listOfConnections[i].equals("Den")){
          JOptionPane.showMessageDialog( this.getContentPane(),"What did I expect to find in a Den...Of Course there is a bear... I Hope I found the Gun.");
          if(!testGame.listOfItems.contains("Gun"))
            testGame.lose=true;    
        }
        if(testGame.listOfConnections[i].equals("Hut")){
          Object selectedValue = null;
          if(!testGame.listOfItems.contains("Gun")){
            selectedValue = JOptionPane.showConfirmDialog(this.getContentPane(),
                                                          "You See a Crazy Man, Do you want to fight?", "Hut Fight", JOptionPane.YES_NO_OPTION);
            
            if(selectedValue.equals(JOptionPane.YES_OPTION)){
              double chance = Math.random();
              if(chance < 1-currentHealth/10.0)
                testGame.lose = true;
            }
            else{
              JOptionPane.showMessageDialog(null, "You Out Ran The Man... And Returned To "  + this.currentLocation );
              this.currentLocation = this.previousLocation;
              this.testGame.currentHealth--;
            }
          }
        }
        
        testGame.doCommand('d',currentLocation, testGame.listOfConnections[i]);
        this.previousLocation = this.currentLocation;
        this.currentLocation = testGame.currentLocation;
        this.currentHealth = testGame.currentHealth;
        this.locationStatus.setText("<html><font color='white'>My Location is: " + currentLocation +"</font></html>");
        this.healthStatus.setText("<html><font color='white'>My Health is: " + currentHealth+"</font></html>");
        for(int k=0;k<numConnections; k++){
          listOfTravelButtons[k].setVisible(false);
          actionPanel.remove(listOfTravelButtons[k]);
        }
        if(testGame.win){
          this.upperPanel.setVisible(false);
          this.lowerPanel.setVisible(false);
          ImageIcon winImage = new ImageIcon(getClass().getResource("caveOut.jpg"));
          JLabel winningLabel = new JLabel(winImage);
          
          this.setLayout(new FlowLayout());
          this.add(winningLabel);
          win=new JLabel("<html><font color='white'>You have Gotten Out! Congrats!...Don't Come Back</font></html>");
          this.getContentPane().setBackground(Color.BLACK);
          this.add(win);
          return;
        }
        if(testGame.lose){
          this.upperPanel.setVisible(false);
          this.lowerPanel.setVisible(false);
          ImageIcon loseImage = new ImageIcon(getClass().getResource("fail.jpg"));
          JLabel losingLabel=new JLabel(loseImage);
          this.setLayout(new FlowLayout());
          this.add(losingLabel);
          this.getContentPane().setBackground(Color.BLACK);
          lose=new JLabel("<html><font color='white'>Maybe next time don't wake up in a cave alone...</font></html>");
          this.add(lose);
        }  
      }
      
    }
  }
  
  
  
  
}
