package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Fibonacci {
    private final int SCREEN_WIDTH = 750;
    private final int SCREEN_HEIGHT = 750;
    private final Color BACKGROUND_COLOR = Color.WHITESMOKE;

    private Stage stage;
    private Pane root;
    private Scene scene;

    private Map<Integer, Color> colorMap = new HashMap<Integer, Color>()
    {
        {
            put(0, Color.LIGHTSTEELBLUE);
            put(1, Color.LIGHTPINK);
            put(2, Color.LIGHTGREEN);
            put(3, Color.GOLD);
            put(4, Color.MEDIUMPURPLE);
        }
    };

    private int curx = 450;
    private int cury = 225;
    private final int scaleFactor = 4;
    private int fn_1 = scaleFactor;
    private int fn_2 = scaleFactor;
    private int squares = 0;

    public Fibonacci(Stage s) throws InterruptedException {
        stage = s;
        stage.setTitle("Fibonacci Spiral");
        root = new Pane();

        Text msg = new Text(10, 740, "Hit enter to generate the next square");
        root.getChildren().add(msg);

        scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT, BACKGROUND_COLOR);
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode pressed = keyEvent.getCode();
                if (pressed == KeyCode.Q) {
                    System.exit(0);
                }
                else if (pressed == KeyCode.ENTER && squares < 11) {
                    Rectangle rect = new Rectangle(curx, cury, fn_1, fn_1);
                    rect.setFill(colorMap.getOrDefault(squares%5, Color.BLACK));
                    root.getChildren().add(rect);

                    if (squares > 3) {
                        Text t = new Text(curx + 5, cury + 15, Integer.toString(fn_1/scaleFactor));
                        root.getChildren().add(t);
                    }

                    if (squares%4 == 0) {
                        curx -= fn_2;
                    }
                    else if (squares%4 == 1) {
                        cury += fn_1;
                    }
                    else if (squares%4 == 2) {
                        curx += fn_1;
                        cury -= (fn_2 - fn_1);
                    }
                    else if (squares%4 == 3) {
                        curx -= (fn_2 - fn_1);
                        cury -= fn_2;
                    }

                    int oldf2 = fn_2;
                    fn_2 = fn_2 + fn_1;
                    fn_1 = oldf2;
                    squares++;
                }
                scene.setRoot(root);
                stage.setScene(scene);
            }
        });

        stage.setScene(scene);
    }
}