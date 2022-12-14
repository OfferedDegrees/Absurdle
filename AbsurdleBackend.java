// Ryan Le
import java.util.*;

public class AbsurdleBackend {
   private Set<String> setField;
   private int length;

   // Constructs a game absurdle given a collection(dictionary) of words and a length
   // goes through the dictionary and puts all words of size length into the game(set)
   // if the length is shorter than 1, throws IllegalArgumentException
   // assumes words are lowercase and no non-words(Strings) are given
   // uses set so duplicate words are avoiding when constructing the collection
   public AbsurdleBackend(Collection<String> Dictionary, int length) {
      if (length < 1) {
         throw new IllegalArgumentException();
      }
      Set<String> set = new TreeSet<String>();
      for (String s : Dictionary) {
         if (s.length() == length) {
            set.add(s);
         }
      }
      this.setField = set;
      this.length = length;
   }
   
   // returns the entire set of current words
   public Set<String> words() {
      return this.setField;
   }

   // The comment for this method is provided. Do not modify this comment:
   // Params:
   //  String word -- the secret word trying to be guessed. Assumes word is made up of only
   //                 lower case letters and is the same length as guess.
   //  String guess -- the guess for the word. Assumes guess is made up of only
   //                  lower case letters and is the same length as word.
   // Exceptions:
   //   none
   // Returns:
   //   returns a string, made up of gray, yellow, or green squares, representing a
   //   standard wordle clue for the provided guess made against the provided secret word.
   public static String patternFor(String word, String guess) {
      Map<Character, Integer> counts = new TreeMap<Character, Integer>();
      for (int i = 0; i < word.length(); i++) {
         char ch = word.charAt(i);
         if (!counts.containsKey(ch)) {
            counts.put(ch, 1);
         } else {
            counts.put(ch, counts.get(ch) + 1);
         }
      }
      String[] pattern = new String[word.length()];
      for (int i = 0; i < word.length(); i++) {
         if (guess.charAt(i) == word.charAt(i)) {
            if (counts.get(word.charAt(i)) > 0) {
               pattern[i] = "????????";
               counts.put(word.charAt(i), counts.get(word.charAt(i)) - 1);
            }
         }
      }
      for (int i = 0; i < word.length(); i++) {
         for (int j = 0; j < guess.length(); j++) {
            if ((guess.charAt(j) == word.charAt(i)) && (pattern[j] == null)) {
               if (counts.get(word.charAt(i)) > 0) {
                  pattern[j] = "????????";
                  counts.put(word.charAt(i), counts.get(word.charAt(i)) - 1);
               }
            }  
         }
      }
      for (int x = 0; x < word.length(); x++) {
         if (pattern[x] == null) {
            pattern[x] = "??????";
         }
      }
      String output = "";
      for (int y = 0; y < pattern.length; y++) {
         String a = pattern[y];
         output += a;
      }
      return output;
   }
   
   // takes in a guess(string) and records this guess
   // if the current set of words is empty, throws IllegalStateException
   // if the guess is shorter than predefined length, throws IllegalArgumentException
   // goes through the current set of words and maps the patterns against the guess
   // records all the patterns against the guess and goes through all patterns
   // finds the group of words that is the largest and returns the pattern of that group. 
   // Patterns are mapped with patterns as keys and set of words as the values. As
   // the game goes on, the set of words is updated with each guess according to the pattern with 
   // the largest number of words. That set becomes the new limiting number of words to guess from
   // assumes all guesses are only lowercase
   public String record(String guess) {
      if (this.setField.isEmpty()) {
         throw new IllegalStateException("The set of words is empty");
      } else if (guess.length() != this.length) {
         throw new IllegalArgumentException("The guess length is not correct");
      }
   
      Map<String, Set<String>> record = new TreeMap<String, Set<String>>();
      for (String s : this.setField) {
         String key = patternFor(s, guess);
         if (!record.containsKey(key)) {
            record.put(key, new TreeSet<>());
         }
         record.get(key).add(s);
      }
      int max = 0;
      String key = "";
      for (String keys : record.keySet()) {
         int setSize = record.get(keys).size();
         if (setSize > max) {
            this.setField = record.get(keys);
            max = setSize;
            key = keys;
         }
      }
      return key;
   } 
}
