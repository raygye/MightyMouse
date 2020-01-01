// This interface defines the actions associated with a list of integers.
public interface ADT{
    public void enqueue(int i);// Enqueues element to back of queue
    public int dequeue();	    // Returns element from front of queue
    public int size();			// Return the number of elements in the queue.
    public boolean isEmpty();	// Return true if the queue is empty, otherwise return false.
}