import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class YahtzeeGame {
	SingleLinkedList playerOneSll = new SingleLinkedList();
	SingleLinkedList playerTwoSll = new SingleLinkedList();
	SingleLinkedList highScoreSll = new SingleLinkedList(); 
	Contestant playerOne = new Contestant("playerOne", 0);
	Contestant playerTwo = new Contestant("playerTwo", 0);
	
	
	
	public YahtzeeGame() throws IOException {
		createHighScoreSll(); 
		
		for(int i = 1; i <= 10; i++) { // GAME LOOP
			System.out.println("");
			System.out.println("Turn:" + i);
			
			// Adds random 3 number to the list in each round.
			playerOneSll = addThreeElementsToSll(playerOneSll);
			playerTwoSll = addThreeElementsToSll(playerTwoSll);
			
			gameScreen();
			//create an argument for the checkSituations method
			int tempSize1 = playerOneSll.size();
			int tempSize2 = playerTwoSll.size();
			
			checkSituations(tempSize1, tempSize2); // Checks Yahtzee and Large Straight situations
			
			System.out.println("----------------------------------------------");
		}
		
		Contestant winner = findWinner();
		gameEndScreen(winner);
		highScoreTable(winner);
		System.out.println("- - - High Score Table - - -");
		highScoreSll.sortedDisplay();
	}
	
	private void gameScreen() { //lists and scores of players are printed
		playerOneSll.display();
		System.out.println("\t"+ playerOne.getName() + " Score: " + playerOne.getScore());
		
		playerTwoSll.display();
		System.out.println("\t"+ playerTwo.getName() + " Score: " + playerTwo.getScore());
		System.out.println("");
	}
	
	private void highScoreTable(Contestant winner) throws IOException {// the winner is added to the High Score Table list, then the High Score Table txt is updated
		if(winner != null) {
			highScoreSll.sortedAdd(winner);
			updateHighScoreTableTxt();
		}
		else { // in case of a tie, two contestants are added to the list
			highScoreSll.sortedAdd(playerOne);
			highScoreSll.sortedAdd(playerTwo);
			updateHighScoreTableTxt();
		}
	}
	
	private void createHighScoreSll() throws FileNotFoundException { // create sorted high score list with sorted add method
		File highScoreTxt = new File("HighScoreTable.txt");
		Scanner reader = new Scanner(highScoreTxt);
		String [] line = new String[2];
		String name;
		int score;
		while (reader.hasNextLine()) { 
			String data = reader.nextLine();
	        line = data.split("#");
	        name = line[0];
	        score = Integer.parseInt(line[1]);
	        Contestant contestant= new Contestant(name,score);
	        highScoreSll.sortedAdd(contestant);
			}
		reader.close();
	}
	
	private void updateHighScoreTableTxt() throws IOException { //the top ten contestants on the High Score Table list are added to the High Score Table txt file
		FileWriter writer = new FileWriter("HighScoreTable.txt");
  	  	for(int i = 1; i <=10 ; i++){
  	  		Contestant tempContestant = (Contestant)highScoreSll.getElement(i);
  	  		writer.write(tempContestant.getName() + "#" + tempContestant.getScore() + "\n");
	      }
	      writer.close();
	}
	
	private void gameEndScreen(Contestant winner) { // the end screen of the game is printed
		System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - -");
		System.out.println("! ! ! The Game is over ! ! !");
		if(winner != null) {
			System.out.println("The winner is : " + winner.getName());
			System.out.println("The winner score is " + winner.getScore());
		}
			
		else
			System.out.println("          ! TIE !");
		System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - -");
	}
	
	private Contestant findWinner() { // the winner is found by comparing the scores
		Contestant winner;
		if(playerOne.getScore() > playerTwo.getScore())
			winner = playerOne;
		else if(playerTwo.getScore() > playerOne.getScore())
			winner = playerTwo;
		else
			winner = null;
		
		return winner;
	}
	
	private void checkSituations(int tempSize1, int tempSize2) { // yahtzee and large straight situations are checked for both players.
		
		playerOneSll = consecutiveNumbersSituation(playerOneSll,playerOne);
		playerTwoSll = consecutiveNumbersSituation(playerTwoSll, playerTwo);
		
		playerOneSll = yahtzeeSituation(playerOneSll, playerOne);
		playerTwoSll = yahtzeeSituation(playerTwoSll, playerTwo);
		
		
		if(tempSize1 != playerOneSll.size() || tempSize2 != playerTwoSll.size()) { // if there is a yahtzee or large straight situations, the new lists are printed to the screen again.
			playerOneSll.display();
			System.out.println("\t"+ playerOne.getName() + " Score: " + playerOne.getScore());
			playerTwoSll.display();
			System.out.println("\t"+ playerTwo.getName() + " Score: " + playerTwo.getScore());
			System.out.println("");
		}
	}
	
	private SingleLinkedList consecutiveNumbersSituation(SingleLinkedList playerSll, Contestant player) {
		if(playerSll.searchSixConsecutiveNumbers()) { // the large straight situation is checked.
			for(int i = 1; i <= 6 ; i++) {
				playerSll.removeOneElement(i);
			}
			player.setScore(player.getScore() + 30);
		}
		return playerSll;
	}
	
	private SingleLinkedList yahtzeeSituation(SingleLinkedList playerSll, Contestant player) { // the yahtzee situation is checked.
		for(int i = 1; i <=6; i++) {
			if(playerSll.searchSameElements(i)) {
				for(int j = 0; j < 4; j++) {
					playerSll.removeOneElement(i);
				}
				player.setScore(player.getScore() + 10);
			}
		}
		
		return playerSll;
	}
	
	private SingleLinkedList addThreeElementsToSll(SingleLinkedList playerSll) { // 3 randomly generated numbers are added to the players' lists
		for(int i = 0; i < 3; i++) {
			playerSll.add(createRandomNumber(1,6)); // Generates random number between 1-6 ([1,6])
		}
		return playerSll;
		
	}
	
	private int createRandomNumber(int minValue, int maxValue ) {
		Random random = new Random();
		int number = random.nextInt((maxValue - minValue) + 1) + minValue;
		return number;
	}
}
