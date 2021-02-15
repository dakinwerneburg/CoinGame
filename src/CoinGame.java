import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * *************************************************************
 * CoinGame.java 
 * Purpose: This is a classic coin flipping game.  The object of 
 * the game is to call heads or tails then flip the coin.  The 
 * one that gets 3 correct guesses, wins the game.
 *
 * @author Dakin T. Werneburg
 * @version 1.0 3/20/2015
 ***************************************************************
 */

public class CoinGame  {

	//Instance Variables
	private JFrame frame = new JFrame("Flip-A-Coin");
	private CardLayout layout = new CardLayout();
	private JPanel mainPanel = new JPanel();
	private JPanel introPanel = new JPanel(new BorderLayout());
	private JPanel menuPanel = new JPanel();
	private JPanel simulateToss = new JPanel(new BorderLayout());
	private JPanel coinSelection = new JPanel(new BorderLayout());
	private JPanel startFlipCoinPanel = new JPanel(new BorderLayout());
	private JPanel coinAnimation = new JPanel(new BorderLayout());
	private JPanel displayFlippedCoin = new JPanel(new BorderLayout());
	private JPanel computerResults = new JPanel(new BorderLayout());
	private JPanel endGameMenu = new JPanel();	
	private JPanel displayCoinResult = new JPanel(new BorderLayout());
	private JPanel scoreBoard = new JPanel(new BorderLayout()); 
	private JButton person2person = new JButton( "Player vs. Player");	
	private JButton person2Computer = new JButton( "Player vs. Computer");
	private JButton simulate = new JButton( "Simulate 100 tosses");	
	private JLabel resultLabel = new JLabel();
	BufferedImage intro = null;
	BufferedImage heads = null;
	BufferedImage tails = null;
	private JLabel headsLabel;
	private JLabel tailsLabel;
	private JLabel introLabel;
	private Font font1 = new Font("SansSerif", Font.BOLD, 15);
	private Player player1 = new Player("Player 1");
	private Player player2 = new Player("Player 2");
	private Player cpu = new Player("CPU");
	private ArrayList<Player> players = new ArrayList<Player>();
	private CoinToss coinToss = new CoinToss();
	private CoinToss autoToss = new CoinToss();	
	private int score1 = 0;
	private int score2 = 0;
	private String coinResult;
	
		//Constructor
		public CoinGame(){	
			initializeWindow();
			introDisplay();
			mainMenu();	
			coinSelection();
			flip();		
			updateScoreBoard();	
		}	
			
		private void initializeWindow(){
			frame.add(mainPanel);
			frame.setVisible(true);
			frame.setSize(500, 500);
			frame.setResizable(false);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setBackground(Color.LIGHT_GRAY);

			//Layouts the 10 different panels to be viewed
			frame.add(mainPanel);
			mainPanel.setLayout(layout);
			layout.show(mainPanel, "0");	
			mainPanel.add(introPanel, "1");
			mainPanel.add(menuPanel, "2");
			mainPanel.add(simulateToss, "3");
			mainPanel.add(coinSelection, "4");
			mainPanel.add(startFlipCoinPanel, "5");
			mainPanel.add(coinAnimation, "6");			
			mainPanel.add(displayFlippedCoin, "7");
			mainPanel.add(computerResults, "8");			
			mainPanel.add(endGameMenu, "9");
			
			//Loads and check images
			try {
				intro = ImageIO.read(new File("Images/Intro.jpg"));
				heads = ImageIO.read(new File("Images/coin-heads.jpg"));
				tails = ImageIO.read(new File("Images/coin-tails.jpg"));
				introLabel = new JLabel(new ImageIcon(intro));  
				headsLabel = new JLabel(new ImageIcon(heads));
				tailsLabel = new JLabel(new ImageIcon(tails));
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Images not Found, please load images");
				System.exit(0);
			}			
		}//end of method
	
		
		// VIEW 1 - INTRODUCTIOON:  Displays greeting message that describes the program to user
		private void introDisplay(){
			JPanel messagePanel = new JPanel();			
			introPanel.add(messagePanel, BorderLayout.NORTH);
			JTextArea introduction = new JTextArea();
			messagePanel.add(introduction);
			introduction.setBorder(new EmptyBorder(10,0,0,0));
			introduction.setSize(400, 200);
			introduction.setFont(new Font("Times Rman", Font.BOLD,15));
			introduction.setLineWrap(true);
			introduction.setWrapStyleWord(true);
			introduction.setBackground(Color.getColor(null));
			introduction.setCaretColor(frame.getBackground());	
			introduction.setText("This is a classic coin flipping game.  "
					+ "The object of the game is to call heads or tails "
					+ "then flip the coin.  The one that gets 3 correct  "
					+ "guesses, wins the game. \n");						
			introPanel.add(introLabel, BorderLayout.CENTER);
			
			//Creates an OK button and switches to next screen once clicked
			JPanel messagePanelOk = new JPanel();
			introPanel.add(messagePanelOk,BorderLayout.SOUTH);
			JButton okButton = new JButton("OK");
			messagePanelOk.add(okButton);
			okButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					layout.show(mainPanel, "2");	
				}
			});
		}//end of method

		
		//VIEW 2 - MAIN MENU:  Player choose number of players
		private void mainMenu(){	
			//Label that asks player to make a choice
			MenuListener button = new MenuListener();
			mainPanel.add(menuPanel, "2");
			JLabel numPlayers = new JLabel("Please Make Choice");
			menuPanel.add(numPlayers);
			numPlayers.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
			numPlayers.setFont(new Font("Times Rman", Font.BOLD,15));

			//Player vs Player Button
			menuPanel.add(person2person, BorderLayout.SOUTH);	
			person2person.setPreferredSize(new Dimension(400,50));
			person2person.setForeground(Color.blue);
			person2person.addActionListener(button);

			//Player vs Computer Button
			menuPanel.add(person2Computer, BorderLayout.SOUTH);
			person2Computer.setPreferredSize(new Dimension(400,50));
			person2Computer.setForeground(Color.blue);
			person2Computer.addActionListener(button);

			//Simulator Button, simulates 100 coin tosses
			menuPanel.add(simulate, BorderLayout.SOUTH);
			simulate.setPreferredSize(new Dimension(400,50));
			simulate.setForeground(Color.blue);
			simulate.addActionListener(button);	
		}//end of method
			
		//MENU BUTTON LISTENER for the main menu
		private class MenuListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				players.removeAll(players);
				//Two Player mode
				if (e.getSource() == person2person){
					players.add(player1);
					players.add(player2);
					player1.setCurrentPlayer(true);					
					layout.show(mainPanel, "4");
				//One Player Mode
				}else if(e.getSource()== person2Computer){	
					players.add(player1);
					players.add(cpu);
					player1.setCurrentPlayer(true);					
					layout.show(mainPanel, "4");
				//Simulation
				}else if(e.getSource()== simulate){
					simulateToss.removeAll();
					simuateCoinToss();
					layout.show(mainPanel, "3");
				}
				updateScoreBoard();				
			}		
		}//end of method
		
		
		//VIEW 3 - SIMULATION:  Simulates 100 tosses and displays them on the screen
		private void simuateCoinToss(){
			mainPanel.add(simulateToss, "3");
			frame.setSize(650, 600);
			String heads = "Heads";
			int headTotal = 0;
			int tailsTotal = 0;	
			
			//Displays animation of coin spinning 
			JPanel autoEast = new JPanel(new BorderLayout());
			simulateToss.add(autoEast, BorderLayout.CENTER);
			autoEast.add(autoToss, BorderLayout.CENTER);
			autoToss.startTimer();
			autoToss.coinAnimation();		
			autoToss.setBackground(Color.LIGHT_GRAY);	
			
			//Displays the Back and Quit again Buttons
			JPanel autoWest = new JPanel(new BorderLayout());
			autoWest.setBorder(BorderFactory.createEmptyBorder(200, 25, 200, 25));
			simulateToss.add(autoWest, BorderLayout.WEST);
			JButton back = new JButton("Back to Main Menu");
			JButton quit = new JButton("Quit");
			
			//Back Button
			autoWest.add(back, BorderLayout.SOUTH);
			back.setPreferredSize(new Dimension(200,50));
			back.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.setSize(500, 500);
					layout.show(mainPanel, "2");					
				}				
			});
			//Quit Button
			autoWest.add(quit, BorderLayout.NORTH);
			quit.setPreferredSize(new Dimension(50,50));
			quit.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}				
			});
			
			//Displays the results of the 100 coin Tosses
			JTextArea autoResults = new JTextArea();
			autoResults.setBorder(BorderFactory.createEmptyBorder(10, 50, 50, 50));
			autoResults.setLineWrap(true);
			autoResults.setWrapStyleWord(true);
			autoResults.setEditable(false);
			autoResults.setBackground(Color.LIGHT_GRAY);
			simulateToss.add(autoResults, BorderLayout.SOUTH);
			for(int i =0; i < 10; i++){
				autoToss.flipping();
				for(int j = 1; j < 10; j++){						
					autoResults.append(autoToss.getResult() + ", ");
					autoToss.flipping();
					if (heads == autoToss.getResult())
						headTotal++;
					else
						tailsTotal++;
				}					
				autoToss.flipping();
				autoResults.append(autoToss.getResult() + ",\n");
				if (heads == autoToss.getResult())
					headTotal++;
				else
					tailsTotal++;
			}
			autoResults.append("\n\tTotal Heads: " + headTotal 
					+ "\n\tTotal Tails: " +tailsTotal);					
		}//end of method
				
		//VIEW 4 - COIN SELECTION:  Creates two buttons and ask player to choice a coin
		private void coinSelection(){
			
			updateScoreBoard();
			//Northern Display area
			mainPanel.add(coinSelection, "4");
			JPanel showCoins = new JPanel(new BorderLayout());		
			JLabel coinSelectionMessage = new JLabel();
			showCoins.add(coinSelectionMessage, BorderLayout.NORTH);	
			coinSelectionMessage.setText("     PLEASE CHOOSE HEADS OR TAILS");
			coinSelectionMessage.setFont(font1);	
			scoreBoard.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			
			//Displays each the heads side and the coin side of coin
			coinSelection.add(showCoins, BorderLayout.CENTER);
			showCoins.setBorder(BorderFactory.createEmptyBorder(25, 100, 20, 100));
			showCoins.setBackground(Color.WHITE);
			showCoins.add(new JLabel(new ImageIcon(heads)), BorderLayout.WEST);			
			showCoins.add(new JLabel(new ImageIcon(tails)), BorderLayout.EAST);
					
			//Displays the heads button and tails button for player to choose
			JButton headsButton = new JButton("Heads");
			JButton tailsButton = new JButton("Tails");
			JPanel headTailSelection = new JPanel(new BorderLayout());
			coinSelection.add(headTailSelection, BorderLayout.SOUTH);
			headTailSelection.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));			
			headTailSelection.add(headsButton, BorderLayout.WEST);
			headTailSelection.add(tailsButton, BorderLayout.EAST);
			headsButton.setPreferredSize(new Dimension(200,50));
			tailsButton.setPreferredSize(new Dimension(200,50));						
			headsButton.addActionListener(new ActionListener(){					
				@Override
				public void actionPerformed(ActionEvent e) {
					if(player1.isCurrentPlayer())
						player1.setPlayersChoice("Heads");													
					if(player2.isCurrentPlayer())							
						player2.setPlayersChoice("Heads");
					layout.show(mainPanel, "5");					
				}
			});			
			tailsButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					updateScoreBoard();
					if(player1.isCurrentPlayer())							
						player1.setPlayersChoice("Tails");														
					if(player2.isCurrentPlayer())							
						player2.setPlayersChoice("Tails");							
					layout.show(mainPanel, "5");		
				}
			});
		}//end of method
								
		private void flip(){				
			mainPanel.add(startFlipCoinPanel, "5");
			JPanel topBorderPanel = new JPanel();
			topBorderPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
			topBorderPanel.setBackground(Color.LIGHT_GRAY);
			startFlipCoinPanel.add(topBorderPanel, BorderLayout.NORTH);
			
			//Center View:  coinPanel is the area the coin appears before spinning	
			JPanel coinPanel = new JPanel(new BorderLayout());
			JLabel imgLabel3 = new JLabel(new ImageIcon(heads));
			startFlipCoinPanel.add(coinPanel, BorderLayout.CENTER);				
			coinPanel.setBackground(Color.WHITE);
			coinPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 50, 100));
			coinPanel.add(imgLabel3);
			
			//Displays the flip coin Button that starts the coin animation
			JButton flipButton = new JButton("Flip Coin");
			coinPanel.add(flipButton, BorderLayout.SOUTH);	
			flipButton.setPreferredSize(new Dimension(100,50));		
			flipButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {					
					coinToss.startTimer();
					layout.show(mainPanel, "6");		
				}
			});
			
			//Displays the animated spinning coin
			mainPanel.add(coinAnimation,"6");
			coinToss.coinAnimation();
			JPanel animatedCoinPanel = new JPanel();
			animatedCoinPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
			animatedCoinPanel.setBackground(Color.LIGHT_GRAY);
			coinAnimation.add(animatedCoinPanel, BorderLayout.NORTH);														
			coinAnimation.add(coinToss, BorderLayout.CENTER);	
			
			/*
			 * STOP BUTTON:  This uses the the timer from the CoinToss Class
			 * to start the animation; calls its flipping() method to
			 * get the result of either heads or tails; if guess was corrects,
			 * updates players score and changes player in the Player class.
			 */
			JButton stop = new JButton("Stop");
			coinAnimation.add(stop, BorderLayout.SOUTH);
			stop.setPreferredSize(new Dimension(250,50));										
			stop.addActionListener(new ActionListener(){						
				@Override
				public void actionPerformed(ActionEvent e) {					
					displayCoinResult.remove(headsLabel);
					displayCoinResult.remove(tailsLabel);
					coinToss.stopTimer();
					coinToss.flipping();
					coinResult = coinToss.getResult();
					if(coinResult == "Heads")						
						displayCoinResult.add(headsLabel, BorderLayout.CENTER);													
					else 
						displayCoinResult.add(tailsLabel, BorderLayout.CENTER);									
					resultLabel.setText(coinResult);
					resultLabel.setFont(font1);
					resultLabel.setBorder(BorderFactory.createEmptyBorder(75, 133, 0, 100));
					displayCoinResult.add(resultLabel,BorderLayout.NORTH);	
					if(player1.isCurrentPlayer()){
						if (player1.getPlayersChoice() == coinResult){
							score1++;
							player1.setScore(score1);
						}
					}else if (player2.isCurrentPlayer()){
						if (player2.getPlayersChoice() == coinResult){
							score2++;
							player2.setScore(score2);									
						}	
					}
					if(players.contains(cpu)){
						
						if(player1.isCurrentPlayer()){
							player1.setCurrentPlayer(false);
							cpu.setCurrentPlayer(true);
						}else{
							player1.setCurrentPlayer(true);
							cpu.setCurrentPlayer(false);
						}
							
					}else if (players.contains(player2)){
						if(player1.isCurrentPlayer()){
							player1.setCurrentPlayer(false);
							player2.setCurrentPlayer(true);
						}else{
							player1.setCurrentPlayer(true);
							player2.setCurrentPlayer(false);
						}
					}	
					updateScoreBoard();
					layout.show(mainPanel, "7");						
				}
			});

			//Updates the display to show Next Player Button
			mainPanel.add(displayFlippedCoin, "7");
			JPanel panel3 = new JPanel();
			panel3.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
			panel3.setBackground(Color.LIGHT_GRAY);
			displayFlippedCoin.add(panel3, BorderLayout.NORTH);
			displayFlippedCoin.add(displayCoinResult, BorderLayout.CENTER);
			displayCoinResult.setBackground(Color.WHITE);
			displayCoinResult.setBorder(BorderFactory.createEmptyBorder(0, 100, 50, 100));
			
			JButton nextPlayerButton = new JButton("Next Player");
			displayCoinResult.add(nextPlayerButton, BorderLayout.SOUTH);
			nextPlayerButton.setPreferredSize(new Dimension(100,50));
			nextPlayerButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {

					if((cpu.isCurrentPlayer())){
						computer();	
						layout.show(mainPanel, "8");
					}else{
						layout.show(mainPanel, "4");
					}	
					updateScoreBoard();
				}
			});		
		}//end of method	
			
		//SCOREBOARD:  Updates the score and displays current player
		private void updateScoreBoard(){
			if (player1.getScore() == 3 || player2.getScore() == 3 || cpu.getScore() == 3){
				endofGameMenu();
				layout.show(mainPanel, "10");
			}else{
				scoreBoard.removeAll();				
				coinSelection.add(scoreBoard, BorderLayout.NORTH);
				JLabel currentPlayer = new JLabel();
				currentPlayer.setBorder(BorderFactory.createEmptyBorder(10, 150, 0, 0));
				currentPlayer.setFont(font1);
				if(player1.isCurrentPlayer())
					currentPlayer.setText("Current Player:      " + player1.getName());					
				else if (player2.isCurrentPlayer())		
					currentPlayer.setText("Current Player:      " + player2.getName());
				else if (cpu.isCurrentPlayer())		
					currentPlayer.setText("Current Player:      " + player2.getName());
				JLabel p1 = new JLabel(player1.getName() + " Score:          " + score1);
				p1.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 25));
				if(players.contains(cpu)){
					JLabel c = new JLabel(cpu.getName() + " Score:              " + score2);
					c.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 50));
					scoreBoard.add(c, BorderLayout.EAST);	
				}
				if(players.contains(player2)){
					JLabel p2 = new JLabel(player2.getName() + " Score:              " + score2);
					p2.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 50));
					scoreBoard.add(p2, BorderLayout.EAST);	
				}											
				scoreBoard.add(currentPlayer, BorderLayout.NORTH);
				scoreBoard.add(p1, BorderLayout.WEST);							
				scoreBoard.setBackground(Color.LIGHT_GRAY);
			}
		}//end of method
			
		
		//Artificial Intelligence:  Simulates a move by the computer and displays results
		private void computer(){
				updateScoreBoard();
				if(cpu.isCurrentPlayer()){
				computerResults.removeAll();
				mainPanel.add(computerResults, "8");
				JPanel panel4 = new JPanel();
				panel4.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
				panel4.setBackground(Color.LIGHT_GRAY);
				computerResults.add(panel4, BorderLayout.NORTH);
				computerResults.setBackground(Color.WHITE);
				JButton continueButton = new JButton("Continue");
				continueButton.setPreferredSize(new Dimension(100,50));
				computerResults.add(continueButton, BorderLayout.SOUTH);
				continueButton.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						updateScoreBoard();					
						layout.show(mainPanel, "4");										
					}					
				});
				Random random = new Random();
		    	int randomNumber = random.nextInt(100)+1;
		    	if (randomNumber < 50)
		    		cpu.setPlayersChoice("Heads");		    				    		   
		    	else
		    		cpu.setPlayersChoice("Tails");   
				coinToss.flipping();
				if(coinToss.getResult() == "Heads")
					computerResults.add(headsLabel, BorderLayout.CENTER);
				else 
					computerResults.add(tailsLabel, BorderLayout.CENTER);								
				JLabel label = new JLabel("Computer Picked: " + cpu.getPlayersChoice());
				label.setFont(font1);				
				panel4.add(label,BorderLayout.NORTH);	
				label.setBackground(Color.LIGHT_GRAY);
		    	if (cpu.getPlayersChoice() == coinToss.getResult()){
					score2++;
					cpu.setScore(score2);					
		    	}
		    	player1.setCurrentPlayer(true);
				cpu.setCurrentPlayer(false);
				updateScoreBoard();
				}
			}//end of method
				
		
		//END OF GAME MENU:  Asks player to play again or quit
		private void endofGameMenu(){	
			endGameMenu.removeAll();
			mainPanel.add(endGameMenu, "10");
			
			//Displays final score
			JLabel finalScore = new JLabel();
			endGameMenu.add(finalScore);
			finalScore.setFont(font1);
			finalScore.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
			
			//Displays message to the player about winning or loosing
			JLabel winner = new JLabel();
			endGameMenu.add(winner);
			winner.setForeground(Color.RED);
			winner.setFont(new Font("Times Rman", Font.BOLD,20));
			if(players.contains(cpu)){				
				finalScore.setText(player1.getName() + ":   " + player1.getScore() + "                  " 
						+ cpu.getName() + ":     " + cpu.getScore());
			
				if(player1.getScore() > cpu.getScore())
					winner.setText("Congradulations Player 1, You Won!");
				else
					winner.setText("Sorry the computer won, better luck next time");			
			}else{
				finalScore.setText(player1.getName() + ":        " + player1.getScore() + "               " 
						+ player2.getName() + ":     " + player2.getScore());
				if(player1.getScore() > player2.getScore())
					winner.setText("Congradulations Player 1, You Won!");
				else
					winner.setText("Congradulations Player 2, You Won!");	
			}
				
			//Displays message for player to choose to play again or quit
			JLabel option = new JLabel("Please Make Choice");			
			endGameMenu.add(option, BorderLayout.NORTH);
			option.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
			option.setFont(new Font("Times Rman", Font.BOLD,15));

			//This creates and displays the Button to play again
			JButton playAgain = new JButton("Play Again?");
			endGameMenu.add(playAgain, BorderLayout.EAST);
			playAgain.setPreferredSize(new Dimension(400,50));
			playAgain.setForeground(Color.blue);
			playAgain.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					updateScoreBoard();
					layout.show(mainPanel, "2");					
					player1.setScore(0);
					player2.setScore(0);
					cpu.setScore(0);
					score1 = 0;
					score2 = 0;	
				}				
			});
					
			//This creates the Button to quit the Game
			JButton quitGame = new JButton("Quit Game?");
			endGameMenu.add(quitGame, BorderLayout.WEST);
			quitGame.setPreferredSize(new Dimension(400,50));
			quitGame.setForeground(Color.blue);	
			quitGame.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);			
				}				
			});				
		}
		
		
		//MAIN METHOD
		public static void main(String[] args) {
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					 new CoinGame();
				}
			});			
		}		
}//END OF GAME CLASS

