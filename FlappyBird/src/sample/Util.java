package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;

import static javafx.scene.text.Font.font;

public class Util {
    public static Text CreateTextNode(
            String text,
            String fontName, FontWeight fontWeight, Color color, int fontSize,
            int x, int y
    ) {
        Text node = new Text(text);
        node.setFont(font(fontName, fontWeight, FontPosture.REGULAR, fontSize));
        node.setX(x);
        node.setY(y);
        node.setFill(color);
        return node;
    }

    public static ImageView CreateImageView(Image img, double rotation, int x, int y) {
        ImageView imgView = new ImageView(img);
        imgView.setX(x);
        imgView.setY(y);
        imgView.setRotate(imgView.getRotate() + rotation);
        return imgView;
    }
}
