import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Display extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new Pane();
        Scene scene = new Scene(pane, 1000, 600 );
        FileInputStream inputStream= new FileInputStream("C:/Users/garyy/IdeaProjects/MightyMice/src/Cheese.jpg");
        Image image = new Image(inputStream);
        ImageView imageView1 = new ImageView(image);
        imageView1.setX(820);
        imageView1.setY(300);
        imageView1.setFitHeight(150);
        imageView1.setFitWidth(150);
        imageView1.setPreserveRatio(true);
        //setting color to the scene
        scene.setFill(Color.WHITE);
        //Setting the title to Stage.
        primaryStage.setTitle("Mighty Mice");
        //Adding the scene to Stage
        primaryStage.setScene(scene);
        //Maze Walls
        Rectangle wall1 = new Rectangle(300, 0, 50, 400);
        Rectangle wall2 = new Rectangle(450, 250, 50, 350);
        Rectangle wall3 = new Rectangle(600, 0, 50, 300);
        Rectangle wall4 = new Rectangle(750, 200, 50, 400);
        pane.getChildren().addAll(wall1, wall2, wall3, wall4, imageView1);
        //Displaying the contents of the stage
        primaryStage.show();
    }
    public static void main(String[] args) {
            launch(args);
    }
}