import java.util.*;
import java.io.*;
import javax.sound.sampled.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Player extends Character{
  private String command, command2, command3;
  BST b;
  XMLParser p;
  static Clip song;
  BackgroundMusic t;
  Scanner sc2 = new Scanner(System.in);
  
public class BackgroundMusic extends Thread{
    private String songName;
    
    public BackgroundMusic(String s){
      songName = s;
    }
    public void run(){
        playSong(new File(songName));
    }
  }
  
  public Player(String n, String d){
    super(n,d);
  }
  
  private static Timer timer;
  private static AtomicInteger timeLeft;
  private static int ppl = 5;
  
  public void setParser(XMLParser par){
    p = par;
  }
  
  public static void elimNpc(){
    ppl--;
  }
  
  public void initTimer(int secs) {
    timeLeft = new AtomicInteger(secs);
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        int tl = timeLeft.decrementAndGet();
        if (tl == 0) {
         System.out.println("Sorry, you lost!");
         System.out.println("Thanks for playing!");
         System.exit(0);
        }
      }
    };
    
    timer = new Timer();
    timer.schedule(task, 0, 1000);
  }
  
  public static void addTime(){
    timeLeft.set(timeLeft.intValue() + 30);
  }
  
  public static void infinite(){
    int inf = (int)Double.POSITIVE_INFINITY;
    timeLeft.set(timeLeft.intValue() + inf);
  }
  
  public AtomicInteger getTime(){
    return timeLeft;
  }
  
  
  public void setTree(BST t){
    b=t;
  }
  
  public static void playSong(File f){
    try{
      song = AudioSystem.getClip();
      song.open(AudioSystem.getAudioInputStream(f));
      song.start();
    }catch(Exception e){
      System.out.println("File doesn't exist!");
    }
  }
  
  public void play(Scanner s){
    boolean cheatmode = false;
    s = new Scanner(System.in);
    t = new BackgroundMusic("Intro.WAV");
    t.start();
    System.out.println("Welcome to the game!");
    System.out.println("You have 60 seconds to scare everyone out of the house.");
    System.out.println("If you scare someone out of the house then your timer is extended by 30 seconds.");
    System.out.println("Press enter to start, if you dare:");
    command2 = s.nextLine();
    initTimer(60);
    if(command2 != null && !command2.equals("quit")){
      System.out.println("Please enter a command or type 'help':");
      command = s.nextLine();
    while(!command.equals("quit") && ppl != 0){
      if(command.equals("help")){
        System.out.print("\n");
        System.out.println("The commands are: "+ "\n" + "look(view the room that you're in)" + "\n" + "north, south, east, or west(Change rooms)" + "\n" + "shake:[itemname], possess:[itemname], throw:[itemname]" + "\n" + "quit" + "\n");
        System.out.println("You have " + getTime() + " remaining.");
        System.out.println("Please enter a command or 'quit':");
        command = s.nextLine();
      }else if(command.equals("cheatmode")){
        cheatmode = true;
        System.out.println("Cheat mode activated!");
        System.out.println("Please enter a command or 'quit':");
        command = s.nextLine();
      }
      else if(cheatmode == true && command.equals("win")){
      ppl = 0;
      }
      else if(cheatmode == true && command.contains("look:")){
        String[] temp = command.split(":");
        if(temp[1].equals("all")){
          b.printInOrder();
        }else{
          Room foundRoom = (Room) b.search(p.getMap().get(temp[1]));
          if(foundRoom!=null) System.out.println(foundRoom);
          else System.out.println("No room found!");
        }
        System.out.println("Please enter a command or 'quit':");
        command = s.nextLine();
      }else if(command.equals("nocheatmode")){
        cheatmode = false;
        System.out.println("Cheat mode deactivated!");
        System.out.println("Please enter a command or 'quit':");
        command = s.nextLine();
      }else if(command.equals("look")){
        System.out.print("\n");
        System.out.println(this.room.toString());
        System.out.println("You have " + getTime() + " remaining.");
        System.out.println("Please enter a command or 'quit':");
        command = s.nextLine();
      }else if(command.contains("possess:")){
        System.out.print("\n");
        String[] arr = command.split(":");
        if(room.hasItem(arr[1])){
          Item i = room.getItem(arr[1]);
          if(i.hasAction(Item.ItemActions.POSSESS) && i.brokenItem() == false){
            for(Character c : room.getCharacters()){
              if (c instanceof Npc)c.react(Item.ItemActions.POSSESS);
            }
          }else System.out.println("Unfortunately you broke this item and it cannot be used again");
        }
        System.out.println("You have " + getTime() + " remaining.");
        System.out.println("Please enter a command or 'quit':");
        command = s.nextLine();
      }else if(command.contains("throw:")){
        System.out.print("\n");
        String[] arr = command.split(":");
        if(room.hasItem(arr[1])){
          Item i = room.getItem(arr[1]);
          if(i.hasAction(Item.ItemActions.THROW) && i.brokenItem() == false){
            for(Character c : room.getCharacters()){
              if(c instanceof Npc){  
                c.react(Item.ItemActions.THROW);
                i.increment(); 
              }
            }
          }else System.out.println("Unfortunately you broke this item and it cannot be used again"); 
        }
        else System.out.println("Can't be thrown");
        System.out.println("You have " + getTime() + " remaining.");
        System.out.println("Please enter a command or 'quit':");
        command = s.nextLine();
      }else if(command.contains("shake:")){
        System.out.print("\n");
        String[] arr = command.split(":");
        if(room.hasItem(arr[1])){
          Item i = room.getItem(arr[1]);
          if(i.hasAction(Item.ItemActions.SHAKE) && i.brokenItem() == false){
            for(Character c : room.getCharacters()){
              if(c instanceof Npc) c.react(Item.ItemActions.SHAKE);     
            }
          }else System.out.println("Unfortunately you broke this item and it cannot be used again");
        } 
        System.out.println("You have " + getTime() + " remaining.");
        System.out.println("Please enter a command or 'quit':");
        command = s.nextLine();
        
      }
      else if(command.equals("north")){
        if(room.north != null){
          room.north.addCharacter(this);
          room.south.removeCharacter(this);
          System.out.println(room.currentRoom());
        }
        else{
          System.out.println("\n" + "There isn't a room in that direction :(");  
        }
        System.out.println("You have " + getTime() + " remaining.");
        System.out.println("Please enter a command or 'quit':");
        command = s.nextLine();
      }
      else if(command.equals("south")){
        if(room.south != null){
          room.south.addCharacter(this);
          room.north.removeCharacter(this);
          System.out.println(room.currentRoom());
        }else{
          System.out.println("There isn't a room in that direction :(");  
        }
        System.out.println("You have " + getTime() + " remaining.");
        System.out.println("Please enter a command or 'quit':");
        command = s.nextLine();
      }
      else if(command.equals("east")){
        if(this.room.east != null){
          room.east.addCharacter(this);
          room.west.removeCharacter(this);
          System.out.println(room.currentRoom());
        }else{
          System.out.println("There isn't a room in that direction :(");  
        }
        System.out.println("You have " + getTime() + " remaining.");
        System.out.println("Please enter a command or 'quit':");
        command = s.nextLine();
      }
      else if(command.equals("west")){
        if(this.room.west != null){
          room.west.addCharacter(this);
          room.east.removeCharacter(this);
          System.out.println(room.currentRoom());
        }
        else{
          System.out.println("\n");
          System.out.println("There isn't a room in that direction :(");  
        }
        System.out.println("You have " + getTime() + " remaining.");
        System.out.println("Please enter a command or 'quit':");
        command = s.nextLine();
      }else{
        System.out.println("Invalid Command :(");
        System.out.println("You have " + getTime() + " remaining.");
        System.out.println("Please enter a command or 'quit':");
        command = s.nextLine();
      }
    }
    }

    if(ppl == 0){
      song.stop();
      t.stop();
      BackgroundMusic bm = new BackgroundMusic("Winning.wav");
      bm.start();
      infinite();
      System.out.println(" /\\_/\\    /\\_/\\    /\\_/\\    /\\_/\\");
      System.out.println("/ o o \\  / o o \\  / o o \\  / o o \\");
      System.out.println("You WIN! Congrats you scary ghoul!");
      System.out.println("Credits: Andrew Boyce CSC 241");
      System.out.println("Press enter to exit the game!");
      s.nextLine();
      System.exit(0);
    }
    System.out.println("Thanks for playing!");
    System.exit(0);
  } 
  public void react(Item.ItemActions haunting){}
}
