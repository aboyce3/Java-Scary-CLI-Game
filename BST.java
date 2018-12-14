import java.util.ArrayList;
import java.util.Stack;
import java.util.Iterator;
class BST<T extends Comparable<T>> implements Iterable<T>{
  
  private class BSTNode<T> {
    T value;
    BSTNode<T> left;
    BSTNode<T> right;
    
    public BSTNode(T value){
      this.value = value;
    }
    
    private StringBuilder toString(StringBuilder prefix, boolean isTail, StringBuilder sb) {
      if(right!=null) {
        right.toString(new StringBuilder().append(prefix).append(isTail ? "?   " : "    "), false, sb);
      }
      sb.append(prefix).append(isTail ? "??? " : "??? ").append(value).append("\n");
      if(left!=null) {
        left.toString(new StringBuilder().append(prefix).append(isTail ? "    " : "?   "), true, sb);
      }
      return sb;
    }
    
    @Override
    public String toString() {
      return this.toString(new StringBuilder(), true, new StringBuilder()).toString();
    }
  }
  
  private BSTNode<T> root;
  
  private BSTNode<T> insert(BSTNode<T> curr, T val){
    if (curr == null)
      return new BSTNode(val);
    if (curr.value.compareTo(val) < 0)
      curr.right = insert(curr.right, val);
    else if (curr.value.compareTo(val) > 0)
      curr.left = insert(curr.left, val); 
    return curr;
  }
  
  public void insert(T value){
    root = insert(root, value);
  }
  
  private T search(BSTNode<T> curr, T val){
    if (curr == null || val == null){
      return null;
    }
    if (curr.value == val){
      return curr.value;
    }
    if (curr.value.compareTo(val) < 0)
      return search(curr.right, val);
    return search(curr.left, val);
  }
  
  public T search(T value){
    return search(root, value);
  }
  
  private void printInOrder(BSTNode<T> curr){
    if (curr != null) {
      // Print everything in left subtree
      printInOrder(curr.left);
      // Print curr.value
      System.out.print(curr.value + "\n");
      // Print everything in right subtree
      printInOrder(curr.right);
    }
  }
  
  
  public void printInOrder(){
    printInOrder(root);
    System.out.println();
  }
  
  public class BSTIterator<U> implements Iterator<T> {
    BST<T> bst;
    BST<T>.BSTNode<T> curr;
    Stack<BST<T>.BSTNode<T>> stack = new Stack<>();
    
    public BSTIterator(BST<T> bst){
      this.bst = bst;
      curr = this.bst.root;
    }
    
    public void traverse(){
    while(curr != null){
        stack.push(curr);
        curr = curr.left;
      }
    }
    
    @Override
    public T next(){
      traverse();
      BSTNode<T> temp = stack.pop();
      curr = temp;
      curr = curr.right;
      return temp.value;
    }
    
    public boolean hasNext(){
      if (!stack.empty() && curr != null) return true;
      return false;
    }
    
  }
  
    
  public Iterator<T> iterator(){
    return new BSTIterator<T>(this);
  }
  
  public void printTree(){
    System.out.println(root.toString());
  }
  
}