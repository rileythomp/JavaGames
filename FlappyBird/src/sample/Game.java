package sample;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

public class Game {
    private ImageView bird;
    private double vy;
    private int score;
    private final double ay = 0.01;
    private final int BIRD_RADIUS = 10;

    private Pane root;
    private Scene scene;
    private Stage stage;

    private final int SCREEN_WIDTH = 800;
    private final int SCREEN_HEIGHT = 800;
    private final Color BACKGROUND_COLOR = Color.LIGHTBLUE;

    public Game(Stage s) {
        stage = s;
        root = new Pane();
        scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT, BACKGROUND_COLOR);

        vy = 0.1;
        score = 0;
        Text scoreLabel = Util.CreateTextNode(Integer.toString(score), "verdana", FontWeight.BOLD, Color.WHITE, 30, SCREEN_WIDTH - 50, 35);
        root.getChildren().add(scoreLabel);

        bird = new ImageView("bird.png");
        bird.setFitHeight(40);
        bird.setFitWidth(40);
        bird.setPreserveRatio(true);
        bird.setX(20);

        root.getChildren().add(bird);

        // move the player
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                handlePlayerMovement(event.getCode());
            }
        });

        stage.setScene(scene);
    }

    private void handlePlayerMovement(KeyCode pressed) {
        if (pressed == KeyCode.SPACE) {
            vy = -2.0;
        }
    }

    public void play() {
        AnimationTimer timer = new AnimationTimer() {
            int i = 0;

            @Override
            public void handle(long now) {
                if (bird.getY() + vy < 0) {
                    bird.setY(0);
                    vy = 0;
                }
                else if (bird.getY() > SCREEN_HEIGHT) {
//                    bird.setY(SCREEN_HEIGHT - 25);
                    // should be a loss
                    this.stop();
                    endGame();
                }
                else  {
                    bird.setY(bird.getY() + vy);
                }
                vy += ay;

                // shift the pipes
                for (int i = 0; i < root.getChildren().size(); ++i) {
                    if (root.getChildren().get(i) instanceof Rectangle) {
                        Rectangle newPipe = (Rectangle)root.getChildren().get(i);
                        newPipe.setX(newPipe.getX() - 1);
                        if (newPipe.getX() < 0 - newPipe.getWidth()) {
                            root.getChildren().remove(root.getChildren().get(i));
                            if (newPipe.getY() == 0) {
                                score += 1;
                                Text updateScore = (Text)root.getChildren().get(0);
                                updateScore.setText(Integer.toString(score));
                                root.getChildren().set(0, updateScore);
                            }
                        }
                        else {
                            root.getChildren().set(i, newPipe);
                        }

                        // check if hit bird
                        if (bird.getX() + 30 > newPipe.getX() && bird.getX() + 10 < newPipe.getX() + newPipe.getWidth()) {
                            if (newPipe.getY() == 0 && bird.getY() + 10 < newPipe.getY() + newPipe.getHeight()) {
                                this.stop();
                                endGame();
                            }
                            else if (newPipe.getY() != 0 && bird.getY() + 25 > newPipe.getY()) {
                                this.stop();
                                endGame();
                            }
                        }
                    }
                    else if (root.getChildren().get(i) instanceof ImageView) {
                        ImageView img = (ImageView)root.getChildren().get(i);

                        if (img.getFitWidth() != 40) {
                            img.setX(img.getX() - 1);
                            if (img.getX() < -100) {
                                root.getChildren().remove(root.getChildren().get(i));
                            }
                            else {
                                root.getChildren().set(i, img);
                            }
                        }
                    }
                }

                if (i == 0) {
                    addPipe();
                }
                i++;
                i %= 420;
            }
        };
        timer.start();
    }

    private void endGame() {
        Rectangle rect = new Rectangle(SCREEN_WIDTH/4, SCREEN_HEIGHT/4, SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
        rect.setFill(Color.WHITE);
        Text winMessage = Util.CreateTextNode(
                "Oh no! You crashed!\n\n" +
                        "Final Score: " + score + "\n\n" +
                        "ENTER - Play again\n\n" +
                        "Q - Quit Game\n\n",
                "verdana", FontWeight.NORMAL, Color.BLACK, 15,
                SCREEN_WIDTH/4 + 25, SCREEN_HEIGHT/4 + 25
        );
        root.getChildren().add(rect);
        root.getChildren().add(winMessage);
        scene.setRoot(root);

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode pressed = event.getCode();
                if (pressed == KeyCode.Q) {
                    System.exit(0);
                }
                else if (event.getCode() == KeyCode.ENTER){
                    Game game = new Game(stage);
                    game.play();
                }
            }
        });
        stage.setScene(scene);
    }

    private void addPipe() {
        int totalPipe = 500;
        int maxPipe = 450;
        int minPipe = totalPipe - maxPipe;
        int pipeWidth = 100;
        Random r = new Random();
        int topHeight = r.nextInt(maxPipe - minPipe) + minPipe;
        int bottomHeight = totalPipe - topHeight;

        Rectangle topPipe = new Rectangle(SCREEN_WIDTH - pipeWidth, 0, pipeWidth, topHeight);
        Rectangle bottomPipe = new Rectangle(SCREEN_WIDTH - pipeWidth, SCREEN_HEIGHT - bottomHeight, pipeWidth, bottomHeight);

        topPipe.setX(SCREEN_WIDTH);
        bottomPipe.setX(SCREEN_WIDTH);

        topPipe.setY(0);
        bottomPipe.setY(SCREEN_HEIGHT - bottomHeight);

        topPipe.setFill(Color.LIMEGREEN);
        bottomPipe.setFill(Color.LIMEGREEN);

        ImageView cloud = new ImageView("cloud.png");
        cloud.setX(SCREEN_WIDTH + Math.min(bottomHeight, topHeight));
        cloud.setY(Math.min(bottomHeight, topHeight));

        root.getChildren().add(cloud);
        root.getChildren().add(topPipe);
        root.getChildren().add(bottomPipe);
        scene.setRoot(root);
        stage.setScene(scene);
    }
}
