/**
 * *************************************************************
 * Player.java 
 * Purpose: This class is meant to create the players of the game
 * and store their score, choice, and name 
 *
 * @author Dakin T. Werneburg
 * @version 1.0 3/20/2015
 ***************************************************************
 */
public class Player {
	//Instance Variables
	private int score = 0;
	private boolean currentPlayer = false;
	private String name = null;
	private String playersChoice = null;
	
	//Constructor
	public Player(String name){
		this.setName(name);
	}
	//Getter: returns the instance variable score
	public int getScore() {
		return score;
	}
	//Setter: takes an integer parameter and set the instance variable score
	public void setScore(int score) {
		this.score = score;
	}
	//Getter: currentPlayer
	public boolean isCurrentPlayer() {
		return currentPlayer;
	}
	//Setter:  currentPlayer
	public void setCurrentPlayer(boolean currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	//Getter:  playersChoice
	public String getPlayersChoice() {
		return playersChoice;
	}
	//Setter:  playersChoice
	public void setPlayersChoice(String playersChoice) {
		this.playersChoice = playersChoice;
	}
	//Getter:  name
	public String getName() {
		return name;
	}
	//Setter: name
	public void setName(String name) {
		this.name = name;
	}	
}//end of Player Class
