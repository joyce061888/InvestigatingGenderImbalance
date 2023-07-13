package javafoundations;
 /**
 * PriorityQueue.java that represents a priority queue using a LinkedMaxHeap.
 * 
 * @author Joyce, Malahim, Candice
 * @version (05/08/2022)
 */
public class PriorityQueue<T extends Comparable<T>> implements Queue<T>
{
    private LinkedMaxHeap<T> heap;
    private int count;
    
    /**
     * Constructor. Creates a new priority queue.
     */
    public PriorityQueue() {
        heap = new LinkedMaxHeap<T>();
        count = 0;
    }

    /**
     * Adds the specified element to the rear of the queue based on priority.
     * 
     * @param element to add to the queue
     */  
    public void enqueue(T element) {
        heap.add(element);
        count++;
    }

    /**
     * Removes and returns the element at the front of the queue 
     * that has the highest priority.
     * 
     * @return element with the highest priority.
     */ 
    public T dequeue() {
        T elt = heap.removeMax();
        count--;
        return elt;
    }

    /**
     * Returns a reference to the element at the front of the queue 
     * with the highest priority without removing it.
     * 
     * @return element with the highest priority.
     */
    public T first() {
        return heap.getMax();
    }

    /**
     * Returns true if this priority queue contains no elements and false
     * otherwise.
     * 
     * @return true if this priority queue contains no elements and false
     * otherwise.
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Returns the number of elements in this priority queue.
     * 
     * @return the number of elements in this priority queue.
     */
    public int size() {
        return count;
    }

    /**
     * Returns a string representation of this priority queue.
     * 
     * @return a string representation of this priority queue.
     */
    public String toString() {
        String result = "";
        PriorityQueue<T> tempQ = new PriorityQueue<T>();
        
        while(!this.isEmpty()) {
            //dequeue from this collection into the temporary collection
            T movie = this.dequeue();
            tempQ.enqueue(movie);
            
            result += movie + "\n";
        }
        
        //put Movies back into this queue from the temporary queue
        while(!tempQ.isEmpty()) {
            T film = tempQ.dequeue();
            this.enqueue(film);
        }
        return result;
    }
    
    /**
     * Main method for testing purposes.
     */
    public static void main​(String[] args) {
        PriorityQueue<Double> q = new PriorityQueue<Double>();

        q.enqueue(1.0);
        q.enqueue(2.5);
        q.enqueue(0.0);
        q.enqueue(4.5);
        q.enqueue(4.5);
        q.enqueue(-1.0);
        
        System.out.println(q);
        System.out.println();
        
        q.dequeue();
        q.dequeue();
        
        System.out.println(q);
        System.out.println();
        
        System.out.println("Queue size: " + q.size());
        System.out.println("Queue should not be empty (expecting false): " + q.isEmpty());
        System.out.println("Current first: " + q.first());
        
        q.dequeue();
        q.dequeue();
        q.dequeue();
        q.dequeue();
        
        System.out.println(q);
        System.out.println();
        
        System.out.println("Queue size: " + q.size());
        System.out.println("Queue should be empty (expecting true): " + q.isEmpty());
        
    }
}