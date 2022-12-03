package Sensor;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;


/**
 * @author Gabriel et Vassili
 * Classe permettant l'utilisation du capteur tactile
 */
public class Touch {
	private float[] samplePressed;
	private EV3TouchSensor ts;
	private SampleProvider touch;

	/**
	 * constructeur du capteur correspondant au toucher
	 */
	public Touch() {
		Brick brick = BrickFinder.getDefault();
		Port s3 = brick.getPort("S3");
		ts = new EV3TouchSensor(s3);
		touch = ts.getTouchMode();
		samplePressed=new float[touch.sampleSize()];
	}

	/**
	 * @return la valeur 1.0 si le capteur detecte un objet sinon 0.0
	 */
	public float getValue() {
		touch.fetchSample(samplePressed, 0);
		return samplePressed[0];
	}
	/**
	 * ferme le capteur
	 */
	public void close() {
		ts.close();
	}

}
