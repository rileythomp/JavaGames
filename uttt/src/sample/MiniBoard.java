package sample;

import java.util.ArrayList;

public class MiniBoard {
    public ArrayList<BoardCell> cells;
    public boolean isTaken;
    public String ownedBy;

    MiniBoard() {
        isTaken = false;
        cells = new ArrayList<>();
        for (int i = 0; i < 9; ++i) {
            int y = i/3;
            int x = i%3;
            cells.add(new BoardCell(x, y));
        }
        ownedBy = "";
    }

    public boolean isWon() {
        return (cells.get(0).val != "" && cells.get(0).val == cells.get(1).val && cells.get(1).val == cells.get(2).val) ||
                (cells.get(3).val != "" && cells.get(3).val == cells.get(4).val && cells.get(4).val == cells.get(5).val) ||
                (cells.get(6).val != "" && cells.get(6).val == cells.get(7).val && cells.get(7).val == cells.get(8).val) ||
                (cells.get(0).val != "" && cells.get(0).val == cells.get(3).val && cells.get(3).val == cells.get(6).val) ||
                (cells.get(1).val != "" && cells.get(1).val == cells.get(4).val && cells.get(4).val == cells.get(7).val) ||
                (cells.get(2).val != "" && cells.get(2).val == cells.get(5).val && cells.get(5).val == cells.get(8).val) ||
                (cells.get(0).val != "" && cells.get(0).val == cells.get(4).val && cells.get(4).val == cells.get(8).val) ||
                (cells.get(2).val != "" && cells.get(2).val == cells.get(4).val && cells.get(4).val == cells.get(6).val);
    }
}
