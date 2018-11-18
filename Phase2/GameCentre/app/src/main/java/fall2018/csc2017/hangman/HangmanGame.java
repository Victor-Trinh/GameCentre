package fall2018.csc2017.hangman;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a game of a Hangman.
 */
public class HangmanGame {

    /**
     * Represents the state of a letter in hangman.
     * Can be unused, correct, or incorrect
     */
    public enum LETTER_STATE{
        UNUSED, CORRECT, INCORRECT
    }

    /**
     * Answer to the game. Not case sensitive
     */
    private String answer;

    /**
     * The category the answer belongs to.
     */
    private String category;

    /**
     * Keep track of letters and their state
     */
    private HashMap<Character, LETTER_STATE> letters;


    /**
     * Create a new HangmanGame
     * @param answer The answer. The game will not be case sensitive; answer can only contain alphabetic letters
     */
    public HangmanGame(String answer, String category){
        // Check if answer makes a valid game
        Pattern p = Pattern.compile("[a-z]+");
        Matcher m = p.matcher(answer);
        if (!m.find())
            throw new InvalidParameterException("Hangman game can only have alphabetic letters, and at least one letter");

        this.answer = answer.toUpperCase();
        this.category = category;
        letters = new HashMap<>();

        // put all the letters as unused
        for(int i = (int)'A';  i < (int)'Z' + 1; i++){
            letters.put((char)i, LETTER_STATE.UNUSED);
        }

    }

    /**
     * Retrieves the state of the letter in the HangmanGame
     * @param letter Letter to check. Must be alphabetic letter
     * @return The state of the letter
     */
    public LETTER_STATE getLetterState(Character letter){
        letter = Character.toUpperCase(letter);
        if(letters.containsKey(letter)){
            return letters.get(letter);
        }
        throw new InvalidParameterException("Alphabet letter expected");
    }

    /**
     * Make a letter guess in hangman
     * @param letter The user's guess
     * @return returns if the user makes a correct guess.
     */
    public boolean makeGuess(Character letter){
        letter = Character.toUpperCase(letter);
        if(letters.containsKey(letter)) {
            if (answer.contains(letter.toString())) {
                letters.put(letter, LETTER_STATE.CORRECT);
                return true;
            } else {
                letters.put(letter, LETTER_STATE.INCORRECT);
                return false;
            }
        }
        throw new InvalidParameterException("Alphabet letter expected");
    }

}
