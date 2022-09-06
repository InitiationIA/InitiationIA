import lejos.hardware.*;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.*;
public class FirstClass {

	public static void main(String[] args) {
		
		GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();

		g.drawString("Hello world", 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		
		Delay.msDelay(5000);
	}

}
