import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Tony Nguyen
 * CS-351
 * Manages the Boggle Game, specify text file used to represent
 * a dictionary within the code.
 */
public class BoggleGame extends Application {

    private final String wordsFile = "dictionary.txt";
    private Scanner sc;
    private Player p1;
    private Tray tray;

    public final static int W = 800;
    public final static int H = 600;
    public final static int TILE_SIZE = 80;

    private final int TRAY_WIDTH = 5;
    private final int TRAY_HEIGHT = 5;
    private Scene scene;
    private boolean gameOver = false;

    private boolean wordBuilt = false;
    private String wordBuild = "";
    private Word currentWord;
    private Text wbuildDisplay = new Text();
    private List<Tile> buildList = new ArrayList<>();

    private Text timer = new Text();
    // in minutes.
    private final static int TIME_LIMIT = 3;


    /**
     * Runs the game, specifically contains logic designed
     * for version 3 of Boggle game.
     */
    private void runGame() {
        tray.addBuildList(buildList);
        if (wordBuilt) {
            boolean inDiction = p1.dictCheck(currentWord);
            boolean inTray = tray.isWordValid(currentWord);
            // valid word
            if (inDiction && inTray) {
                currentWord.setValid(true);
            } else {
                currentWord.setPointZero();
            }
            p1.update();
            resetCurrWord();
        }
    }

    /**
     * @param currWord current word.
     * @param isDict   whether word exists in dictionary.
     * @param isTray   whether word is valid in tray.
     */
    private void printWordCheck(Word currWord, boolean isDict, boolean isTray) {
        String str = currWord.getWord();
        if (isDict) {
            System.out.println(str + " is a valid word in Dictionary.");
        } else {
            System.out.println(str + " is not a valid word Dictionary.");
        }
        System.out.println("Tray path for word is "
                + isTray);
    }

    /**
     * Prints out instructions, creates relevant objects to the version of
     * game.
     */
    private void setupGame() {
        this.sc = new Scanner(System.in);
        tray = new Tray(TRAY_HEIGHT, TRAY_WIDTH);
        Dictionary.setFile(wordsFile);
        wbuildDisplay.setText("Enter guess : ");
        System.out.println("Using " + wordsFile);
    }

    /**
     * builds the user interface, returns Parent node that consists of
     * relevant nodes.
     *
     * @return
     */
    private Parent createContent() {

        BorderPane root = new BorderPane();
        Pane trayPane = new Pane();
        Pane statusPane = new Pane();
        VBox statVBox = new VBox();

        this.p1 = new Player();

        // STATUS PANE
        statusPane.getChildren().add(p1);

        // ROOT
        root.setCenter(trayPane);
        root.setRight(statusPane);
        root.setPrefSize(W, H);

        // STATUS PANE
        statusPane.setPrefSize(300, H);
        statusPane.setStyle("-fx-background-color: black");
        statusPane.getChildren().add(statVBox);

        // TRAY
        trayPane.getChildren().add(tray.display());
        trayPane.getChildren().add(wbuildDisplay);
        trayPane.getChildren().add(timer);

        // WORD BUILD DISPLAY
        wbuildDisplay.setTranslateX(50);
        wbuildDisplay.setTranslateY(H - 100);
        wbuildDisplay.setFont(Font.font(24));
        wbuildDisplay.setFill(Color.RED);

        // TIMER
        timer.setFont(Font.font(24));
        timer.setFill(Color.BLACK);
        timer.setTranslateX(150);
        timer.setTranslateY(50);

        return root;
    }

    /**
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        setupGame();
        scene = new Scene(createContent());

        stage.setResizable(false);
        stage.setTitle("Boggle");
        stage.setScene(scene);
        stage.show();

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.ENTER) {
                currentWord = new Word(wordBuild);
                wbuildDisplay.setText("Enter guess : ");
                wordBuilt = true;
                tray.resetSelect(buildList);
                buildList.clear();
            }
        });

        AnimationTimer a = new AnimationTimer() {
            private long startTime = -1;

            @Override
            public void handle(long now) {
                if (startTime < 0) {
                    startTime = now;
                }
                Duration elapsed = Duration.ofNanos(now - startTime);
                timer.setText(String.format("elapsed time %2d:%02d",
                        elapsed.toMinutes(),
                        elapsed.getSeconds()));

                if (!gameOver) {
                    runGame();
                }
                update();

                if (elapsed.toMinutes() >= TIME_LIMIT) {
                    timer.setText("GAME OVER");
                    gameOver = true;
                    stop();
                }
            }
        };
        a.start();
    }

    private void update() {
        wordBuild = tray.printTiles(buildList);
        wbuildDisplay.setText("Enter guess : " + wordBuild);
    }


    private void resetCurrWord() {
        wordBuilt = false;
        wordBuild = "";
    }

    public static void main(String[] args) {
        launch(args);
    }
}
