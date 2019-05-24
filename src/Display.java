import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Display extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        int numWalls = (int)(Math.random()*4+1);

        System.out.println(numWalls);
        Pane pane = new Pane();
        Scene scene = new Scene(pane, 1440, 1080);
        //setting color to the scene
        scene.setFill(Color.WHITE);
        //Setting the title to Stage.
        primaryStage.setTitle("Mighty Mice");
        //Adding the scene to Stage
        primaryStage.setScene(scene);
        //Displaying the contents of the stage
        primaryStage.show();
    }
}