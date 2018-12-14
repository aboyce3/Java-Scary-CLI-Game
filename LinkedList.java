import java.util.Iterator;
import java.lang.IndexOutOfBoundsException;
 
public class LinkedList<T> implements Iterable<T> {
 
    private class Node<T> {
        T data;
        Node<T> next;
 
        public Node(T data){
            this.data = data;
        }
    
        public Node(T data, Node<T> next){
            this(data);
            this.next = next;
        }
 
        public String toString(){
            return "" + data;
        }
    }
    
    public class LLIterator<U> implements Iterator<U> {
        LinkedList<U> ll;
        LinkedList<U>.Node<U> curr;
 
        public LLIterator(LinkedList<U> ll){
            this.ll = ll;
            curr = null;
        }
 
        public boolean hasNext(){
            if (curr == ll.tail && ll.tail != null) return false;
            return true;
        }
        
        public U next(){
          if (curr == null)
            curr = ll.head;
          else curr = curr.next;
          return curr.data;
        }
    }
 
    private Node<T> head, tail;
    private int count;
 
    public LinkedList(){
        count = 0;
    }
    
    public void prepend (T i){
 if (count == 0){
     head = tail = new Node<T>(i);
 }
 else {
     head = new Node<T>(i, head);
 }
 count++;
    }
    
    public void insert(T data, int index){
 if(index < 0 || index > count){
     throw new IndexOutOfBoundsException("Index out of bounds!");
 }
        if(!isEmpty() && index == 0){
            head = new Node(data, head);
        } else {
            insertHelper(data, index-1, head);
        }
        count++;
    }

    private void insertHelper(T data, int index, Node current){
        if(index == 0){
            current.next = new Node(data, current.next);
        } else {
            insertHelper(data, index -1, current.next);
        }
    }
    

    public boolean exists(T data){
 Node<T> temp = head;
 while(temp != null){
     if(temp.data == data){
  return true;
     }
     temp = temp.next;
 }
 return false;
    }

    public void removeHelper(T data, int index, Node current){
 if(index == 0){
            current.next = current.next.next;
 }else {
            removeHelper(data, index -1, current.next);
        }
    }
    
    public boolean remove(T data) {
 if(head.data == data && tail.data == data){
     head = null;
     tail = null;
     count--;
     return true;
 }else if(head == null){
     return false;
        }else if(head.data == data){
     head = head.next;
     count--;
     return true;
        }else if(head.next.data == data){
     tail = head;
     count--;
     return true;
 }
    
 for(int i = 0; i < size(); i++){
     if(get(i) == data){
  removeHelper(get(i), i-1, head);
  count--;
  return true;
     }
 } return false;
    }


    public T get(int index){
 Node<T> temp = head;
 for (int i = 0; i < index; i++){
     temp = temp.next;
 }
 return temp.data;
    }

   


    public void append(T i){
        if (size() == 0){
            head = tail = new Node<T>(i);
        }
        else {
            tail = tail.next = new Node<T>(i);
        }
        count++;
    }
    
    
    public int size(){
        return count;
    }
 
    public boolean isEmpty(){
        return head == null;
    }
 
    public String toString() {
        String retVal = "Linked list with " + count + " elements\nNodes:";
        
 
        for (Node<T> temp = head; temp != null; temp = temp.next)
            retVal += temp + " ";
        
        return retVal;
    }
 
    public Iterator<T> iterator(){
        return new LLIterator<T>(this);
    }
}
