package wof;

import java.util.Scanner;

public class WOFHumanContestant extends WOFContestant {
	
	Scanner humanScan = new Scanner(System.in);
	
	public WOFHumanContestant(String name) {
		// Invoke base-class constructor
		super(name);
	}
	
	// Get human contestant's move and return it to the main game class
	public String getMove() {
		System.out.print("\nDo you wish to (S)pin, s(O)lve, (B)uy Vowel or (Q)uit? ");
		String humanMove = humanScan.nextLine();
		return humanMove.toUpperCase();
	}

}
