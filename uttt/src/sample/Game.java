package sample;

import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Game {
    public boolean p1Turn;
    public Board board;
    public boolean isWon;

    private GameView view;

    public Game(Stage stage) throws FileNotFoundException {
        p1Turn = true;
        board = new Board();
        view = new GameView(stage);
    }

    public void play() {
        view.displayGame(this);
    }

    public void updateGame(int x, int y, int miniX, int miniY) {
        board.miniBoards.get(3*y + x).cells.get(3*miniY + miniX).val = (p1Turn ? "X" : "O");
        p1Turn = !p1Turn;

        // Check for win on small board
        checkForSmallWin(x, y);

        // Check for win on large board
        checkForWin();

        // update can canBeMovedTo
        for (int i = 0; i < board.miniBoards.size(); ++i) {
            MiniBoard miniBoard = board.miniBoards.get(i);
            for (int j = 0; j < miniBoard.cells.size(); ++j) {
                int curX = i%3;
                int curY = i/3;

                if (board.miniBoards.get(3*miniY+miniX).isTaken && miniBoard.cells.get(j).val == "") {
                    miniBoard.cells.get(j).canBeMovedTo = true;
                }
                else if (curX == miniX && curY == miniY && miniBoard.cells.get(j).val == "") {
                    miniBoard.cells.get(j).canBeMovedTo = true;
                }
                else {
                    miniBoard.cells.get(j).canBeMovedTo = false;
                }
            }
        }
    }

    public void checkForWin() {
        isWon = board.isWon();
    }

    public void checkForSmallWin(int x, int y) {
        MiniBoard boardToCheck = board.miniBoards.get(3*y + x);
        if (boardToCheck.isWon()) {
            board.miniBoards.get(3*y + x).isTaken = true;
            board.miniBoards.get(3*y + x).ownedBy = (p1Turn ? "O" : "X");
        }
    }

}
