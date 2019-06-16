import java.util.Vector;

public class Mouse {
    Vector position;
    Vector acceleration;
    Vector velocity;
    //Fitness
    float fitness;
    boolean hitWall;
    boolean gotCheese;
    DNA dna;
    int lifespan;
    //constructor
    public Mouse(Vector l, DNA dna1, int numMice){
        acceleration = new Vector();
        velocity = new Vector();
        position = l;

    }
}
