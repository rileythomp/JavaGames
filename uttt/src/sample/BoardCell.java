package sample;

public class BoardCell {
    public String val;
    public boolean canBeMovedTo;
    private int x;
    private int y;

    BoardCell(int xcoord, int ycoord) {
        val = "";
        canBeMovedTo = false;
        x = xcoord;
        y = ycoord;
    }
}
