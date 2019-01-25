import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Tony Nguyen
 * CS-351
 *
 * Used to represent a Player, might change to abstract class.
 */
public class Player extends VBox {

    // It might be a good idea to clone objects being added to this node.
    private List<Word> guessList = new ArrayList<>();
    private int totalScore;
    private Text score;

    public Player() {
        score = new Text("Score = " + totalScore);

        score.setFont(Font.font(18));
        score.setFill(Color.RED);

        setPrefSize(300, BoggleGame.H);
        setAlignment(Pos.TOP_CENTER);

        getChildren().add(score);
    }

    /**
     * Given a Word, checks if it exists within the Dictionary.
     *
     * @param word
     * @return
     */
    public boolean dictCheck(Word word) {
        if (!guessList.contains(word)) {
            getChildren().add(word);
            guessList.add(word);
        }
        return Dictionary.getInstance().containsWord(word);
    }

    /**
     * Updates the total amount of points by going over guessList,
     * and recalculating total.
     */
    public void update() {
        totalScore = 0;
        for (Word w : guessList) {
            totalScore += w.getPoints();
        }
        score.setText("Score = " + totalScore);
    }

    /**
     * Used to build a string with all guessed Words.
     *
     * @return
     */
    public String printGuess() {
        StringBuilder sb = new StringBuilder();
        for (Word w : guessList) {
            sb.append(w.toString());
        }
        return sb.toString();
    }
}
