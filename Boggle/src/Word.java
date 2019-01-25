import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.HashSet;
import java.util.Objects;

/**
 * Tony Nguyen
 * CS-351
 * Used to represent a word.
 */
public class Word extends Text {

    private String word;
    private boolean valid = false;
    private int points;


    public Word(String str) {
        this.word = str.toLowerCase();
        this.points = str.length() - 2;

        setFont(Font.font(18));
        setText(word);
        setVisible(true);
        setFill(Color.RED);


        str.split("(?!^)");

    }

    public String getWord() {
        return word;
    }

    public int getPoints() {
        return points;
    }

    public void setPointZero() {
        points = 0;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
        update();
    }

    private void update() {
        setFill(valid ? Color.GREEN : Color.RED);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return Objects.equals(getWord(), word1.getWord());
    }

    @Override
    public String toString() {
        return " " + word + " ";
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWord());
    }
}
