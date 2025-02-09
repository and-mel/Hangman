import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

// The Graphics class consists of static methods and constants used to draw
// things on an ANSI console. THE GRAPHICS CLASS IS ONLY COMPATIBLE WITH AN ANSI
// CONSOLE.
public class Graphics {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_GREEN_BG = "\u001B[42m";
    public static final String ANSI_YELLOW_BG = "\u001B[43m";
    
    // PRECONDITION: incorrectLetters >= 0
    public static String getHangman(int incorrectLetters, int max) {
        try {
            StringBuilder output = new StringBuilder();
            Scanner hangmanTxt = new Scanner(new File("resources/hangman"+
                (int)((double)incorrectLetters/max*4)+".txt"));
            while (hangmanTxt.hasNextLine()) {
                output.append(hangmanTxt.nextLine());
                if(hangmanTxt.hasNextLine())
                    output.append("\n");
            }
            hangmanTxt.close();
            return new String(output);
        } catch(FileNotFoundException e) {
            return "";
        }
    }
    
    public static String formatUnderlines(char[] wordSoFar) {
        String[] formattedWord = new String[wordSoFar.length];
        
        for(int i = 0; i < wordSoFar.length; i++) {
            formattedWord[i] = wordSoFar[i] == 0 
                ? "_" : Character.toString(wordSoFar[i]);
        }
        return String.join(" ", formattedWord);
    }
    
    public static void goTo(int x, int y) {
        char escCode = 0x1B;
        System.out.print(String.format("%c[%d;%df",escCode,x,y));
    }
    
    public static void prevLine() {
        System.out.print(String.format("\033[%dA",1));
    }
    
    public static void clearLine() {
        System.out.print("\033[2K");
    }
    
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
    }
}