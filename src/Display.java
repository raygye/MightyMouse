import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Display extends Application {
    //arrays of wall bounds
    private double [] wallsLeft = new double[4];
    private double [] wallsRight = new double[4];
    private double [] wallsUp = new double[4];
    private double [] wallsDown = new double [4];
    //timer
    long oldTime;
    AnimationTimer timer;
    private double timePast;
    //adjacency matrix
    //boolean adj[][] = new boolean [600000][600000];
    // visited matrix (dfs)
    private boolean [][] visited = new boolean [1005][1005];
    // visited array (bfs)
    /*private boolean [] visited2 = new boolean [600000];*/
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
        Group group = new Group();
        Scene scene = new Scene(group, 1000, 600);
        //Success Circle
        Circle confirm = new Circle (950, 50, 30, Color.RED);
        //Cheese Image
        FileInputStream inputStream = new FileInputStream("C:/Users/garyy/IdeaProjects/MightyMice/src/Cheese.jpg");
        Image image = new Image(inputStream);
        ImageView imageView1 = new ImageView(image);
        imageView1.setX(820);
        imageView1.setY(300);
        imageView1.setFitHeight(150);
        imageView1.setFitWidth(150);
        imageView1.setPreserveRatio(true);
        Circle cheese = new Circle(900, 300, 10, Color.YELLOW);
        //Setting color to the scene
        scene.setFill(Color.WHITE);
        //Setting the title to Stage.
        primaryStage.setTitle("Mighty Mice");
        //Adding the scene to Stage
        primaryStage.setScene(scene);
        //Maze Walls
        Rectangle rects [] = new Rectangle[4];
        Rectangle wall1 = new Rectangle(300, 0, 50, 400);
        Rectangle wall2 = new Rectangle(450, 250, 50, 350);
        Rectangle wall3 = new Rectangle(600, 0, 50, 300);
        Rectangle wall4 = new Rectangle(750, 200, 50, 400);
        rects[0] = wall1;
        rects[1] = wall2;
        rects[2] = wall3;
        rects[3] = wall4;
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
        //populating adjacency matrix
        /*for (int i = 0; i< 600; i++){
            for (int j = 0; j<1000; j++){
                if (i>0){
                    adj[i*600000+j][i*59999+j] = up((double)i, (double) j);
                }
                if (i<600){
                    adj[i*600000+j][i*600001+j] = down((double)i, (double) j);
                }
                if (j>0){
                    adj[i*600000+j][i*600000+j-1] = left((double)i, (double) j);
                }
                if (j<1000){
                    adj[i*600000+j][i*600000+j+1] = right((double)i, (double) j);
                }
            }
        }*/
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
                //System.out.println("x " + x + " y "+ y);
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
            }/*
            public Queue bfs(int[][]matrix, int start) {
                Queue queue = new Queue();
                visited2[start] = true;
                queue.enqueue(start);
                while (!queue.isEmpty()) {
                    int element = queue.dequeue();
                    int temp = element;
                    while (temp <= 600000) {
                        if ((!visited2[temp]) && (adj[element][temp] == true)) {
                            if (temp == cheese.getCenterY()*1000+cheese.getCenterX()) {
                                return queue;
                            }
                            queue.enqueue(temp);
                            visited2[temp] = true;
                        }
                        temp++;
                    }
                }
                return queue;
            }*/

            public String toString(){
                String info = "Steps" + cur + " Mouse Coordinates: " + mouse.getCenterX() + ", " + mouse.getCenterY()+ ", " +success;
                return info;
            }
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
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
           @Override
           public void handle(KeyEvent event) {
               if (event.getCode() == KeyCode.S) {
                   text.setText("");
               }
           }
       });
        prevWakeup = System.currentTimeMillis();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long deltaTime = System.currentTimeMillis() - prevWakeup;
                double secElapsed = (double) deltaTime;
                if (cur >= 0 && secElapsed > 1 &&cur<counter) {
                    mouse.setCenterX(steps[cur].x);
                    mouse.setCenterY(steps[cur].y);
                    if (mouse.getCenterX()==cheese.getCenterX()&&mouse.getCenterY()==cheese.getCenterY()){
                        confirm.setFill(Color.GREEN);
                        success = true;
                    }
                    cur++;
                    prevWakeup = System.currentTimeMillis();
                }
                if ((mouse.getCenterX()==cheese.getCenterX()&&mouse.getCenterY()==cheese.getCenterY())){
                    timer.stop();
                }
            }
        };
        timer.start();

    }
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
    public void sleep(int time){
        try{
            Thread.sleep(time);
        }catch (Exception e){}
    }

    public static void main(String[] args) {
        launch(args);
    }
}