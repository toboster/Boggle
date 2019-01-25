import javafx.scene.layout.Pane;

import java.util.*;


/**
 * Tony Nguyen
 * CS-351
 *
 * Object that represents a tray for the game known as Boggle.
 */
public class Tray {

    private final int PAD = 1;
    private int TILE_HEIGHT;
    private int TILE_WIDTH;
    private Tile[][] tray;
    private List<Tile> checkList = new ArrayList<>();
    // CHANGE THIS WHEN FINISHED
    private Random rand = new Random();

    public Tray(int height, int width) {
        TILE_HEIGHT = height + PAD;
        TILE_WIDTH = width + PAD;
        tray = new Tile[TILE_WIDTH][TILE_HEIGHT];
        initTray();
    }

    public boolean isWordValid(Word word) {
        return isWordValid(word.getWord());
    }

    /**
     * Used to check if given word exists in tray.
     *
     * @param str
     * @return
     */
    public boolean isWordValid(String str) {
        Iterator<Tile> boardIt = boardIter();
        boolean result = false;
        String[] strArray;
        str = str.toUpperCase();

        if (str == null) {
            System.out.println("Error, null str given in Tray : isWordValid");
        } else {
            strArray = str.split("(?!^)");
            while (boardIt.hasNext()) {
                Tile currTile = boardIt.next();
                if (isNeigbValid(strArray, currTile, 0)) {
                    result = true;
                    break;
                } else {
                    checkList.clear();
                }
            }
        }
        return result;
    }

    /**
     * Used by isWordValid(), recursively used to search depth first a valid
     * path given string broken up into array.
     *
     * @param str  String array used to check for completeness.
     * @param tile tile that is relevent to the call of the method.
     * @param indx indx used to keep track of completeness of path, which
     *             is based on whether all letters in str[] are valid.
     * @return
     */
    private boolean isNeigbValid(String[] str, Tile tile, int indx) {
        Iterator<Tile> neighbIt;
        boolean result = false;
        boolean tileCheck = !checkList.contains(tile);
        // Checks if last letter of the str is valid, only way method returns
        // true.
        if (tile.isEqual(str[indx]) && (indx == (str.length - 1)) &&
                tileCheck) {
            result = true;
        }
        // If given tiles letter is equal to str letter, found possible path.
        else if (tile.isEqual(str[indx]) && tileCheck) {
            neighbIt = getNeighbors(tile).iterator();
            checkList.add(tile);
            while (neighbIt.hasNext()) {
                // If true we have found a valid path.
                if (isNeigbValid(str, neighbIt.next(), indx + 1)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Code obtained from a youtube video by AlmaB on a Minesweeper Game.
     *
     * @param tile
     * @return
     */
    private List<Tile> getNeighbors(Tile tile) {
        List<Tile> neighbors = new ArrayList<>();

        int[] points = new int[]{
                -1, -1,
                -1, 0,
                -1, 1,
                0, -1,
                0, 1,
                1, -1,
                1, 0,
                1, 1
        };

        for (int i = 0; i < points.length; i++) {
            int dx = points[i];
            int dy = points[++i];

            int newX = tile.getX() + dx;
            int newY = tile.getY() + dy;

            neighbors.add(tray[newX][newY]);

        }
        return neighbors;
    }

    /**
     * Based on existing tiles that contain letter "Q", fills its
     * neighbors with "U" based on a probability.
     */
    private void specialQfill() {
        Iterator<Tile> boardIt = boardIter();

        while (boardIt.hasNext()) {
            Tile boardTile = boardIt.next();

            if (boardTile.isEqual("Q")) {
                Iterator<Tile> it = getNeighbors(boardTile).iterator();
                while (it.hasNext()) {
                    Tile tempNeighb = it.next();
                    if (rand.nextDouble() < .3 && !tempNeighb.isEmpty()) {
                        tempNeighb.setLetter("U");
                    }
                }
            }
        }
    }

    /**
     * @return Iterator that contains all valid tiles on the board.
     */
    private Iterator<Tile> boardIter() {
        List<Tile> tileList = new ArrayList<>();

        for (int c = 1; c < TILE_WIDTH - 1; c++) {
            for (int r = 1; r < TILE_HEIGHT - 1; r++) {
                tileList.add(tray[c][r]);
            }
        }
        return tileList.iterator();
    }

    /**
     * Fills the tray using fillTray() and specialQfill().
     */
    private void initTray() {
        List<String> alphabet = new ArrayList<>();

        for (char c = 'A'; c <= 'Z'; c++) {
            alphabet.add(Character.toString(c));
        }
        //Collections.shuffle(alphabet);
        fillTray(alphabet);
        specialQfill();
    }

    /**
     * Used by initTray, adds random letters to fill entire tray.
     *
     * @param alphabet list of entire alphabet.
     */
    private void fillTray(List<String> alphabet) {
        fillBorderBlank();
        for (int c = 1; c < TILE_WIDTH - 1; c++) {
            for (int r = 1; r < TILE_HEIGHT - 1; r++) {
                tray[c][r] = new Tile(c, r,
                        alphabet.get(rand.nextInt(alphabet.size())));
            }
        }
    }

    /**
     * Pads the borders of the board with empty tiles.
     */
    private void fillBorderBlank() {
        fillColBlank(0);
        fillColBlank(TILE_HEIGHT - 1);
        fillRowBlank(0);
        fillRowBlank(TILE_WIDTH - 1);
    }

    /**
     * fills specified collumn with empty tiles in tray.
     *
     * @param col collumn
     */
    private void fillColBlank(int col) {
        for (int r = 0; r < TILE_HEIGHT; r++) {
            if (tray[col][r] == null) {
                tray[col][r] = new Tile(col, r);
            }
        }
    }

    /**
     * fills specified row with empty tiles in tray.
     *
     * @param row
     */
    private void fillRowBlank(int row) {
        for (int c = 0; c < TILE_WIDTH; c++) {
            if (tray[c][row] == null) {
                tray[c][row] = new Tile(c, row);
            }
        }
    }

    /**
     * used to return a nod containing the tray.
     *
     * @return
     */
    public Pane display() {
        Pane trayPane = new Pane();
        for (int c = 1; c < TILE_WIDTH - 1; c++) {
            for (int r = 1; r < TILE_HEIGHT - 1; r++) {
                trayPane.getChildren().add(tray[c][r]);
            }
        }

        return trayPane;
    }

    public Tile[][] getTray() {
        return tray;
    }

    /**
     * goes through entire board and adds selected tiles in list.
     *
     * @param buildList
     */
    public void addBuildList(List<Tile> buildList) {
        Iterator<Tile> boardIt = boardIter();
        while (boardIt.hasNext()) {
            Tile tile = boardIt.next();
            if (tile.isSelected() && !buildList.contains(tile)) {
                buildList.add(tile);
            }
        }
    }

    /**
     * resets all selected tile in a given list to false.
     *
     * @param buildList
     */
    public void resetSelect(List<Tile> buildList) {
        for (Tile t : buildList) {
            t.setSelected(false);
        }
    }

    /**
     * @param buildList
     * @return
     */
    public String printTiles(List<Tile> buildList) {
        StringBuilder sb = new StringBuilder();
        for (Tile t : buildList) {
            sb.append(t.toString());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < TILE_WIDTH; i++) {
            for (int j = 0; j < TILE_HEIGHT; j++) {
                Tile tempT = tray[i][j];
                if (tempT == null) {
                    sb.append(" 0 ");
                } else if (tempT.isEmpty()) {
                    sb.append(" 1 ");
                } else {
                    sb.append(tray[i][j]);
                }
            }
            sb.append("\n");
        }
        return " tray= \n" + sb.toString();
    }


}
