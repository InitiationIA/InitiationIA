package Sensor;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

/**
 * @author Gabriel et Vassili
 * Classe permettant l'utilisation du capteur de couleurs
 */
public class Color{
	private float[] sampleColor;
	private EV3ColorSensor cs;
	private SampleProvider csRGB;
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
