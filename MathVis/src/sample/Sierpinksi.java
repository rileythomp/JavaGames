package sample;

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

import java.util.HashMap;
import java.util.Map;

public class Sierpinksi {
    private final int SCREEN_WIDTH = 750;
    private final int SCREEN_HEIGHT = 750;
    private final Color BACKGROUND_COLOR = Color.WHITESMOKE;

    private int CELL_LEN = 2;
    private int BOARD_WIDTH = 300;

    private Stage stage;
    private Pane root;
    private Scene scene;

    private final ObservableList<String> options = FXCollections.observableArrayList(
            "Modulus 2",
            "Modulus 3",
            "Modulus 4",
            "Modulus 5",
            "Modulus 6",
            "Modulus 7",
            "Modulus 8"
            );

    private int[][] triangle;
    private int mod;

    private Map<Integer, Color> colorMap = new HashMap<Integer, Color>()
    {
        {
            put(0, Color.WHITESMOKE);
            put(1, Color.BLACK);
            put(2, Color.PINK);
            put(3, Color.LIGHTBLUE);
            put(4, Color.LIGHTGREEN);
            put(5, Color.GOLD);
            put(6, Color.MEDIUMPURPLE);
            put(7, Color.TURQUOISE);
        }
    };

    public Sierpinksi(Stage s) {
        stage = s;
        stage.setTitle("Sierpinksi Triangles");
        root = new Pane();
        triangle = new int[BOARD_WIDTH][BOARD_WIDTH];
        mod = 2;

        Text t = new Text(10, 730, "Select from the dropdown menu to change the modulus of Pascal's triangle");
        root.getChildren().add(t);

        final ComboBox comboBox = new ComboBox(options);
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                String val = (String)comboBox.getValue();
                mod = val.charAt(val.length() - 1) - '0';
                displayTriangle();
            }
        };
        comboBox.setOnAction(event);
        root.getChildren().add(comboBox);

        displayTriangle();

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

    private void displayTriangle() {
        for (int i = 0; i < BOARD_WIDTH; ++i) {
            for (int j = 0; j < BOARD_WIDTH; ++j) {
                triangle[i][j] = binomialCoefficientIsOdd(j, i);
                Rectangle rect = new Rectangle(30 + CELL_LEN*j, 50 + CELL_LEN*i, CELL_LEN, CELL_LEN);
                rect.setFill(colorMap.get(triangle[i][j]));
                root.getChildren().add(rect);
            }
        }
    }

    private int binomialCoefficientIsOdd(int x, int y) {
        if (x == 0 || y == 0) {
            return 1;
        }
        else {
            return (triangle[y-1][x] + triangle[y][x-1]) % mod;
        }
    }
}