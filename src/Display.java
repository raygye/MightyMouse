import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Display extends Application {
    //arrays of wall bounds
    private final double [] wallsLeft = new double[4];
    private final double [] wallsRight = new double[4];
    private final double [] wallsUp = new double[4];
    private final double [] wallsDown = new double [4];
    //Maze Walls
    Rectangle rects [] = new Rectangle[4];
    Rectangle wall1 = new Rectangle(300, 0, 50, 400);
    Rectangle wall2 = new Rectangle(450, 250, 50, 350);
    Rectangle wall3 = new Rectangle(600, 0, 50, 300);
    Rectangle wall4 = new Rectangle(750, 200, 50, 400);
    //timer
    long oldTime;
    AnimationTimer timer;
    // visited matrix (dfs)
    private boolean [][] visited = new boolean [1005][1005];
    //move order: down, right, up, left
    private static int[][] moves = {{0,10},{10,0},{0,-10},{-10,0}};
    //steps
    Pair [] steps = new Pair[10000];
    int counter = 0;
    int cur = -1;
    long prevWakeup;
    //success
    boolean success = false;
    @Override
    public void start(Stage primaryStage) throws Exception {
        rects[0] = wall1;
        rects[1] = wall2;
        rects[2] = wall3;
        rects[3] = wall4;
        Group group = new Group();
        Scene scene = new Scene(group, 1000, 600);
        //Success Circle
        Circle confirm = new Circle (950, 50, 30, Color.RED);
        Circle cheese = new Circle(900, 300, 10, Color.YELLOW);
        //Setting color to the scene
        scene.setFill(Color.WHITE);
        //Setting the title to Stage.
        primaryStage.setTitle("Mighty Mice");
        //Adding the scene to Stage
        primaryStage.setScene(scene);
        //Wall Bounds
        for (int i = 0; i<4; i++){
            wallsLeft[i] = rects[i].getX();
            wallsRight[i] = rects[i].getX()+50;
            wallsDown[i] = rects[i].getY()+rects[i].getHeight();
            wallsUp[i] = rects[i].getY();
        }
        //Info Text
        String info = "";
        Text text = new Text(info);
        text.setX(650);
        text.setY(100);
        //Mouse
        Circle mouse = new Circle(200, 300, 10, Color.DARKGRAY);
        //Displaying the contents of the stage
        group.getChildren().addAll(wall1, wall2, wall3, wall4, cheese, mouse, confirm);
        primaryStage.show();
        //timer
        group.getChildren().clear();
        group.getChildren().addAll(wall1, wall2, wall3, wall4, cheese, mouse, confirm, text);
        //Keyboard Input
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event){
                if (event.getCode() == KeyCode.UP) {
                    System.out.println("UP key pressed.");
                    moveUp();
                }
                if (event.getCode() == KeyCode.DOWN){
                    System.out.println("DOWN key pressed.");
                    moveDown();
                }
                if (event.getCode() == KeyCode.LEFT){
                    System.out.println("LEFT key pressed.");
                    moveLeft();
                }
                if (event.getCode() == KeyCode.RIGHT){
                    System.out.println("RIGHT key pressed.");
                    moveRight();
                }
                if (event.getCode() == KeyCode.S){
                    text.setText(toString());
                }
                if (event.getCode() == KeyCode.ENTER){
                    dfs((int)mouse.getCenterX(), (int)mouse.getCenterY());
                    cur = 0;
                    System.out.println("ENTER key pressed.");
                }
                //color indicates whether the mouse has the cheese
                if (mouse.getCenterX()==cheese.getCenterX()&&mouse.getCenterY()==cheese.getCenterY()){
                    confirm.setFill(Color.GREEN);
                    success = true;
                }
                else{
                    confirm.setFill(Color.RED);
                    success = false;
                }

                System.out.println(mouse.getCenterX() + "," + mouse.getCenterY());
            }

            public boolean dfs(int x, int y){
                System.out.println(x + " " + y);
                if(x==(int)cheese.getCenterX()&&y==(int)cheese.getCenterY()){
                    return false;
                }
                visited[x][y] = true;
                for(int i = 0; i<4; i++){
                    int nextX = x+moves[i][0], nextY = y+moves[i][1];
                    if(valid(nextX, nextY) && !visited[nextX] [nextY]){
                        steps[counter] = new Pair(nextX, nextY);
                        counter++;
                        if(dfs(nextX, nextY)){
                            return true;
                        }
                    }
                }
                return false;
            }
            //displays steps, coordinates, and success confirmation
            public String toString(){
                String info = "Steps" + cur + " Mouse Coordinates: " + mouse.getCenterX() + ", " + mouse.getCenterY()+ ", " +success;
                return info;
            }
            //if invalid move by user, movement will be corrected
            public void moveUp(){
                if(mouse.getCenterY()>10){
                    mouse.setCenterY(mouse.getCenterY() - 10);
                    if(!up(mouse.getCenterX(), mouse.getCenterY())){
                        mouse.setCenterY(mouse.getCenterY() + 10);
                    }
                }
            }
            public void moveDown(){
                if(mouse.getCenterY()<590){
                    mouse.setCenterY(mouse.getCenterY() + 10);
                    if(!down(mouse.getCenterX(), mouse.getCenterY())){
                        mouse.setCenterY(mouse.getCenterY() - 10);
                    }
                }
            }
            public void moveLeft(){
                if(mouse.getCenterX()>10){
                    mouse.setCenterX(mouse.getCenterX() - 10);
                    if(!left(mouse.getCenterX(), mouse.getCenterY())){
                        mouse.setCenterX(mouse.getCenterX() + 10);
                    }
                }
            }
            public void moveRight(){
                if(mouse.getCenterX()<990){
                    mouse.setCenterX(mouse.getCenterX() + 10);
                    if(!right(mouse.getCenterX(), mouse.getCenterY())){
                        mouse.setCenterX(mouse.getCenterX() - 10);
                    }
                }
            }
            //valid move checking function for dfs
            public boolean valid(double x, double y){
                if (x>0&&y>0&&x<1000&&y<600){
                    for(int i = 0; i<4; i++){
                        if (x>=wallsLeft[i]&&x<=wallsRight[i]&&y>=wallsUp[i]&&y<=wallsDown[i]) {
                            return false;
                        }
                    }
                    return true;
                }
                else{
                    return false;
                }
            }
        });
        //text disappears once S key is released
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
           @Override
           public void handle(KeyEvent event) {
               if (event.getCode() == KeyCode.S) {
                   text.setText("");
               }
           }
       });
        //dfs loop/timer
        prevWakeup = System.currentTimeMillis();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long deltaTime = System.currentTimeMillis() - prevWakeup;
                double secElapsed = (double) deltaTime;
                if (cur >= 0 && secElapsed > 1 &&cur<counter) {
                    mouse.setCenterX(steps[cur].x);
                    mouse.setCenterY(steps[cur].y);
                    //success circle update
                    if (mouse.getCenterX()==cheese.getCenterX()&&mouse.getCenterY()==cheese.getCenterY()){
                        confirm.setFill(Color.GREEN);
                        success = true;
                    }
                    cur++;
                    prevWakeup = System.currentTimeMillis();
                }
                //stops once mouse reaches cheese
                if ((mouse.getCenterX()==cheese.getCenterX()&&mouse.getCenterY()==cheese.getCenterY())){
                    timer.stop();
                }
            }
        };
        timer.start();

    }
    //checks is up/down/left/right is a valid move
    private boolean up(double x, double y){
        for (int i = 0; i<4; i++){
            if ((y==wallsDown[i]&&x>=wallsLeft[i]&&x<=wallsRight[i])||y==0){
                return false;
            }
        }
        return true;
    }
    private boolean down(double x, double y){
        for (int i = 0; i<4; i++){
            if ((y==wallsUp[i]&&x>=wallsLeft[i]&&x<=wallsRight[i])||y==600){
                return false;
            }
        }
        return true;
    }
    private boolean left(double x, double y){
        for (int i = 0; i<4; i++){
            if ((x==wallsRight[i]&&y>=wallsUp[i]&&y<=wallsDown[i])||x==0){
                return false;
            }
        }
        return true;
    }
    public boolean right(double x, double y){
        for (int i = 0; i<4; i++){
            if ((x==wallsLeft[i]&&y>=wallsUp[i]&&y<=wallsDown[i])||x==1000){
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        launch(args);
    }
}