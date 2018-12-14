public abstract class Npc extends Character{  
  int scaredLevel;
  public Npc(String n, String d){
    super(n,d);
  }
  
  
  
  @Override
  public String toString(){
    String output = "Persons Name: "+name+" Persons Description: "+description+" Scared Level: "+scaredLevel;
    return output;
  }
}
