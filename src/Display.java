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
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new Pane();
        Scene scene = new Scene(pane, 1000, 600);
        //Cheese Image
        FileInputStream inputStream = new FileInputStream("C:/Users/garyy/IdeaProjects/MightyMice/src/Cheese.jpg");
        Image image = new Image(inputStream);
        ImageView imageView1 = new ImageView(image);
        imageView1.setX(820);
        imageView1.setY(300);
        imageView1.setFitHeight(150);
        imageView1.setFitWidth(150);
        imageView1.setPreserveRatio(true);
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
        double [] wallsLeft = new double[4];
        double [] wallsRight = new double[4];
        double [] wallsUp = new double[4];
        double [] wallsDown = new double [4];
        for (int i = 0; i<4; i++){
            wallsLeft[i] = rects[i].getX();
            wallsRight[i] = rects[i].getX()+50;
            wallsDown[i] = rects[i].getY()+rects[i].getHeight();
            wallsUp[i] = rects[i].getY();
        }
        //Mouse
        Circle mouse = new Circle(200, 300, 10, Color.DARKGRAY);
        //Displaying the contents of the stage
        pane.getChildren().addAll(wall1, wall2, wall3, wall4, imageView1, mouse);
        primaryStage.show();
        //Keyboard Input
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println(mouse.getCenterX() + "," + mouse.getCenterY());
                if (event.getCode() == KeyCode.UP && mouse.getCenterY()>10) {
                    System.out.println("UP key pressed.");
                    mouse.setCenterY(mouse.getCenterY() - 10);
                    for (int i = 0; i<4; i++){
                        if (mouse.getCenterY()==wallsDown[i]&&mouse.getCenterX()>=wallsLeft[i]&&mouse.getCenterX()<=wallsRight[i]){
                            mouse.setCenterY(mouse.getCenterY() + 10);
                        }
                    }
                }
                if (event.getCode() == KeyCode.DOWN && mouse.getCenterY()<590) {
                    mouse.setCenterY(mouse.getCenterY() + 10);
                    System.out.println("DOWN key pressed.");
                    for (int i = 0; i<4; i++){
                        if (mouse.getCenterY()==wallsUp[i]&&mouse.getCenterX()>=wallsLeft[i]&&mouse.getCenterX()<=wallsRight[i]){
                            mouse.setCenterY(mouse.getCenterY() - 10);
                        }
                    }
                }
                if (event.getCode() == KeyCode.LEFT && mouse.getCenterX()>10) {
                    mouse.setCenterX(mouse.getCenterX() - 10);
                    System.out.println("LEFT key pressed.");
                    for (int i = 0; i<4; i++){
                        if (mouse.getCenterX()==wallsRight[i]&&mouse.getCenterY()>=wallsUp[i]&&mouse.getCenterY()<=wallsDown[i]){
                            mouse.setCenterX(mouse.getCenterX() + 10);
                        }
                    }
                }
                if (event.getCode() == KeyCode.RIGHT && mouse.getCenterX()<990) {
                    mouse.setCenterX(mouse.getCenterX() + 10);
                    System.out.println("RIGHT key pressed.");
                    for (int i = 0; i<4; i++){
                        if (mouse.getCenterX()==wallsLeft[i]&&mouse.getCenterY()>=wallsUp[i]&&mouse.getCenterY()<=wallsDown[i]){
                            mouse.setCenterX(mouse.getCenterX() - 10);
                        }
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}