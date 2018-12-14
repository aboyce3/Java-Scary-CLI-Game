public abstract class Character{
  
  String name, description;
  Room room;
  
  
  public Character(String n, String d){
    name = n;
    description = d;
  }
  public void setRoom(Room a){
    this.room = a;        
  }
  
  public abstract void react(Item.ItemActions haunting);

  public String toString(){
    String output = "Persons Name: " + name+" Persons Description: " + description;
    return output;
  }
  
  
}
