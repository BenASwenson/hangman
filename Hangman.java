import java.util.ArrayList;
import java.util.Scanner;



public class Hangman {

    public static String word = "Word: \t";
    public static String random = "";
    public static String guessTop = "Guess: \t";
    public static String userGuess = "";
    public static ArrayList<String> userWrongGuesses = new ArrayList<>();
    public static String misses = "Misses: \t";
    public static String guessBottom = "Guess: \t";
    public static ArrayList<String> wordPlaceholder = new ArrayList<>();
    public static int missCount = 0;
    public static int countCorrectLetters = 0;

    public static Scanner scan = new Scanner(System.in);

    // words beneath this folding..
    //#region
    public static String[] words = {"ant", "baboon", "badger", "bat", "bear", "beaver", "camel",
    "cat", "clam", "cobra", "cougar", "coyote", "crow", "deer",
    "dog", "donkey", "duck", "eagle", "ferret", "fox", "frog", "goat",
    "goose", "hawk", "lion", "lizard", "llama", "mole", "monkey", "moose",
    "mouse", "mule", "newt", "otter", "owl", "panda", "parrot", "pigeon", 
    "python", "rabbit", "ram", "rat", "raven","rhino", "salmon", "seal",
    "shark", "sheep", "skunk", "sloth", "snake", "spider", "stork", "swan",
    "tiger", "toad", "trout", "turkey", "turtle", "weasel", "whale", "wolf",
    "wombat", "zebra"};
    //#endregion

    // gallows beneath this folding..
    //#region
    public static String[] gallows = {"+---+\n" +
    "|   |\n" +
    "    |\n" +
    "    |\n" +
    "    |\n" +
    "    |\n" +
    "=========\n",

    "+---+\n" +
    "|   |\n" +
    "O   |\n" +
    "    |\n" +
    "    |\n" +
    "    |\n" +
    "=========\n",

    "+---+\n" +
    "|   |\n" +
    "O   |\n" +
    "|   |\n" +
    "    |\n" +
    "    |\n" +
    "=========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|   |\n" +
    "     |\n" +
    "     |\n" +
    " =========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|\\  |\n" + //if you were wondering, the only way to print '\' is with a trailing escape character, which also happens to be '\'
    "     |\n" +
    "     |\n" +
    " =========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|\\  |\n" +
    "/    |\n" +
    "     |\n" +
    " =========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|\\  |\n" + 
    "/ \\  |\n" +
    "     |\n" +
    " =========\n"};
    //#endregion

    public static void main(String[] args) {

        try {
            while(missCount < 7){

                //print game start user interface
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\nLet's play hangman!\n");
            
                //Guess:
                System.out.println(guessTop);
                System.out.println("\n");
    
                //Gallows
                System.out.println(gallows[missCount]);
    
                //Word: 
                if (random == ""){
                    random = randomWord();
                    System.out.print(word);
                    
                } else {
                    System.out.print(word);
                }
    
                //Print initial underscore placeholders where letters will be filled in
                if (userGuess == ""){
                    printPlaceholders();
                } else {
                    updatePlaceholders(userGuess);
                }
                System.out.println("\n");
    
                //Misses:
                System.out.print(misses);
                for (String guess : userWrongGuesses) {
                    System.out.print(guess + " ");
                }
                System.out.println();
    
                //Guess:
                System.out.print(guessBottom);

                // to decide if win
                if (countCorrectLetters == random.length()){
                    System.out.println("\n\nYou Win!!");
                    break;
                } else if (missCount == 6) {
                    System.out.println("\n\nSorry, you lose! The answer was: " + random);
                    break;
                }
    
                // while(!scan.hasNext()) scan.next();
                userGuess = scan.next();
                guessTop = "Guess: \t" + userGuess;
    
                if (checkGuess(userGuess, random) == false){
                    userWrongGuesses.add(userGuess);
                    missCount++;                    
                }               
            }   

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
    // functions


    // Access Random Word
    public static String randomWord(){
        // random number identifying word in array
        int wordNumber = (int)(Math.random()*64);
        return words[wordNumber]; 
    }

    // Check User Guess
    public static boolean checkGuess(String letter, String word){
        
        // find the index of the word with the guessed letter
        int index = word.indexOf(letter);
        if (index != -1) {
            return true;
        }
        return false;   
    }

    // Printing of initial underscore placeholders for word to guess
    public static void printPlaceholders(){
        for (int i = 0; i < random.length(); i++){
            wordPlaceholder.add("_ ");
        }
        for (String place : wordPlaceholder){
            System.out.print(place + " ");
        }
        
    }

    // Update Placeholders after user guess
    public static void updatePlaceholders(String letter){

        // create array for guessed letters' indexed locations
        ArrayList<Integer> indexList = new ArrayList<>();

        // find indexed locations for matching letters
        int index = random.indexOf(letter);
        indexList.add(index);
        while(index != -1){
            index = random.indexOf(letter, index + 1);
            indexList.add(index);
        }
        int lastindex = indexList.size() - 1;
        indexList.remove(lastindex);
        countCorrectLetters += indexList.size();

        // fill placeholder with "__"
        for (int i = 0; i < random.length(); i++){
            if (wordPlaceholder.get(i).equals("")){
                wordPlaceholder.set(i, "_");
            } 
            
        }
        
        // iterate through correctly guessed letters' indexes and add to placeholder
        for (int i = 0; i < indexList.size(); i++) {
            wordPlaceholder.set(indexList.get(i), letter);
        }

        for (int i = 0; i < wordPlaceholder.size(); i++) {
            System.out.print(wordPlaceholder.get(i) + " ");
        }
        System.out.println("\n");

    }

}

    





