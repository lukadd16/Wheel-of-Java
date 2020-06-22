package final30;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * RUN THIS FILE WHEN YOU WANT TO PLAY THE GAME
 * Date: 2020-06-18
 * Author: Lukas A.
 * Purpose: Create a playable text-based Wheel of Fortune game where a human player verses a computer
 * History: V1.0 - Bug-free version, plays perfectly, but had to cut some corners in the object oriented design due to time constraints
 */

public class WheelOfFortune {
	// Declare game constants
	static final List<String> ALPHA_LETTERS = new ArrayList<>(Arrays.asList(new String[] {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"}));
	static final List<String> ALPHA_VOWELS = new ArrayList<>(Arrays.asList(new String[] {"A","E","I","O","U"}));
	static final int VOWEL_COST = 250;
	static final List<String> ROUND_ONE_WHEEL = new ArrayList<>(Arrays.asList(new String[] {"2500","BANKRUPT","900","500","650","500","800","LOSETURN","700","750","650","BANKRUPT","600","500","550","600","750","700","500","650","600","700","600","500"}));
	static final List<String> ROUND_TWO_WHEEL = new ArrayList<>(Arrays.asList(new String[] {"3500","BANKRUPT","900","500","650","500","800","LOSETURN","700","750","650","BANKRUPT","600","500","550","600","750","700","500","650","600","700","600","500"}));
	static final List<String> ROUND_THREE_WHEEL = new ArrayList<>(Arrays.asList(new String[] {"5000","BANKRUPT","900","500","650","500","800","LOSETURN","700","750","650","BANKRUPT","600","500","550","600","750","700","500","650","600","700","600","500"}));
	
	// Add other classes when I need them
	static Scanner inputScan;
	static File ph;
	static File lb;
	static Scanner phScan;
	static PrintWriter wr;
	
	// Attempt to initialize java classes
	static {
		try {
			ph = new File("WOF_Phrases.txt");			 // Open file containing our phrases
			lb = new File("WOF_Leaderboard.txt");		 // Open file containing high scores
			//lb.createNewFile();						 // Create new file if it does not already exist
			phScan = new Scanner(ph);					 // Create file scanner for reading phrases
			inputScan = new Scanner(System.in);			 // Create user input scanner
		} catch(IOException e) {
			throw new ExceptionInInitializerError(e.getMessage());
		}
	}
	
	// Get a random phrase from a list that contains the category and all the phrases associated with it
	// Category is at index 0, therefore a random integer is generated from index 1 to the size of the list
	public static String getRandomPhrase(List<String> allPhrases) {
		Random rng = new Random();
		int randomIndex = rng.nextInt(allPhrases.size()-1) + 1; // Get random index between 1 (inclusive) and 25 (exclusive)
		
		return allPhrases.get(randomIndex).toUpperCase(); // Test this
	}
	
	// Repeatedly ask the user to pick one of three possible difficulties for the computer opponent
	// What each difficulty does is explained in the strings below
	public static double getComputerDifficulty() throws InterruptedException {
		System.out.println("\nIn this implementation of Wheel of Fortune, the computer is your opponent.");
		System.out.println("The computer will 'solve' puzzles according to the skill level you choose.");
		
		System.out.println("The higher the computer skill, the more challenging the game gets for you!");
		
		
		// Keep looping until a valid difficulty is entered
		while (true) {
			System.out.print("\nEnter the computer's skill level, (L)ow, (A)verage, (G)enius: ");
			String userInput = inputScan.nextLine();
			
			// Check if user input matches one of three valid options
			userInput = userInput.toUpperCase();
			
			if (userInput.equals("L")) {
				return 0.7; // Return the appropriate decimal value, tweak this as I start to play games
			} else if (userInput.equals("A")) {
				return 0.6;
			} else if (userInput.equals("G")) {
				return 0.55;
			}
			
			// If input matches none of the three conditions then send error message and repeat loop
			System.out.println("You did not enter a valid skill level, please try again.");
		}	
	}
	
	public static String spinWheel(int currentRound) throws InterruptedException {
		// Generate a random number between 0 (inclusive) and the length of one of the spinners (exclusive)
		Random rng = new Random();
		int randomIndex = rng.nextInt(ROUND_ONE_WHEEL.size());
		
		long timeIncrement = 0;
		for (int i = 0; i < ROUND_ONE_WHEEL.size(); i++) {
			TimeUnit.MILLISECONDS.sleep(timeIncrement);
			
			if (currentRound == 1) {
				System.out.println(ROUND_ONE_WHEEL.get(i));
			} else if (currentRound == 2) {
				System.out.println(ROUND_TWO_WHEEL.get(i));
			} else {
				System.out.println(ROUND_THREE_WHEEL.get(i));
			}
			timeIncrement += 1;
			
		}
		
		for (int i = 0; i <= randomIndex; i++) {
			TimeUnit.MILLISECONDS.sleep(timeIncrement);

			if (currentRound == 1) {
				System.out.println(ROUND_ONE_WHEEL.get(i));
			} else if (currentRound == 2) {
				System.out.println(ROUND_TWO_WHEEL.get(i));
			} else {
				System.out.println(ROUND_THREE_WHEEL.get(i));
			}
			
			// 'Slow' the wheel down by a more significant amount to create more
			// suspense/anticipation as the user sees the slots going by
			timeIncrement += 30;
		}
		
		if (currentRound == 1) {
			return ROUND_ONE_WHEEL.get(randomIndex);
		} else if (currentRound == 2) {
			return ROUND_TWO_WHEEL.get(randomIndex);
		} else {
			return ROUND_THREE_WHEEL.get(randomIndex);
		}
	}
	
	// Using the phrase for the current round and the letters that have already been guessed (if any), create a new string that:
	// replaces individual letters in the phrase with underscores to indicate that the letter has not been guessed,
	// replaces single-space characters in the phrase with slashes to add separation between words,
	// or adds letters from the phrase that have already been guessed for the contestant to see
	public static String hidePhrase(String roundPhrase, List<String> guessedLetters) {
		String str = "";
		
		for (int i = 0; i < roundPhrase.length(); i++) {
			if (ALPHA_LETTERS.contains(String.valueOf(roundPhrase.charAt(i)).toUpperCase()) && !(guessedLetters.contains(String.valueOf(roundPhrase.charAt(i)).toUpperCase()))) {
				str += "-";
			} else if (String.valueOf(roundPhrase.charAt(i)).equals(" ")) {
				str += " / ";
			} else {
				str += (String.valueOf(roundPhrase.charAt(i)));
			}
			// System.out.println(str); // DEBUG
		}
		
		return str;
	}
	
	// For use in displaying available consonants to the contestant
	public static List<String> getAvailableConsonants(List<String> guessedLetters) {
		List<String> lettersNoVowels = new ArrayList<>();
		
		lettersNoVowels.addAll(Arrays.asList(new String[] {"B","C","D","F","G","H","J","K","L","M","N","P","Q","R","S","T","V","W","X","Y","Z"}));
		
		if (guessedLetters.size() == 0) {
			return lettersNoVowels;
		} else {
			lettersNoVowels.removeAll(guessedLetters);
			return lettersNoVowels;
		}
	}
	
	// For use in displaying available vowels to the contestant
	public static List<String> getAvailableVowels(List<String> guessedLetters) {
		List<String> onlyVowels = new ArrayList<>();
		
		onlyVowels.addAll(Arrays.asList(new String[] {"A","E","I","O","U"}));
		
		if (guessedLetters.size() == 0) {
			return onlyVowels;
		} else {
			onlyVowels.removeAll(guessedLetters);
			return onlyVowels;
		}
	}
	
	// Set up the board for the contestant which will display the current category,
	// the covered up phrase and the letters that have not been guessed yet
	public static void showBoard(String category, String hiddenPhrase, List<String> guessedLetters) {
		// See methods for explanation
		List<String> availableConsonants = getAvailableConsonants(guessedLetters);
		List<String> availableVowels = getAvailableVowels(guessedLetters);
		
		// Sort the available letters in alphabetical order to improve readability
		Collections.sort(availableConsonants);
		Collections.sort(availableVowels);
		
		System.out.println();
		System.out.printf("Category: %s\n", category);
		System.out.printf("Phrase: %s\n", hiddenPhrase);
		System.out.printf("\nAvailable Consonants: %s\n", availableConsonants);
		System.out.printf("Available Vowels: %s", availableVowels);
		System.out.println();
	}
	
	// To add separation between different aspects of the game while it is being printed in the terminal
	public static void printTitleBar() {
		System.out.println();
		for (int i = 0; i < 40; i++) {
			System.out.print("-");
		}
		System.out.println();
	}
	
	public static void main(String[] args) throws InterruptedException, FileNotFoundException { // An exception that is thrown when another thread interrupts the current one while performing delay, this exception will never get thrown but is still required to declare
		// Game Setup
		List<String> guessedLetters = new ArrayList<>(); // List where letters guessed for each round's puzzle will be stored
		List<String> allPhrases = new ArrayList<>(); // Stores all the phrases from the txt file that contains them for use while the program is running
		List<String> highScores = new ArrayList<>(); // Contestant high scores will be read from a file into this list at the start of every game
		
		// Add all lines from our file to a list for future use
		while (phScan.hasNext()) {
			allPhrases.add(phScan.nextLine());
		}
		
		int currentRound; // Will store the current round as an integer (1 to 3), used in loop logic
		
		boolean playAgain = true; // Boolean will change depending on whether user wishes to play another 3 rounds
		
		// Main Game Loop
		while (playAgain == true) {
			// Create file scanner for reading high scores
			// Reason it is declared here is to account for the situation that the scanner reaches the end of the
			// file and if the user wants to play again, there is no way to elegantly return the scanner to the top of the
			// file (other than initializing the scanner again)
			Scanner lbScan = new Scanner(lb);
			
			// Get high scores from file
			while (lbScan.hasNext()) {
				highScores.add(lbScan.nextLine());
			}
						
			printTitleBar(); // See method for explanation	
			System.out.println("\nWELCOME TO WHEEL OF FORTUNE");
			printTitleBar();
			
			// Set up contestants
			System.out.print("\nWhat is your name? ");
			String userName = inputScan.nextLine();
			
			double computerDifficulty = getComputerDifficulty(); // See method for explanation
			
			// Create a WOFContestant type array for storing two contestants
			WOFContestant[] contestants = new WOFContestant[2];
			// Instantiate our human contestant with the required constructs as defined in the WOFHumanContestant.java class
			contestants[0] = new WOFHumanContestant(userName);
			// Instantiate our computer contestant with the required constructs as defined in the WOFComputerContestant.java class
			contestants[1] = new WOFComputerContestant("Computer Player", computerDifficulty);
			
			int contestantIndex = 0;  // To keep track of which contestant has control of the board
			WOFContestant contestant; // To store the contestant object that is currently in control
			
			currentRound = 1; // Set game to first round
			
			System.out.printf("\nWe are now starting Round #%d\n", currentRound);
			System.out.printf("\nYour opponent today is %s", contestants[1].name);
			
			// Get the first item in the list (which is the category), this allows for swapping out
			// the file between games and introducing a new set of phrases (which may belong to a different category)
			String gameCategory = allPhrases.get(0); 

			// See method for explanation
			String roundPhrase = getRandomPhrase(allPhrases);
			String hiddenPhrase;
			
			// Individual Round Loop
			// This loop will exit when all three rounds are finished
			while (currentRound <= 3) {
				contestant = contestants[contestantIndex]; // Set the current contestant instance that is in control
				
				// State the contestants earnings and show the board
				printTitleBar();
				System.out.printf("\n%s has control\n", contestant.name);
				
				// Update board				
				System.out.printf("\nEarnings this round: $%d", contestant.roundMoney);
				System.out.printf("\nEarnings in the game: $%d\n", contestant.totalMoney);
								
				// Call method to hide all letters in puzzle that haven't been guessed yet
				hiddenPhrase = hidePhrase(roundPhrase, guessedLetters);
				// Display the current status of the board
				showBoard(gameCategory, hiddenPhrase, guessedLetters);
				
				TimeUnit.SECONDS.sleep(3);
				
				// Depending on which contestant has control (human or CPU), call the appropriate method to get their move 
				String contestantMove;
				String rawWheelPrize = ""; // For storing the raw string spin value
				int wheelPrize = 500; // For storing numerical spin values
				
				// I started off right with the object oriented design but ran out of time and had to brute force
				// this part of it, the human and computer contestants are handled differently which was not the original intent
				// Ideally they should both be confined to the same rules but in this version the computer always spins the wheel
				// and then guesses a consonant, vowel or the phrase
				if (contestantIndex == 0) {
					// Prompt user what action they want to perform, spin, quit, solve or buy a vowel
					contestantMove = ((WOFHumanContestant) contestant).getMove();
					
					// Break the inner loop if user specifies they wish to end the game early
					if (contestantMove.equals("Q")) {
						break;
					}
					// Attempt to solve
					else if (contestantMove.equals("O")) {
						System.out.print("Type solution here: ");
						contestantMove = inputScan.nextLine();
					}
					// Buy a vowel
					else if (contestantMove.equals("B")) {
						System.out.print("Type the vowel here: ");
						contestantMove = inputScan.nextLine();
					}
					// Spin and get their consonant guess
					else {
						rawWheelPrize = spinWheel(currentRound);
						
						System.out.printf("\n%s spins ", contestant.name);
						
						// Attempt to convert the spinner value to an integer
						// If conversion fails then we know the spin is either
						// bankrupt or lose a turn, both of which will be handled accordingly
						try {
							wheelPrize = Integer.parseInt(rawWheelPrize);
							System.out.print("'$" + wheelPrize + "'\n");
						}
						catch (NumberFormatException e) {
							if (rawWheelPrize.equals("BANKRUPT")) {
								System.out.printf("'%s'\n", rawWheelPrize);
								// Call method belonging to WOFContestant to bankrupt the contestant
								contestant.goBankrupt();
								// Hand control over to next contestant
								contestantIndex = (contestantIndex + 1) % contestants.length;
								TimeUnit.SECONDS.sleep(3);
								continue;

							} else {
								System.out.printf("'%s'\n", rawWheelPrize);
								// Hand control over to next contestant
								contestantIndex = (contestantIndex + 1) % contestants.length;
								TimeUnit.SECONDS.sleep(3);
								continue;
							}
						}
						
						// Update board
						showBoard(gameCategory, hiddenPhrase, guessedLetters);
						
						System.out.print("\nGuess a consonant here: ");
						contestantMove = inputScan.nextLine();
						
						if (ALPHA_VOWELS.contains(contestantMove.toUpperCase())) {
							System.out.printf("\n'%s' is not a consonant, please try again.", contestantMove);
							TimeUnit.SECONDS.sleep(2);
							continue;
						}
					}
				} else {
					// Computer will spin the wheel each time they have control
					// Then they guess a letter (consonant or vowel) or solve the puzzle
					contestantMove = ((WOFComputerContestant) contestant).getMove(roundPhrase, guessedLetters, hiddenPhrase);
					
					TimeUnit.SECONDS.sleep(2);
					rawWheelPrize = spinWheel(currentRound);
					
					System.out.printf("\n%s spins ", contestant.name);
					
					try {
						wheelPrize = Integer.parseInt(rawWheelPrize);
						System.out.print("'$" + wheelPrize + "'\n");
					}
					catch (NumberFormatException e) {
						if (rawWheelPrize.equals("BANKRUPT")) {
							System.out.printf("'%s'\n", rawWheelPrize);
							contestant.goBankrupt();
							// Hand control over to next contestant
							contestantIndex = (contestantIndex + 1) % contestants.length;
							TimeUnit.SECONDS.sleep(3);
							continue;

						} else {
							System.out.printf("'%s'\n", rawWheelPrize);
							// Hand control over to next contestant
							contestantIndex = (contestantIndex + 1) % contestants.length;
							TimeUnit.SECONDS.sleep(3);
							continue;
						}
					}
				}

				contestantMove = contestantMove.toUpperCase();
				
				// If contestant move is one character long,
				// assume they are guessing a letter or attempting to buy a vowel
				if (contestantMove.length() == 1) {
					// If letter guessed by contestant is not found in the alphabet,
					// give them another chance to guess correctly
					if (!ALPHA_LETTERS.contains(contestantMove)) {
						System.out.println("\nGuesses should be alphabetical, please try again.");
						continue;
					}
					if (guessedLetters.contains(contestantMove)) { // If letter has already been guessed, contestant loses their turn
						System.out.printf("\n'%s' has already been guessed, you lost your turn. \n", contestantMove);
						TimeUnit.SECONDS.sleep(3);
						contestantIndex = (contestantIndex + 1) % contestants.length; // Move control to next contestant
						continue;
					}
					if (ALPHA_VOWELS.contains(contestantMove)) { // If letter is found in alphabet vowels, check if they have enough money to buy it
						if (contestant.roundMoney < VOWEL_COST) {
							System.out.printf("\nYou need $%d to buy a vowel, pick another option.\n", VOWEL_COST);
							TimeUnit.SECONDS.sleep(3);
							continue;
						}
						// If contestant has enough money to buy a vowel, remove the appropriate amount from their money for the current round
						else {
							contestant.roundMoney -= VOWEL_COST;
						}
					}
					
					guessedLetters.add(contestantMove.toUpperCase()); // Add contestant guess to list of all letters already guessed this round
					System.out.printf("\n%s guessed '%s'...", contestant.name, contestantMove); // Announce contestant move 
					
					// Loop to count occurances of contestant guess in the full phrase
					int letterCount = 0;
					for (int i = 0; i < roundPhrase.length(); i++) {
						if (contestantMove.equals(String.valueOf(roundPhrase.charAt(i)))) {
							letterCount++; // Increase tally when a letter is found
						}
					}
					
					if (letterCount > 0) {
						if (letterCount == 1) {
							System.out.printf("There is one '%s'\n", contestantMove);
						} else {
							System.out.printf("There are %d %s's\n", letterCount, contestantMove);
						}
						
						// Only award money if the letter guessed is a consonant
						if (!ALPHA_VOWELS.contains(contestantMove)) {
							contestant.addRoundMoney(letterCount * wheelPrize);
						}
						
						TimeUnit.SECONDS.sleep(3);						
						continue;
					} else {
						System.out.printf("There is no '%s'\n", contestantMove);
						TimeUnit.SECONDS.sleep(4);
					}
				}
				// If move is not exit and is not one character long,
				// then assume contestant is attempting to guess the phrase
				else {
					if (contestantMove.equalsIgnoreCase(roundPhrase)) {
						// If contestant earned less than $1000 in the round, increase their money for that round to $1000
						if (contestant.roundMoney < 1000) {
							contestant.roundMoney = 1000;
						}
						
						// Add money earned during the round to contestant total score
						System.out.printf("\n%s guessed the phrase! It was '%s'\n", contestant.name, roundPhrase);
						contestant.addGameMoney(contestant.roundMoney);
						// Reset round money for both contestants
						contestants[0].roundMoney = 0;
						contestants[1].roundMoney = 0;
						// Clear all letters that were guessed in the previous round
						guessedLetters.clear(); // Test this
						// Move to next round
						currentRound += 1;
						if (currentRound <= 3) {
							System.out.printf("\nWe are now starting Round #%d\n", currentRound);
						}
						// Generate a new phrase
						roundPhrase = getRandomPhrase(allPhrases);
						
						TimeUnit.SECONDS.sleep(3);
						continue;
					}
					// Guessed incorrectly, control moves to the next contestant
					else {
						System.out.printf("'%s' was not the phrase\n", contestantMove);
						contestantIndex = (contestantIndex + 1) % contestants.length;
						continue;
					}
				}
				
				// Pass control over to next contestant by setting the contestantIndex to the index of the next contestant in the list
				// If contestant 0 had control, value returned is 1
				// If contesant 1 had contro, value is reset to 0
				contestantIndex = (contestantIndex + 1) % contestants.length;
			}
						
			// Recap both contestant's total earnings
			WOFContestant contestantHuman = contestants[0];
			WOFContestant contestantCPU = contestants[1];
			System.out.println("\nEnd of Game Summary:");
			System.out.printf("%s earned a total of $%d", contestantHuman.name, contestantHuman.totalMoney);
			System.out.printf("\n%s earned a total of $%d", contestantCPU.name, contestantCPU.totalMoney);
			
			TimeUnit.SECONDS.sleep(2);
			
			// Determine the winner(s)
			// Assume that if two contestants have equal earnings, they both win
			int humanEarnings = contestantHuman.totalMoney;
			int cpuEarnings = contestantCPU.totalMoney;
			
			if (humanEarnings > cpuEarnings) {
				System.out.printf("\n\n%s wins the game!", contestantHuman.name);
				// Add contestant score to list of other high scores
				highScores.add("$" + contestantHuman.totalMoney + ", " + contestantHuman.name);
			} else if (humanEarnings < cpuEarnings) {
				System.out.printf("\n\n%s wins the game!", contestantCPU.name);
				// Add contestant score to list of other high scores
				highScores.add("$" + contestantCPU.totalMoney + ", " + contestantCPU.name);
			} else {
				System.out.printf("\n\n%s & %s both win the game!", contestantHuman.name, contestantCPU.name);
				// Add both contestant's scores to list of other high scores
				highScores.add("$" + contestantHuman.totalMoney + ", " + contestantHuman.name);
				highScores.add("$" + contestantCPU.totalMoney + ", " + contestantCPU.name);
			}
			
			TimeUnit.SECONDS.sleep(2);
			
			// Create print writer for use in storing high scores
			// Also display high scores in descending order (high score to low score)
			// Using a comparator for user to see
			System.out.println("\n\nHigh Scores:");
			Collections.sort(highScores, Collections.reverseOrder());
			// Collections.reverse(highScores); // Alternate method
						
			wr = new PrintWriter("WOF_Leaderboard.txt");
			for (int i = 0; i < highScores.size(); i++) { // Test this, does it overwrite or append?
				wr.println(highScores.get(i));
				System.out.println(highScores.get(i)); // DEBUG
			}
			
			highScores.clear(); // Clear high scores list to avoid duplicate entries if user decides to play again
			wr.close();			// Commit changes to leaderboard file
			
			TimeUnit.SECONDS.sleep(3);
			
			// Prompt user to play again, set boolean appropriately
			System.out.print("\nDo you want to play again? (Y)es or (N)o ");
			String userAgain = inputScan.nextLine();
			
			if (userAgain.equalsIgnoreCase("Y")) {
				playAgain = true;
				// Reset instance variables
				contestants[0].roundMoney = 0;
				contestants[1].roundMoney = 0;
				// Clear all letters that were guessed in the previous round
				guessedLetters.clear();
			} else {
				playAgain = false;
			}
		}
		
		// User has decided not to play again, send a thank you message
		System.out.println("\nThank you for playing Wheel of Fortune!");
		inputScan.close();
		phScan.close();
	}

}






