import java.util.ArrayList;
public class Room implements Comparable<Room>{
  
    LinkedList<Character> character = new LinkedList<>();
    //Character[] character = new Character[6];
    private String name, description;
    Room north;
    Room south;
    Room east;
    Room west; 
    XMLParser xml;
    ArrayList<Item> item = new ArrayList<>();
 
    public LinkedList<Character> getCharacters(){
 return character;
 }
    public Room(String n, String d){
 name = n;
 description = d;
    }
    public void setItem(Item i){
 item.add(i);
    }
    public void north(Room a){
 north = a;
    }
    public void south(Room a){
 south = a;
    }
    public void east(Room a){
 east = a;
    }
    public void west(Room a){
 west = a;
    }
    public String getName(){
 return name;
    }
    public String currentRoom(){
 return "\n" +"You're now in the: " + name; 
    }
  
   /* public Character[] getCharacters(){
 return character;
 }*/
    public void addCharacter(Character a){
/* for (int i = 0; i < character.length; i++){ 
   if(character[i] == null){
   character[i] = a;
   a.setRoom(this); 
   clean();
   break;
   }  
   }*/

 character.append(a);
 a.setRoom(this);
 if(a instanceof Npc) clean();
    }
  
    private void clean(){
 for(Item i : item){
     if(i.brokenItem() == true){
  item.remove(i);
     }
 }
    }
    @Override
    public int compareTo(Room roomN){
      if(this.name.compareTo(roomN.name) > 0)
        return 1;
       else if(this.name.compareTo(roomN.name) < 0)
        return -1;
       else 
         return 0;
    }
    
    public boolean hasItem(String s){
 for(int i = 0; i < item.size(); i++){
     if(item.get(i).getName().equals(s)){
  return true;
     }
 }return false;
    }
  
    public void removeCharacter(Character a){
 /*for(int i = 0; i < character.length; i++){
   if(character[i] == a)
   character[i] = null;
   }*/
  character.remove(a);
    }
    public Item getItem(String s){
 for(int i = 0; i < item.size(); i++){
     if(item.get(i).getName().equals(s)) {
  return item.get(i);
     }
      
 } return null;
    }
    public String toString(){ 
 String output = "Room Name:" + name + "\n" +"Rooms Description: " + description + "\n"; 
 if(north != null) output += "North: " + north.getName()+"\n";
 if(south != null) output += "South: " + south.getName()+"\n"; 
 if(east != null) output += "East: " + east.getName()+"\n"; 
 if(west != null) output += "West: " + west.getName()+"\n";  
 for(int i = 0; i < character.size(); i++){
     if(character.get(i) instanceof Npc) output += character.get(i).toString() + "\n";
 }
 for(int i = 0; i < item.size(); i++){
     if(item.get(i) != null) output += item.get(i).toString();
 }
 return output;
    
    }
  
}
