import org.xml.sax.*;
import javax.xml.parsers.*;
import java.io.*;
import org.xml.sax.helpers.DefaultHandler;
import java.util.*;



public class SecondParser extends DefaultHandler {
  private String stringBuffer, fileName;
  private String north, south, east, west, roomn;
  public Item.ItemActions itemactions;
  private String name;
  XMLParser par;
  Scanner kb;
  BST b = new BST();
  
  public SecondParser(XMLParser p){
    par = p;
  }
  @Override
  public void startDocument() throws SAXException {   
  }
  
  @Override
  public void startElement(String namespaceURI,
                           String localName,
                           String qName, 
                           Attributes atts) throws SAXException {
    if(qName.equals("room")){
      roomn = atts.getValue("name");
      north = atts.getValue("north");
      south = atts.getValue("south");
      east = atts.getValue("east");
      west = atts.getValue("west");
      if(north != null)par.setNorth(roomn, north);
      if(south != null)par.setSouth(roomn, south);
      if(east != null)par.setEast(roomn, east);
      if(west != null)par.setWest(roomn, west);
      //Sets the references equal to the objects in their respective directions.
    }
    
  }  @Override
    public void endElement(String namespaceURI,
                           String localName,
                           String qName) throws SAXException {
    
  }
  
  
  public void endDocument() throws SAXException {
    for(Room r : par.getMap().values())
      b.insert(r);
    par.setBST(b);
    par.setParser(par);
    par.callPlay(kb);  
  }
  
  @Override
  public void characters(char ch[], int start, int length)
    throws SAXException {
    stringBuffer = new String(ch, start, length).trim();
  }  
  
} 
