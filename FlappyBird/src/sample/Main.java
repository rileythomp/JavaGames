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
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;

public class Main extends Application {

    private final int SCREEN_WIDTH = 800;
    private final int SCREEN_HEIGHT = 800;
    private final int FONT_SIZE = 30;
    private final String FONT_NAME = "verdana";
    private final Color BACKGROUND_COLOR = Color.LIGHTBLUE;

    public Main() {
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT, BACKGROUND_COLOR);

        Text gameTitle = Util.CreateTextNode(
                "Flappy Bird",
                FONT_NAME, FontWeight.BOLD, Color.BLACK, 2*FONT_SIZE,
                210, 240
        );
        root.getChildren().add(gameTitle);

        Text instructionsTitle = Util.CreateTextNode(
                "      Instructions",
                FONT_NAME, FontWeight.BOLD, Color.BLACK, FONT_SIZE,
                240, 300
        );
        root.getChildren().add(instructionsTitle);

        Text commands = Util.CreateTextNode(
                "ENTER - Start Game\n\n" +
                "SPACE - Flap\n\n" +
                "Q - Quit Game\n\n",
                FONT_NAME, FontWeight.NORMAL, Color.BLACK, FONT_SIZE/2,
                240, 350
        );
        root.getChildren().add(commands);

        Rectangle coverInstructions = new Rectangle(240, 335, 315, 200);
        coverInstructions.setFill(BACKGROUND_COLOR);
        root.getChildren().add(coverInstructions);

        final Canvas canvas = new Canvas(SCREEN_WIDTH,SCREEN_HEIGHT);
        root.getChildren().add(canvas);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                coverInstructions.setY(coverInstructions.getY() + 0.7);
                coverInstructions.setHeight(coverInstructions.getHeight() - 0.7);
            }
        };
        timer.start();

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode pressed = event.getCode();
                if (pressed == KeyCode.Q) {
                    Rectangle rect = new Rectangle(SCREEN_WIDTH/4, SCREEN_HEIGHT/4, SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
                    rect.setFill(Color.WHITESMOKE);
                    Text quitMessage = Util.CreateTextNode(
                            "Thanks for playing!",
                            FONT_NAME, FontWeight.NORMAL, Color.BLACK, FONT_SIZE/2,
                            SCREEN_WIDTH/4 + 25, SCREEN_HEIGHT/4 + 25
                    );
                    root.getChildren().add(rect);
                    root.getChildren().add(quitMessage);

                    AnimationTimer quitDelay = new AnimationTimer() {
                        int counter = 0;
                        @Override
                        public void handle(long now) {
                            counter += 1;
                            if (counter > 100) {
                                System.exit(0);
                            }
                        }
                    };
                    quitDelay.start();
                }
                else if (pressed == KeyCode.ENTER){
                    Game game = new Game(stage);
                    game.play();
                }
            }
        });

        stage.setTitle("Flappy Bird");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}