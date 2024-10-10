import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int totalItems;

    private class Node{
        Node next;
        Item item;
    }

    private class DequeIterator implements Iterator<Item>{
        private Node curNode = first;
        @Override
        public boolean hasNext() {
            return this.curNode != null;
        }

        @Override
        public Item next() {
            this.curNode = this.curNode.next;
            return this.curNode.item;
        }
        
    }  
    // construct an empty deque
    public Deque(){
        this.first = null;
        this.last = null;
        this.totalItems = 0;
    }

    // is the deque empty?
    public boolean isEmpty(){
        return this.first == null;
    }

    // return the number of items on the deque
    public int size(){
        return this.totalItems;
    }

    // add the item to the front
    public void addFirst(Item item){
        if(item == null)
            throw new IllegalArgumentException();
        Node oldNode = this.first;
        this.first = new Node();
        this.first.item = item;
        this.first.next = oldNode;
        this.totalItems++;
        if(this.last == null)
            this.last = this.first;

    }

    // add the item to the back
    public void addLast(Item item){
        if(item == null)
            throw new IllegalArgumentException();
        Node oldNode = this.last;
        this.last = new Node();
        this.last.item = item;
        this.last.next = null;
        this.totalItems++;
        if(oldNode == null)
            this.first = this.last;
        else
            oldNode.next = this.last;
    }

    // remove and return the item from the front
    public Item removeFirst(){
        if(this.first == null)
            throw new NoSuchElementException();
        this.totalItems--;
        Item item = this.first.item;
        this.first = this.first.next;
        if(this.first == null)
            this.last = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast(){
        if(this.first == null)
            throw new NoSuchElementException();
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args)
    {
        System.out.print("Hello World");
    }
}