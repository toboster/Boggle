import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Objects;


/**
 * Tony Nguyen
 * CS-351
 *
 * Used to represent a tile that contains a letter on the board game Boggle.
 */
class Tile extends StackPane {
    private int x, y;
    private String letter = "";
    private boolean selected = false;

    private Rectangle border = new Rectangle(
            BoggleGame.TILE_SIZE - 2,
            BoggleGame.TILE_SIZE - 2
    );
    private Text text = new Text();

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return getX() == tile.getX() &&
                getY() == tile.getY() &&
                Objects.equals(letter, tile.letter);
    }

    @Override
    public int hashCode() {

        return Objects.hash(getX(), getY(), letter);
    }

    public Tile(int x, int y, String letter) {
        this(x, y);
        this.letter = letter;

        text.setFont(Font.font(18));
        text.setText(letter);
        text.setVisible(true);
        text.setFill(Color.RED);

        border.setStroke(Color.LIGHTGRAY);

        setTranslateX(x * BoggleGame.TILE_SIZE);
        setTranslateY(y * BoggleGame.TILE_SIZE);

        getChildren().addAll(border, text);

        setOnMouseClicked(key -> toggle());
    }


    public void setSelected(boolean bool) {
        selected = bool;
        update();
    }

    public void toggle() {
        if (!selected) {
            selected = true;
            update();
        }
    }

    public void update() {
        border.setFill(selected ? Color.WHITE : Color.BLACK);
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public boolean isEmpty() {
        return letter.isEmpty();
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean isEqual(String str) {
        return letter.equals(str);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return letter;
    }
}
