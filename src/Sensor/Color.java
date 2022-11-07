package Sensor;

import java.util.Arrays;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Key;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Color{
	float[] sampleColor;
	EV3ColorSensor cs;
	SampleProvider csRGB;
	public Color(){
		 Brick brick = BrickFinder.getDefault();
		 Port s4 = brick.getPort("S4");
		 cs = new EV3ColorSensor (s4);
		 csRGB = cs.getRGBMode();
		 sampleColor = new float[csRGB.sampleSize()];
	}
	public float[] getValue() {
		csRGB.fetchSample(sampleColor, 0);
		return Arrays.copyOfRange(sampleColor, 0, csRGB.sampleSize()); 
	}
	public void close() {
		cs.close();
	}
	
	public static void main(String[] args) {
		 /* Steps to initialize a sensor */
	    Color cl = new Color();
	    /*
	     * Get the Red mode of the sensor.
	     * 
	     * In Red mode the sensor emmits red light and measures reflectivity of a
	     * surface. Red mode is the best mode for line following.
	     * 
	     * Alternatives to the method below are: sensor.getMode(1) or

	    Key escape = brick.getKey("Escape");
	    while (!escape.isDown()) {
	      /*
	       * Get a fresh sample from the filter. (The filter gets it from its
	       * source, the redMode)
	       */
	      cl.getValue();
	      /* Display individual values in the sample. */
	      for (int i = 0; i < cl.csRGB.sampleSize(); i++) {
	        System.out.print(cl.sampleColor[i] + " ");
	      }
	      cl.getValue();
	      System.out.println();
	      Delay.msDelay(50);
	    
	    /* When ready close the sensor */
	    cl.close();
	}
}
