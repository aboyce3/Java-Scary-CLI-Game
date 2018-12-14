import java.util.Random;
public class Child extends Npc{
  Random r = new Random();
  int roomChange = r.nextInt(4);
  public Child(String n, String d){
    super(n,d);
  }
  
  
  private void runAway(){
    int roomChange = r.nextInt(4);
    if(scaredLevel < 50){
      System.out.println(name + ": " + "shrieked" +"\n" + "Scared Level: " + scaredLevel);
    }
    
    if(scaredLevel >= 50 && scaredLevel < 100){
      
      if(roomChange == 0){ 
        if(room.north != null){
          System.out.println(name + " shrieked three times and ran out of the room, don't let them get away!" +"\n" + "Scared Level: " + scaredLevel);
          room.north.addCharacter(this);
          room.south.removeCharacter(this); 
        }else runAway();
      }if(roomChange == 1){
        if(room.south != null){
          System.out.println(name + " shrieked three times and ran out of the room, don't let them get away!"+"\n" + "Scared Level: " + scaredLevel);
          room.south.addCharacter(this);
          room.north.removeCharacter(this); 
        }else runAway();
      }
      if(roomChange == 2){
        if(room.east != null){
          System.out.println(name + " shrieked three times and ran out of the room, don't let them get away!" +"\n" + "Scared Level: " + scaredLevel);
          room.east.addCharacter(this);
          room.west.removeCharacter(this); 
        }else runAway();
        
      }if(roomChange == 3){
        if(room.west != null){
          System.out.println(name + " shrieked three times and ran out of the room, don't let them get away!"+"\n" + "Scared Level: " + scaredLevel); 
          room.west.addCharacter(this);
          room.east.removeCharacter(this);
        }else runAway();
      }    
    }
    
    if(scaredLevel >= 100){
      System.out.println(name + " shried extra loud and ran out of the house, you're a pretty spooky ghoul.");
      room.removeCharacter(this);
      Player.addTime();
      Player.elimNpc();
    }
    
  }
  
  @Override
  public void react(Item.ItemActions haunting){
    if(haunting == Item.ItemActions.POSSESS){
      scaredLevel = (int) Math.round((this.scaredLevel + (r.nextInt(25-10) + 10)*1.5));
      runAway();
    }else if(haunting == Item.ItemActions.THROW){
      scaredLevel =  (int) Math.round(this.scaredLevel + (this.scaredLevel + (r.nextInt(40-20) + 20)*1.5));
      runAway();
    }else if(haunting == Item.ItemActions.SHAKE){
      scaredLevel = (int) Math.round(this.scaredLevel + (this.scaredLevel + (r.nextInt(15-5) + 15)*1.5));
      runAway();
    }
  }
}




//shaking adds a random value between 5 and 15; possessing adds a random value between 10 and 25; throwing adds a random
//value between 20 and 40.  Children have their values increased at 1.5x the rate of adults.
