/**
 * Auto Generated Java Class.
 */
public class TestingGameDriver {
  
  
  public static void main(String[] args) { 
    GameLayout testGameLayout = new GameLayout("GetOutConnections", "GetOutProperties");
    System.out.println("******************************************************************************************");
    System.out.println(testGameLayout.connections);
    System.out.println("******************************************************************************************");
    System.out.println(testGameLayout.descriptions);
    System.out.println("******************************************************************************************");
    System.out.println(testGameLayout.getLocationDescription("Waterfall"));
    System.out.println("******************************************************************************************");
    //testGameLayout.fileWriter();
    testGameLayout.updateItem("Waterfall","Knife");
    testGameLayout.updateHealth("Waterfall",.6);
    System.out.println(testGameLayout.getLocationDescription("Waterfall"));
    System.out.println("******************************************************************************************");
    //testGameLayout.fileWriter();
    System.out.println("******************************************************************************************");
    System.out.println(testGameLayout.search("Start","Bear"));
    
    
    
    
  }
  
}
