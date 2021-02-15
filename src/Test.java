import java.util.Random;


public class Test {

	public static void main(String[] args) {
		  System.out.println(getRandomLocation());
	}
	public static String getRandomLocation() {
		
		String l = "";
        float x;
        float y;
        float z;

        //Generates Random floats for x, y ,z
        Random random = new Random();
        x = random.nextFloat() * 100;
        y = random.nextFloat() * 100;
        z = random.nextFloat() * 100;

        //Gets negative values if even
        if ((int) x % 2 == 0)  x = x * -1;
        if ((int) z % 2 == 0)  z = z * -1;        
        if ((int) y % 2 == 0)  y = y * -1;
  
        l = (x + " " + y + " " + z);
        
	return l ;
}
}
