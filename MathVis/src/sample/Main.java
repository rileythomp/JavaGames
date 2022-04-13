package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Main extends Application {

    private final int SCREEN_WIDTH = 750;
    private final int SCREEN_HEIGHT = 750;
    private final int FONT_SIZE = 30;
    private final String FONT_NAME = "verdana";
    private final Color BACKGROUND_COLOR = Color.WHITESMOKE;

    public Main() throws FileNotFoundException {

    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        Group root = new Group();
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT, BACKGROUND_COLOR);
        final Canvas canvas = new Canvas(SCREEN_WIDTH,SCREEN_HEIGHT);

        RevealInstructions(root);

        root.getChildren().add(canvas);

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode pressed = keyEvent.getCode();
                if (pressed == KeyCode.Q) {
                    QuitGame(root);
                }
                else if (pressed == KeyCode.S) {
                    Sierpinksi spt = new Sierpinksi(primaryStage);
                }
                else if (pressed == KeyCode.F) {
                    try {
                        Fibonacci fib = new Fibonacci(primaryStage);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else if (pressed == KeyCode.L) {
                    LineArt la = new LineArt(primaryStage);
                }
            }
        });

        Text t1 = new Text(10, 20, "Press 'f' to see the Fibonacci spiral");
        root.getChildren().add(t1);

        Text t2 = new Text(10, 40, "Press 's' to see the Sierpinski triangles");
        root.getChildren().add(t2);

        Text t3 = new Text(10, 60, "Press 'l' to see the line art");
        root.getChildren().add(t3);

        primaryStage.setTitle("Math Visualizations");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void RevealInstructions(Group root) {
    }

    public void QuitGame(Group root) {
        Rectangle rect = new Rectangle(SCREEN_WIDTH/4, SCREEN_HEIGHT/4 - 25, SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
        rect.setFill(Color.WHITE);
        Text quitMessage = Util.CreateTextNode(
                "Thanks for playing!",
                FONT_NAME, FontWeight.NORMAL, Color.BLACK, FONT_SIZE/2,
                SCREEN_WIDTH/4 + 25, SCREEN_HEIGHT/4
        );
        root.getChildren().add(rect);
        root.getChildren().add(quitMessage);

        AnimationTimer quitDelay = new AnimationTimer() {
            int counter = 0;
            @Override
            public void handle(long now) {
                counter += 1;
                if (counter > 500) {
                    System.exit(0);
                }
            }
        };
        quitDelay.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
