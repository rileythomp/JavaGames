package sample;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.Date;

public class GameView {
    private Stage gameStage;
    private Pane gameRoot;
    private Scene gameScene;

    private final int SCREEN_WIDTH = 1000;
    private final int SCREEN_HEIGHT = 800;
    private final int FONT_SIZE = 30;
    private final String FONT_NAME = "verdana";
    private final Color BACKGROUND_COLOR = Color.BURLYWOOD;

    private final int CELL_LEN = 50; // in px
    private final int BOARD_LEN = 9; // in cells

    GameView(Stage stage) {
        gameStage = stage;
    }

    private void handleGameWon(Game game) {
        String turnText = "Player " + (!game.p1Turn ? "1" : "2") + " has won!";
        Text turnMessage = Util.CreateTextNode(turnText, FONT_NAME, FontWeight.NORMAL, Color.BLACK, FONT_SIZE/2, 50, 50 + CELL_LEN * BOARD_LEN + 66);
        gameRoot.getChildren().add(turnMessage);

        Text quitMessage = Util.CreateTextNode("q - Quit", FONT_NAME, FontWeight.NORMAL, Color.BLACK, FONT_SIZE/2, 50, 50 + CELL_LEN * BOARD_LEN + 99);
        gameRoot.getChildren().add(quitMessage);

        Text playMessage = Util.CreateTextNode("p - Play again",FONT_NAME, FontWeight.NORMAL, Color.BLACK, FONT_SIZE/2, 50, 50 + CELL_LEN * BOARD_LEN + 132);
        gameRoot.getChildren().add(playMessage);

        for (Object child : gameRoot.getChildren()) {
            if (child instanceof Rectangle) {
                ((Rectangle)(child)).setFill(Color.WHITESMOKE);
            }
        }

        gameRoot.getChildren().add(game.board.winLine);
    }

    public void displayGame(Game game) {
        gameRoot = new Pane();

        displayBoard(game);

        displayBoardLines();

        if (game.isWon) {
            handleGameWon(game);
        }
        else {
            displayTurnMessage(game.p1Turn);
        }

        gameScene = new Scene(gameRoot, SCREEN_WIDTH, SCREEN_HEIGHT, BACKGROUND_COLOR);

        if (game.isWon) {
            gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    takeUserKeyInput(keyEvent);
                }
            });
        }

        gameStage.setScene(gameScene);
    }

    private void takeUserKeyInput(KeyEvent keyEvent) {
        KeyCode pressed = keyEvent.getCode();
        if (pressed == KeyCode.Q) {
            System.exit(0);
        }
        else if (pressed == KeyCode.P) {
            Game game = null;
            try {
                game = new Game(gameStage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            game.play();
        }
    }

    private void displayBoard(Game game) {
        int i = 0;
        for (MiniBoard miniBoard : game.board.miniBoards) {
            int y = i/3;
            int x = i%3;
            if (!miniBoard.isTaken) {
                int j = 0;
                for (BoardCell boardCell : miniBoard.cells) {
                    int miniX = j%3;
                    int miniY = j/3;
                    Rectangle cellView = new Rectangle((SCREEN_WIDTH - BOARD_LEN*CELL_LEN)/2 + 150*x + 50*miniX, 150*y + 50 + 50*miniY, 50, 50);
                    if (miniBoard.cells.get(j).canBeMovedTo && miniBoard.cells.get(j).val == "") {
                        cellView.setFill(Color.PALEGREEN);
                        cellView.opacityProperty().set(0.75);
                        cellView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                if (!game.isWon) {
                                    game.updateGame(x, y, miniX, miniY);

//                                    long startTime = new Date().getTime();
//                                    long curTime = new Date().getTime();
//                                    while ((int)(curTime - startTime)/1000 < 4) {
//                                        curTime = new Date().getTime();
//                                    }
//                                    cellView.setFill(Color.PURPLE);
                                    
                                    displayGame(game);
//                                    int mbIndex = 0;
//                                    boolean broken = false;
//                                    for (MiniBoard mb : game.board.miniBoards) {
//                                        int cellIndex = 0;
//                                        for (BoardCell cell : mb.cells) {
//                                            if (cell.val == "") {
//                                                game.board.miniBoards.get(mbIndex).cells.get(cellIndex).val = "R";
//                                                broken = true;
//                                                break;
//                                            }
//                                            cellIndex++;
//                                        }
//                                        if (broken) {
//                                            break;
//                                        }
//                                        mbIndex++;
//                                    }
//                                    displayGame(game);
                                }
                            }
                        });
                    }
                    else {
                        cellView.setFill(Color.WHITESMOKE);
                    }
                    Text letter = Util.CreateTextNode(miniBoard.cells.get(j).val, FONT_NAME, FontWeight.NORMAL, Color.BLACK, FONT_SIZE, (SCREEN_WIDTH - BOARD_LEN*CELL_LEN)/2 + 50*(3*x+miniX)+15, 50*(3*y+miniY)+88);
                    gameRoot.getChildren().add(cellView);
                    gameRoot.getChildren().add(letter);
                    j++;
                }
                displayMiniBoardLines(x, y);
            }
            else {
                Rectangle cellView = new Rectangle((SCREEN_WIDTH - BOARD_LEN*CELL_LEN)/2 + 150*x, 150*y + 50, 150, 150);
                cellView.setFill(Color.WHITESMOKE);
                gameRoot.getChildren().add(cellView);

                Text letter = Util.CreateTextNode(miniBoard.ownedBy, FONT_NAME, FontWeight.NORMAL, Color.BLACK, 5*FONT_SIZE, (SCREEN_WIDTH - CELL_LEN*BOARD_LEN)/2 + 150*x + 15, 180 + 150*y);
                gameRoot.getChildren().add(letter);
            }

            i++;
        }
    }

    private void displayMiniBoardLines(int x, int y) {
        Line firstVert = new Line((SCREEN_WIDTH - BOARD_LEN*CELL_LEN)/2 + 50 + 150*x, 50 + 150*y, (SCREEN_WIDTH - BOARD_LEN*CELL_LEN)/2 + 50 + 150*x, 200 + 150*y);
        gameRoot.getChildren().add(firstVert);

        Line secondVert = new Line((SCREEN_WIDTH - BOARD_LEN*CELL_LEN)/2 + 100 + 150*x, 50 + 150*y, (SCREEN_WIDTH - BOARD_LEN*CELL_LEN)/2 + 100 + 150*x, 200 + 150*y);
        gameRoot.getChildren().add(secondVert);

        Line firstHoriz = new Line((SCREEN_WIDTH - BOARD_LEN*CELL_LEN)/2 + 150*x, 100 + 150*y, (SCREEN_WIDTH - BOARD_LEN*CELL_LEN)/2 + 150 + 150*x, 100 + 150*y);
        gameRoot.getChildren().add(firstHoriz);

        Line secondHoriz = new Line((SCREEN_WIDTH - BOARD_LEN*CELL_LEN)/2 + 150*x, 150 + 150*y, (SCREEN_WIDTH - BOARD_LEN*CELL_LEN)/2 + 150 + 150*x, 150 + 150*y);
        gameRoot.getChildren().add(secondHoriz);
    }

    private void displayTurnMessage(boolean p1Turn) {
        String turnText = "Player " + (p1Turn ? "1" : "2") + "'s turn";
        Text turnMessage = Util.CreateTextNode(
                turnText,
                FONT_NAME, FontWeight.NORMAL, Color.BLACK, FONT_SIZE/2,
                50, 50 + CELL_LEN * BOARD_LEN + 33 + 33
        );
        gameRoot.getChildren().add(turnMessage);
    }

    private void displayBoardLines() {
        for (int i = 0; i <= BOARD_LEN; ++i) {
            if (i%3 == 0) {
                Line line = new Line(((SCREEN_WIDTH - BOARD_LEN*CELL_LEN)/2), CELL_LEN*i + 50, ((SCREEN_WIDTH - BOARD_LEN*CELL_LEN)/2) + CELL_LEN*BOARD_LEN, 50 + CELL_LEN*i);
                line.setStrokeWidth(3);
                gameRoot.getChildren().add(line);
            }
        }

        for (int i = 0; i <= BOARD_LEN; ++i) {
            if (i%3 == 0) {
                Line line = new Line(((SCREEN_WIDTH - BOARD_LEN*CELL_LEN)/2) + CELL_LEN*i, 50, ((SCREEN_WIDTH - BOARD_LEN*CELL_LEN)/2) + CELL_LEN*i, 50 + CELL_LEN*BOARD_LEN);
                line.setStrokeWidth(3);
                gameRoot.getChildren().add(line);
            }
        }
    }
}
