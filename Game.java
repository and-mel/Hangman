import java.util.Set;
import java.util.HashSet;

// The Game class represents a game of Hangman. This class only knows about
// when the player has won. Losing mechanics are handled outside of this class.
public class Game {
    public static final String ALLOWED_CHARS = "QWERTYUIOPASDFGHJKLZXCVBNM";
    
    private String word;
    private Set<Character> correctLetters = new HashSet<>();
    private Set<Character> incorrectLetters = new HashSet<>();
    private boolean hasWon = false;
    
    public Game(String word) {
        this.word = word.toUpperCase();
    }
    
    public boolean guess(char letter) {
        letter = Character.toUpperCase(letter);
        if(ALLOWED_CHARS.indexOf(letter) == -1)
            return false;
        if(word.indexOf(letter) == -1) {
            incorrectLetters.add(letter);
            return false;
        }
        correctLetters.add(letter);
        hasWon = new String(getWordSoFar()).equals(word);
        return true;
    }
    
    public boolean guess(String word) {
        word = word.toUpperCase();
        if(word.equals(this.word)) {
            hasWon = true;
            return true;
        }
        return false;
    }
    
    public String getWord() {
        return word;
    }
    
    public boolean hasWon() {
        return hasWon;
    }
    
    public int getIncorrectAmount() {
        return incorrectLetters.size();
    }
    
    public char[] getWordSoFar() {
        char[] output = new char[word.length()];
        for(int i = 0; i < word.length(); i++) {
            output[i] = correctLetters.contains(word.charAt(i)) || ALLOWED_CHARS.indexOf(word.charAt(i)) == -1 ? word.charAt(i) : 0; // 0 is NUL character
        }
        return output;
    }
    
    public HashSet<Character> getCorrectLetters() {
        return new HashSet<Character>(correctLetters);
    }
    
    public HashSet<Character> getIncorrectLetters() {
        return new HashSet<Character>(incorrectLetters);
    }
}