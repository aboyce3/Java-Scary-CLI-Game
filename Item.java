import java.util.*;
public class Item{
  int count = 0;
  private String name, description;
  public enum ItemActions{THROW,SHAKE,POSSESS};
  public Set<ItemActions> ss = new HashSet<ItemActions>();
  
  public Item(String n, String d){
    name = n;
    description = d;
    
  }
  
  
  public void addAction(ItemActions action){
    ss.add(action);
  }  
  
  public String getName(){
    return name;
  }
  
  public boolean brokenItem(){
    if (count < 1){ 
      return false;
    }
    return true;
  }
  
  public boolean hasAction(Item.ItemActions a){
    Iterator<ItemActions> itr= ss.iterator();  
    while(itr.hasNext()){  
      if(itr.next() == a) return true; 
    } return false;
  }
  
  public void increment(){
    count++;
  }
  
  public String toString(){
    String output = "Item Name: " + name + "\n" + "Item Description: " + description + "\n";
    if(brokenItem() == false){
      Iterator<ItemActions> it = ss.iterator();
      while(it.hasNext()){
        output += "Item Action: " + it.next() + "\n" ;  
      } 
    }else{
      output += "The "+name+" is broken and cannot be used." + "\n";
    }
    return output;
  }
  
  
}
