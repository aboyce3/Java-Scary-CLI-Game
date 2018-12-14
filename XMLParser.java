import org.xml.sax.*;
import javax.xml.parsers.*;
import java.io.*;
import org.xml.sax.helpers.DefaultHandler;
import java.util.*;



public class XMLParser extends DefaultHandler {
  private HashMap<String, Room> ra;
  private HashMap<String, Item> items;
  private String stringBuffer, fileName, actions;
  private String itemn, itemd, roomn, roomd, adultn, adultd, playern, playerd, childn, childd; 
  private String north, south, east, west;
  private Item.ItemActions itemactions;
  private String name;
  private Player player;
  BST b;
  
  @Override
  public void startDocument() throws SAXException {
    ra = new HashMap<>();//Hashmap of rooms.
    items = new HashMap<>();//Hashmap of items.
    
  }
  
  @Override
  public void startElement(String namespaceURI,
                           String localName,
                           String qName, 
                           Attributes atts) throws SAXException {
    if(qName.equals("room")){
      roomn = atts.getValue("name");
      roomd = atts.getValue("description");
      north = atts.getValue("north");
      south = atts.getValue("south");
      east = atts.getValue("east");
      west = atts.getValue("west");
      ra.put(roomn, new Room(roomn, roomd));
      //Puts rooms into the ra Hashmap.
    }else if(qName.equals("item")){
      itemn = atts.getValue("name"); 
      itemd = atts.getValue("description"); 
      items.put(itemn, new Item(itemn, itemd));
      actions = atts.getValue("actions");
      String[] tokens = actions.split(",");
      //Splitting the actions.
      for (int i = 0; i < tokens.length; i++){   
        if(tokens[i] != null){
          if(tokens[i].contains("possess")){
            items.get(itemn).addAction(itemactions.POSSESS); 
            //Adding actions to items.
          }else if(tokens[i].contains("throw")){   
            items.get(itemn).addAction(itemactions.THROW);
            //Adding actions to items.   
          }else if(tokens[i].contains("shake")){
            items.get(itemn).addAction(itemactions.SHAKE);
            //Adding actions to items.    
          }     
        }
      }
      ra.get(roomn).setItem(items.get(itemn));
      //Adding items to the rooms with their actions.
      
    }else if(qName.equals("adult")){
      adultn = atts.getValue("name");
      adultd = atts.getValue("description");
      Adult adult = new Adult(adultn, adultd);
      adult.setRoom(ra.get(roomn));
      ra.get(roomn).addCharacter(adult);
      //Adds adults to the rooms and sets the adults room.
    }else if(qName.equals("player")){
      playern = atts.getValue("name");
      playerd = atts.getValue("description");
      player = new Player(playern, playerd);      
      player.setRoom(ra.get(roomn));
      ra.get(roomn).addCharacter(player);
      //Adds players to the rooms and sets the players room.
    }else if(qName.equals("child")){
      String childn = atts.getValue("name");
      String childd = atts.getValue("description");
      Child child = new Child(childn, childd);
      child.setRoom(ra.get(roomn));
      ra.get(roomn).addCharacter(child);
      //Adds children to the rooms and sets the childs room.
    }
    
  }  @Override
    public void endElement(String namespaceURI,
                           String localName,
                           String qName) throws SAXException {
    
    
  }
  
  
  public void setNorth(String s, String d){
    ra.get(s).north(ra.get(d));
    //Sets North
  }
  public void setSouth(String s, String d){
    ra.get(s).south(ra.get(d));
    //Sets South
  }
  public void setEast(String s, String d){
    ra.get(s).east(ra.get(d));
    //Sets East
  }
  public void setWest(String s, String d){
    ra.get(s).west(ra.get(d));
    //Sets West
  }
  public String printRoom(String s){
    if(!ra.get(s).equals(null)) return ra.get(s).toString();
    else return "Not a valid room";
    //Prints a specific room
  }
  
  public void setAction(String n, Item.ItemActions a){
    items.get(n).addAction(a);
    //Sets actions
  }
  
  public void addItem(String r, String i){
    ra.get(r).setItem(items.get(i));
    //Adds items
  }
  public String getRoom(String s){
    if(ra.get(s) != null) return ra.get(s).toString();
    else return "No such room exists!";
  }

  
  public void endDocument() throws SAXException {
    
  }
  
  public void callPlay(Scanner s){
    player.play(s);
  }
  
  public Map<String, Room> getMap(){
    return ra;
  }
  
  public void setBST(BST b){
    player.setTree(b);
  }
  
  public void setParser(XMLParser p){
    player.setParser(p);
  }
  
  @Override
  public void characters(char ch[], int start, int length)
    throws SAXException {
    stringBuffer = new String(ch, start, length).trim();
  }  
  
  
}
