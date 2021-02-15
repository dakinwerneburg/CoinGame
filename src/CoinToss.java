import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * *************************************************************
 * CoinToss.java 
 * Purpose: This class is to simulate and animate a coin toss by 
 * displaying a spinning coin using paintComponent and providing
 * the simulate results of landing on heads or tails 
 *
 * @author Dakin T. Werneburg
 * @version 1.0 3/20/2015
 ***************************************************************
 */

public class CoinToss extends JPanel implements ActionListener { 

	//Instance Variables
	private Timer t = new Timer(50,this);
	private Image[] animate = new Image[17];
	private int index = 0;
	private int randomNumber = 0;
	private String result;
	private BufferedImage images;

	//Constructor
	public CoinToss(){

	} 
	
	/* CoinAnimaton Method loads all images and sets the background to white
	 * There are no parameters and return type is void
	 */

	public void coinAnimation(){ 
		this.setBackground(Color.WHITE);
		for(int i= 0; i < animate.length; i++){
			try {
				images = ImageIO.read(new File("Images/" + i + ".jpg"));
				animate[i]  = new ImageIcon(images).getImage();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Images not Found, please load images");
				System.exit(0);
			}
		}  
	}
	
	/* flipping Method simulates the chance of landing on heads or tails,
	 * I chose to get 100 random numbers to provide a more accurate ratio.
	 * There are no parameters and returns the string value of heads or tails
	 * 
	 */
	public String flipping(){
		Random random = new Random();
		randomNumber = random.nextInt(100)+1;
		if (randomNumber < 50)
			result = "Heads";
		else
			result =  "Tails";
		return result;
	}

	/*
	 * paintComponent Method paint the current image to the panel,
	 * it takes the parameter g from the Graphics Class and return type is
	 * void
	 */
	public void paintComponent(Graphics g) {            	 	
		super.paintComponent(g);            	 		
		g.drawImage(animate[index], 175, 80, null);
	}
	
	//updates the image index, restarts timer to keep animating, and repaints the panel.
	@Override
	public void actionPerformed(ActionEvent arg0) {	
		if(index == 16){
			repaint();
			t.restart();
			index = 0;				

		} else {
			repaint();
			index++;					
		}				
	}
	
	//Getter: result
	public String getResult(){
		return this.result;
	}

	//startTimer method starts animation
	public void startTimer(){
		t.start();
	}
	
	//stopTimer method stops animation
	public void stopTimer(){
		t.stop();
	}
} //End of CoinToss Class
