public class Queue {
    public int l,r;
    public int[] arr;

    public Queue() {
        arr = new int[20000000];
        l = 1;
        r = 0;
    }

    public void enqueue(int n) {
        arr[++r] = n;
    }

    public int dequeue() {
        if(l-1 == r){
            return -1;
        }
        return arr[l++];
    }

    public int size() {
        return r-l+1;
    }

    public boolean isEmpty() {
        if(l-1 == r){
            return true;
        }
        return false;
    }
}