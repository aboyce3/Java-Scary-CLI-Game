import java.util.Iterator;
public class Driver{
   public static void main(String[] args){
        BST<Integer> bst = new BST<Integer>();
        bst.insert(8);
        bst.insert(3);
        bst.insert(1);
        bst.insert(6);
        bst.insert(4);
        bst.insert(7);
        bst.insert(10);
        bst.insert(14);
        bst.insert(13);
        bst.printTree();
        System.out.println("9? " + bst.search(9));
        System.out.println("7? " + bst.search(7));
        bst.printInOrder();
        bst.printTree();
        Iterator i = bst.iterator();
        //for loop for the iterator
        for(i.next();i.hasNext();){ 
          try{
          System.out.println(i.next());
          }catch(Exception e){
            break;
          }
        }
        
   }
   
}
