package sample;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class LineArt {
    private final int SCREEN_WIDTH = 750;
    private final int SCREEN_HEIGHT = 750;
    private final Color BACKGROUND_COLOR = Color.WHITESMOKE;

    private Stage stage;
    private Pane root;
    private Scene scene;

    private Map<Integer, Color> colorMap = new HashMap<Integer, Color>()
    {
        {
            put(0, Color.RED);
            put(1, Color.BLUE);
            put(2, Color.GREEN);
            put(3, Color.GOLD);
        }
    };

    public LineArt(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Line Art");
        root = new Pane();

        Text t = new Text(10, 730, "Line Art");
        root.getChildren().add(t);

        int startWidth = 700;
        int startX = 10;
        int startY = 10;
        int vertGap = 7;
        int squares = 0;

        while (startWidth > vertGap) {
            drawCurve(vertGap, startWidth, startWidth, startX, startY, colorMap.get(squares%4), Curves.TOPLEFT);
            drawCurve(vertGap, startWidth, startWidth, startX, startY, colorMap.get((1+squares)%4), Curves.TOPRIGHT);
            drawCurve(vertGap, startWidth, startWidth, startX, startY, colorMap.get((2+squares)%4), Curves.BOTTOMRIGHT);
            drawCurve(vertGap, startWidth, startWidth, startX, startY, colorMap.get((3+squares)%4), Curves.BOTTOMLEFT);

            startX += (startWidth/4);
            startY += (startWidth/4);
            startWidth /= 2;
            squares++;
        }

//        while (startWidth > 16) {
//            drawCurve(vertGap, startWidth, startWidth, startX, startY, colorMap.get(squares%4), Curves.TOPLEFT);
//            drawCurve(vertGap, startWidth, startWidth, startX + startWidth, startY, colorMap.get((1+squares)%4), Curves.TOPRIGHT);
//            drawCurve(vertGap, startWidth, startWidth, startX + startWidth, startY + startWidth, colorMap.get((2+squares)%4), Curves.BOTTOMRIGHT);
//            drawCurve(vertGap, startWidth, startWidth, startX, startY + startWidth, colorMap.get((3+squares)%4), Curves.BOTTOMLEFT);
//
//            startX += (startWidth/4);
//            startY += (startWidth/4);
//            startWidth -= (startWidth/4);
//            squares++;
//        }
//
//        drawCurve(8, 256, 256, 10 + 256, 10 + 256, Color.BLACK, Curves.TOPLEFT);
//        drawCurve(8, 256, 256, 10, 10 + 256, Color.BLACK, Curves.TOPRIGHT);
//        drawCurve(8, 256, 256, 10, 10, Color.BLACK, Curves.BOTTOMRIGHT);
//        drawCurve(8, 256, 256, 10 + 256, 10, Color.BLACK, Curves.BOTTOMLEFT);
//
//        drawCurve(6, 118, 118, 10 + 118, 10 + 118, Color.BLACK, Curves.TOPLEFT);
//        drawCurve(6, 118, 118, 10, 10 + 118, Color.BLACK, Curves.TOPRIGHT);
//        drawCurve(6, 118, 118, 10, 10, Color.BLACK, Curves.BOTTOMRIGHT);
//        drawCurve(6, 118, 118, 10 + 118, 10, Color.BLACK, Curves.BOTTOMLEFT);
//
//        drawCurve(6, 118, 118, 20 + 256 + 10 + 118, 10 + 118, Color.BLACK, Curves.TOPLEFT);
//        drawCurve(6, 118, 118, 20 + 256 + 10, 10 + 118, Color.BLACK, Curves.TOPRIGHT);
//        drawCurve(6, 118, 118, 20 + 256 + 10, 10, Color.BLACK, Curves.BOTTOMRIGHT);
//        drawCurve(6, 118, 118, 20 + 256 + 10 + 118, 10, Color.BLACK, Curves.BOTTOMLEFT);
//
//        drawCurve(6, 118, 118, 12 + 118, 20 + 256 + 10 + 118, Color.BLACK, Curves.TOPLEFT);
//        drawCurve(6, 118, 118, 12, 20 + 256 + 10 + 118, Color.BLACK, Curves.TOPRIGHT);
//        drawCurve(6, 118, 118, 12, 20 + 256 + 10, Color.BLACK, Curves.BOTTOMRIGHT);
//        drawCurve(6, 118, 118, 12 + 118, 20 + 256 + 10, Color.BLACK, Curves.BOTTOMLEFT);
//
//        drawCurve(6, 118, 118, 20 + 256 + 10 + 118, 20 + 256 + 10 + 118, Color.BLACK, Curves.TOPLEFT);
//        drawCurve(6, 118, 118, 20 + 256 + 10, 20 + 256 + 10 + 118, Color.BLACK, Curves.TOPRIGHT);
//        drawCurve(6, 118, 118, 20 + 256 + 10, 20 + 256 + 10, Color.BLACK, Curves.BOTTOMRIGHT);
//        drawCurve(6, 118, 118, 20 + 256 + 10 + 118, 20 + 256 + 10, Color.BLACK, Curves.BOTTOMLEFT);
//
//        drawCurve(8, 256, 256, 10, 10, Color.BLACK, Curves.TOPLEFT);
//        drawCurve(8, 256, 256, 10 + 256, 10, Color.BLACK, Curves.TOPRIGHT);
//        drawCurve(8, 256, 256, 10 + 256, 10 + 256, Color.BLACK, Curves.BOTTOMRIGHT);
//        drawCurve(8, 256, 256, 10, 10 + 256, Color.BLACK, Curves.BOTTOMLEFT);



        scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT, BACKGROUND_COLOR);
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode pressed = keyEvent.getCode();
                if (pressed == KeyCode.Q) {
                    System.exit(0);
                }
            }
        });

        stage.setScene(scene);
    }

    public Color getColorFromRGB(int r, int g, int b)  {
        return Color.web("rgb(" + r + "," + g + "," + b + ")");
    }

    // x and y are the top left corner of the curve square rea
    public void drawCurve(int vertGap, int width, int height, int x, int y, Color color, Curves curve) {
        int numLines = (height/vertGap);
        int horizGap = width/numLines;

        Line line = new Line(0, 0, 0, 0);

        for (int i = 0; i <= numLines; ++i){
            if (curve == Curves.TOPLEFT) {
                line = new Line(x + horizGap*i, y, x, y + height - vertGap*i);
            }
            else if (curve == Curves.TOPRIGHT) {
                line = new Line(x + horizGap*i, y, x + width, y + vertGap*i);
            }
            else if (curve == Curves.BOTTOMLEFT) {
                line = new Line(x + horizGap*i, y + height, x, y + vertGap*i);
            }
            else if (curve == Curves.BOTTOMRIGHT) {
                line = new Line(x + horizGap*i, y + height, x + width , y + height - vertGap*i);
            }

            line.setStroke(color);
            root.getChildren().add(line);
        }
    }
}
