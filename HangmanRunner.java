import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

// The HangmanRunner brings that Game and Graphics classes together to form
// a Hangman game on the console.
public class HangmanRunner {
    private static Scanner input = new Scanner(System.in);
    private static Game game;
    private static double startTime;
    
    public static void run(int incorrectGuesses) {
        startTime = 0;
        String lastGuess = "";
        game = new Game(randomizeWord());
        while(game.getIncorrectAmount() < incorrectGuesses && !game.hasWon()) {
            redraw(incorrectGuesses, lastGuess);
            System.out.print("\n"+Graphics.formatUnderlines(game.getWordSoFar()).replaceAll(lastGuess, Graphics.ANSI_YELLOW_BG+lastGuess+Graphics.ANSI_RESET)+"\n\nGuess: ");
            String guess = input.nextLine();
            if(startTime == 0)
                startTime = System.currentTimeMillis();
            if(guess.equals("/rs")) {
                run(incorrectGuesses);
                return;
            } else if(guess.length() == 1) {
                lastGuess = guess.toUpperCase();
                game.guess(guess.charAt(0));
            } else {
                game.guess(guess);
            }
        }
        double endTime = (System.currentTimeMillis()-startTime)/1000;
        redraw(incorrectGuesses, "");
        System.out.println("\n"+String.join(" ", game.getWord().split("(?!^)"))+"\n");
        double score = 10000/(90+endTime)-5*game.getIncorrectAmount();
        score = score > 100 ? 110 - (int)endTime : score;
        
        if(game.hasWon()) {
            System.out.println("You won in "+(System.currentTimeMillis()-startTime)/1000+" sec with a score of "+Graphics.ANSI_YELLOW+Math.round(score*100)/100.0+" ("+grade(score)+")"+Graphics.ANSI_RESET+"!");
        } else {
            System.out.println("You lost! ("+(System.currentTimeMillis()-startTime)/1000+" sec) (F)");
        }
    }
    
    private static void redraw(int ig, String lg) {
        Graphics.clearScreen();
        Graphics.goTo(0,0);
        for(char c : game.getIncorrectLetters()) {
            System.out.print((lg.equals(""+c) ? Graphics.ANSI_YELLOW_BG+c+Graphics.ANSI_RESET+" " : c + " "));
        }
        System.out.println(" ("+game.getIncorrectAmount()+" / "+ig+") Time: "+Graphics.ANSI_YELLOW+(startTime == 0 ? 0 : System.currentTimeMillis()-startTime)/1000+Graphics.ANSI_RESET+"\n"+Graphics.getHangman(game.getIncorrectAmount(), ig));
    }
    
    private static String randomizeWord() {
        try {
            ArrayList<String> possibleWords = new ArrayList<>();
            Scanner wordsTxt = new Scanner(new File("resources/words.txt"));
            while (wordsTxt.hasNextLine()) {
                String line = wordsTxt.nextLine();
                if(!line.trim().startsWith("#") && !line.trim().isEmpty())
                    possibleWords.add(line.trim());
            }
            wordsTxt.close();
            return possibleWords.get((int)(Math.random()*possibleWords.size()));
        } catch(FileNotFoundException e) {
            return "FILE NOT FOUND";
        }
    }
    
    private static String grade(double score) {
        if (score >= 105) {
            return "S+";
        } else if (score >= 100) {
            return "S";
        } else if (score > 95) {
            return "A+";
        } else if (score >= 90) {
            return "A";
        } else if (score > 85) {
            return "B+";
        } else if (score >= 80) {
            return "B";
        } else if (score > 75) {
            return "C+";
        } else if (score >= 70) {
            return "C";
        } else if (score >= 60) {
            return "D+";
        } else {
            return "D";
        }
    }
}