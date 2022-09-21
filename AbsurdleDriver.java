// Class AbsurdleDriver is the driver program for the Absurdle program. It reads
// a dictionary of words to be used during the game and then plays a game with
// the user.

//Derived off code by Kevin Lin

import java.util.*;
import java.io.*;

public class AbsurdleDriver {
    public static final String DICTIONARY_FILE = "dictionary.txt";
    public static final int WORDLENGTH = 5;

    public static void main(String[] args) throws FileNotFoundException {
        // open the dictionary file and read dictionary into an ArrayList
        Scanner input = new Scanner(new File(DICTIONARY_FILE));
        List<String> dictionary = new ArrayList<String>();
        while (input.hasNext()) {
            String word = input.next();
            dictionary.add(word.toLowerCase());
        }

        // set up the AbsurdleBackendand start the game
        List<String> mainDict = Collections.unmodifiableList(dictionary);
        AbsurdleBackend absurdle = new AbsurdleBackend(mainDict, WORDLENGTH);
        if (absurdle.words().isEmpty()) {
            System.out.println("There are no words of that length.");
        } else {
            Scanner console = new Scanner(System.in);
            List<String> patterns = new ArrayList<>();
            while (!isFinished(patterns)) {
                System.out.print("> ");
                String guess = console.next().toLowerCase().strip();
                String pattern = absurdle.record(guess);
                if (!pattern.isEmpty()) {
                    patterns.add(pattern);
                    System.out.println(": " + pattern);
                    System.out.println();
                }
            }
            System.out.println("Absurdle " + patterns.size() + "/∞");
            System.out.println();
            System.out.println(String.join("\n", patterns));
        }
    }

    private static boolean isFinished(List<String> patterns) {
        if (patterns.isEmpty()) {
            return false;
        }
        String lastPattern = patterns.get(patterns.size() - 1);
        return !lastPattern.contains("⬜") && !lastPattern.contains("🟨");
    }
}