package Sensor;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

public class Color{
	float[] sampleColor;
	EV3ColorSensor cs;
	SampleProvider csRGB;
	/**
	 * Constructeur du capteur de couleur
	 */
	public Color(){
		 Brick brick = BrickFinder.getDefault();
		 Port s4 = brick.getPort("S4");
		 cs = new EV3ColorSensor (s4);
		 csRGB = cs.getColorIDMode();
		 sampleColor = new float[csRGB.sampleSize()];
	}
	/**
	 * @return une valeur correspondant à une couleur
	 */
	public float getValue() {
		csRGB.fetchSample(sampleColor, 0);
		return sampleColor[0];
	}
	/**
	 * arrete le capteur
	 */
	public void close() {
		cs.close();
	}
	
}
