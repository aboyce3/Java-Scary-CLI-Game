import java.util.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import java.io.*;
import org.xml.sax.helpers.DefaultHandler;
import java.util.Scanner;

public class Main{
  
  public static void main(String[] args){
    Scanner kb = new Scanner(System.in);
    System.out.println("What is the name of your file or type quit:");
    String fileName = kb.nextLine();
    if(!new File(fileName).exists()){
      System.out.println("Invalid file name, try again or type quit: ");
      fileName = kb.nextLine();
    }if(fileName.equals("quit")){
        System.out.println("Thanks for playing!"); 
        System.exit(0);
      }
    System.out.println("\n");
    SAXParserFactory spf = SAXParserFactory.newInstance();
    XMLParser sxp = new XMLParser();
    SecondParser xp;
    try {
      InputStream xmlInput  = new FileInputStream(fileName);
      SAXParser saxParser = spf.newSAXParser();
      saxParser.parse(xmlInput, sxp); 
    } catch(SAXException|ParserConfigurationException|IOException e){
      e.printStackTrace(); 
    }
    
    SAXParserFactory spq = SAXParserFactory.newInstance();
    try {   
      InputStream xmlInputSecond  = new FileInputStream(fileName);
      SAXParser saxParser = spq.newSAXParser();
      xp = new SecondParser(sxp);
      saxParser.parse(xmlInputSecond, xp); 
    } catch(SAXException|ParserConfigurationException|IOException e){
      e.printStackTrace();
    }
  }
}
