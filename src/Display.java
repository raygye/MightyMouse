import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Display extends Application {
    //arrays of wall bounds
    double [] wallsLeft = new double[4];
    double [] wallsRight = new double[4];
    double [] wallsUp = new double[4];
    double [] wallsDown = new double [4];
    // visited matrix
    boolean [][] visited = new boolean [1005][1005];
    public static int[][] moves = {{0,50},{50,0},{0,-50},{-50,0}};
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new Pane();
        Scene scene = new Scene(pane, 1000, 600);
        //Success Circle
        Circle confirm = new Circle (950, 50, 30, Color.RED);
        //Cheese Image
//        FileInputStream inputStream = new FileInputStream("C:/Users/garyy/IdeaProjects/MightyMice/src/Cheese.jpg");
//        Image image = new Image(inputStream);
//        ImageView imageView1 = new ImageView(image);
//        imageView1.setX(820);
//        imageView1.setY(300);
//        imageView1.setFitHeight(150);
//        imageView1.setFitWidth(150);
//        imageView1.setPreserveRatio(true);
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
        /*//populating adjacency matrix
        for(int i = 0; i<10; i++){
            for (int j = 0; j<10; j++){
                //i=rows=y(multiply by number in row), j = x
                //up
                matrix[i*10+j][i*9+j] = up(i*10, j*10);
                //down
                if(i*11+j<100) {
                    matrix[i * 10 + j][i * 11 + j] = down(i * 10, j * 10);
                }
                //left
                if(i*10+j-1>0){
                    matrix[i * 10 + j][i * 10 + j - 1] = left(i * 10, j * 10);
                }
                //right
                if(i*10+j+1<100) {
                    matrix[i * 10 + j][i * 10 + j + 1] = right(i * 10, j * 10);
                }
            }
        }*/
        /*int counter = 0;
        for(int i = 0; i<matrix.length; i++){
            for (int j = 0; j<matrix[i].length; j++){
                if(matrix[i][j]){
                    System.out.println(matrix[i][j]+ " "+ counter);
                    counter++;
                }
            }
        }*/
        //Mouse
        Circle mouse = new Circle(200, 300, 10, Color.DARKGRAY);
        //Displaying the contents of the stage
        pane.getChildren().addAll(wall1, wall2, wall3, wall4, cheese, mouse, confirm);
        primaryStage.show();
        //Keyboard Input
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP) {
                    System.out.println("UP key pressed.");
                    moveUp();
                }
                if (event.getCode() == KeyCode.DOWN) {
                    System.out.println("DOWN key pressed.");
                    moveDown();
                }
                if (event.getCode() == KeyCode.LEFT) {
                    System.out.println("LEFT key pressed.");
                    moveLeft();
                }
                if (event.getCode() == KeyCode.RIGHT) {
                    System.out.println("RIGHT key pressed.");
                    moveRight();
                }
                if (event.getCode() == KeyCode.ENTER){
                    dfs((int)mouse.getCenterX(), (int)mouse.getCenterY());
                    System.out.println("ENTER key pressed.");
                }
                //color indicates whether the mouse has the cheese
                if (mouse.getCenterX()==cheese.getCenterX()&&mouse.getCenterY()==cheese.getCenterY()){
                    confirm.setFill(Color.GREEN);
                }
                else{
                    confirm.setFill(Color.RED);
                }
                System.out.println(mouse.getCenterX() + "," + mouse.getCenterY());
            }

            public boolean dfs(int x, int y){
                if(mouse.getCenterX()==cheese.getCenterX()&&mouse.getCenterY()==cheese.getCenterY()){
                    System.exit(0);
                }
                System.out.println("x " + x + " y "+ y);
                visited[x][y] = true;
                for(int i = 0; i<4; i++){
                    int nextX = x+moves[i][0], nextY = y+moves[i][1];
                    System.out.println("x " + nextX + " y "+ nextY);
            /*public void dfs(){
                boolean [][] visited = new boolean[60][100];
                //up>left>down>right
                while(!(mouse.getCenterX()==cheese.getCenterX()&&mouse.getCenterY()==cheese.getCenterY())) {
                    while (!up(mouse.getCenterX(), mouse.getCenterY())) {
                        visited[(int) mouse.getCenterY() / 10][(int) mouse.getCenterX() / 10] = true;
                        moveUp();
                        if (visited[(int) mouse.getCenterY() / 10][(int) mouse.getCenterX() / 10] == true) {
                            moveDown();
                            break;
                        }
                        sleep(1000);
                    }
                    while (!left(mouse.getCenterX(), mouse.getCenterY())) {
                        visited[(int) mouse.getCenterY() / 10][(int) mouse.getCenterX() / 10] = true;
                        moveLeft();
                        if (visited[(int) mouse.getCenterY() / 10][(int) mouse.getCenterX() / 10] == true) {
                            moveRight();
                            break;
                        }
                        sleep(1000);
                    }
                    while (!down(mouse.getCenterX(), mouse.getCenterY())) {
                        visited[(int) mouse.getCenterY() / 10][(int) mouse.getCenterX() / 10] = true;
                        moveDown();
                        if (visited[(int) mouse.getCenterY() / 10][(int) mouse.getCenterX() / 10] == true) {
                            moveUp();
                            break;
                        }
                        sleep(1000);
                    }
                    while (!right(mouse.getCenterX(), mouse.getCenterY())) {
                        visited[(int) mouse.getCenterY() / 10][(int) mouse.getCenterX() / 10] = true;
                        moveRight();
                        if (visited[(int) mouse.getCenterY() / 10][(int) mouse.getCenterX() / 10] == true) {
                            moveLeft();
                            break;
                        }
                        sleep(1000);
                    }
                }
            }*/
            /*public void dfs(double x, double y){
                if((mouse.getCenterX()==cheese.getCenterX()&&mouse.getCenterY()==cheese.getCenterY())){
                    return;
                }
                visited[(int)x][(int)y] = true;
                for(int i = 0; i<4; i++){
                    double nextX = x+moves[i][0], nextY = y+moves[i][1];
                    if((!valid(nextX, nextY) || visited[(int)nextX] [(int)nextY])){
                        mouse.setCenterX(nextX);
                        mouse.setCenterY(nextY);
                        sleep(1000);
                        dfs(nextX, nextY);
                        mouse.setCenterX(x);
                        mouse.setCenterY(y);
                    }
                }
            }*/
                    if(valid(nextX, nextY) && !visited[nextX] [nextY]){
                        System.out.println("x " + x + " y "+ y);
                        mouse.setCenterX((double)nextX);
                        mouse.setCenterY((double)nextY);
                        sleep(100);
                        if(dfs(nextX, nextY)){
                            return true;
                        }
                        mouse.setCenterX((double)x);
                        mouse.setCenterY((double)y);
                    }
                }
                return false;
            }
            public void sleep(int time){
                try{
                    Thread.sleep(time);
                }catch (Exception e){}
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
                if (x>0&&y>0&&x<=600&&y<=1000){
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
    }
    public boolean up(double x, double y){
        for (int i = 0; i<4; i++){
            if ((y==wallsDown[i]&&x>=wallsLeft[i]&&x<=wallsRight[i])||y==0){
                return false;
            }
        }
        return true;
    }
    public boolean down(double x, double y){
        for (int i = 0; i<4; i++){
            if ((y==wallsUp[i]&&x>=wallsLeft[i]&&x<=wallsRight[i])||y==610){
                return false;
            }
        }
        return true;
    }
    public boolean left(double x, double y){
        for (int i = 0; i<4; i++){
            if ((x==wallsRight[i]&&y>=wallsUp[i]&&y<=wallsDown[i])||x==0){
                return false;
            }
        }
        return true;
    }
    public boolean right(double x, double y){
        for (int i = 0; i<4; i++){
            if ((x==wallsLeft[i]&&y>=wallsUp[i]&&y<=wallsDown[i])||x==1010){
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        launch(args);
    }
}