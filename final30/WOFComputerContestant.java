package final30;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WOFComputerContestant extends WOFContestant {

	public double difficulty;
	Random rng = new Random();

	public WOFComputerContestant(String name, double difficulty) {
		// Invoke bass-class constructor
		super(name);
		
		this.difficulty = difficulty; // This will contain a decimal that represents the CPU's
									  // probability of being able to solve the puzzle
									  // based on # of letters guessed divided by total # of letters in the phrase.
									  // If the resulting decimal is larger than the decimal for the computer's
									  // skill level, then the computer solves the puzzle, otherwise it just guesses a letter
	}
		
	// Returns a list of all possible letters that can be guessed by the computer
	// Also considers if the computer has enough money to guess a vowel and adds vowels to the list accordingly
	public List<String> getPossibleLetters(List<String> guessedLetters) {
		List<String> letters = new ArrayList<>();
		List<String> lettersNoVowels = new ArrayList<>();
		
		letters.addAll(Arrays.asList(new String[] {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"}));
		lettersNoVowels.addAll(Arrays.asList(new String[] {"B","C","D","F","G","H","J","K","L","M","N","P","Q","R","S","T","V","W","X","Y","Z"}));		
		
		if (guessedLetters.size() == 0 && this.roundMoney >= 250) {
			return letters;
		} else if (guessedLetters.size() == 0 && this.roundMoney < 250) {
			return lettersNoVowels;
		} else if (guessedLetters.size() > 0 && this.roundMoney >= 250) {
			letters.removeAll(guessedLetters);
			return letters;
		} else {
			lettersNoVowels.removeAll(guessedLetters);
			return lettersNoVowels;
		}
		
	}
		
	// Using the list of letters that the computer is able to guess,
	// pick and return random one to use as their guess for that turn
	
	// NOTE: When doing getMove, will need to calc percentage of guessing the phrase,
	// if percentage is below difficulty, then call this method and return a letter to the main game
	// if percentage is above difficulty, then they solve the phrase and return the full phrase to the main game
	// (will need to pass the full phrase to this method in order to be able to do that)
	public String getRandomLetter(List<String> possibleLetters) {		
		// Generate a random number between 0 (inclusive) and the
		// length of the list of possible letters to choose from (exclusive)
		int randomIndex = rng.nextInt(possibleLetters.size());
				
		String randomLetter = possibleLetters.get(randomIndex);
		
		return randomLetter;
	}
	
	public String getMove(String roundPhrase, List<String> guessedLetters, String hiddenPhrase) {
		String strippedRoundPhrase = roundPhrase.replaceAll(" ", "");
		
		// Get the total # of letters in the complete phrase
		int phraseLetterAmount = strippedRoundPhrase.length();
				
		// Perform Computer Probability Calculations
		
		// hiddenPhrase is needed to calculate the number of letters that are present on the board
		// This value is divided by the total number of letters in the phrase to calculate the computer's
		// probability of solving the puzzle (in order to actually make the game challenging)
		String strippedHiddenPhrase = hiddenPhrase.replaceAll(" ", "");  // Replace spaces with empty strings
		strippedHiddenPhrase = strippedHiddenPhrase.replaceAll("-", ""); // Replace underscores (hidden letters) with empty strings
		strippedHiddenPhrase = strippedHiddenPhrase.replaceAll("/", "");
				
		// What is left in strippedHiddenPhrase are all the letters that are currently visible on the board
		// Get the total number of letters visible on the board
		int guessedLetterAmount = strippedHiddenPhrase.length();
		
		double computerSolveRate = ((double)guessedLetterAmount / (double)phraseLetterAmount);
				
		if (computerSolveRate >= this.difficulty) { // If computer probability is larger than computer difficulty, then the computer "solves" the puzzle
			return roundPhrase.toUpperCase(); // Return the completed puzzle
		} else {
			// Computer guesses a letter
			List<String> possibleLetters = this.getPossibleLetters(guessedLetters); // Return a list of all possible guesses that can be made by the computer
						
			String computerGuess = this.getRandomLetter(possibleLetters); // Return a random letter from all possible guesses
	
			return computerGuess;
		}

	}

}
