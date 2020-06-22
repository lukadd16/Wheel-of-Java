package final30;

public class WOFContestant {

	public String name;
	public int totalMoney = 0;
	public int roundMoney = 0;
	
	public WOFContestant(String name) {
		this.name = name;
		
	}
	
	// Note: static method is a method that belongs to all instances of a class rather than a specific one
	// We want it to belong to a specific instance as each instance is a contestant
	// Add money to the contestant's total for the entire game
	public void addGameMoney(int amount) {
		totalMoney += amount;
	}
	
	// Add money to the contestant for the current round they are playing
	public void addRoundMoney(int amount) {
		roundMoney += amount;
	}
	
	// Set contestant's money for the current round to 0 if they land on bankrupt
	public void goBankrupt() {
		roundMoney = 0;
	}

}
