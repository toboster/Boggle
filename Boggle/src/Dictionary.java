import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

/**
 * Tony Nguyen
 * CS-351
 *
 * Encapsulates words to represent a dictionary, singleton class.
 */
public class Dictionary {

    private static Dictionary instance = null;
    private static String file = "";
    private HashSet<Word> dictionary = new HashSet<>();


    private Dictionary() {
        fillDict();
    }

    public static void setFile(String str) {
        file = str;
    }

    public static Dictionary getInstance() {
        if (instance == null) {
            instance = new Dictionary();
        }
        return instance;
    }

    /**
     * Method expects that for every new line within the text file,
     * there is a single unique word, fills dictionary with
     * Words from a text file.
     *
     * @throws IOException
     */
    private void fillDict() {
        try (BufferedReader in = new BufferedReader(
                new FileReader(file));) {
            String lineWord;
            while ((lineWord = in.readLine()) != null) {
                dictionary.add(new Word(lineWord));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "dictionary=" + dictionary +
                '}';
    }

    public boolean containsWord(Word word) {
        return dictionary.contains(word);
    }

    private void add(Word word) {
        dictionary.add(word);
    }
}
