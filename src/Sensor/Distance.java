package Sensor;

import java.util.Arrays;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;


/**
 * @author Gabriel et Vassili
 * Classe du capteur ultrason permettant de envoyer une distance 
 */
public class Distance {
	private float[] sampleDistance;
	private EV3UltrasonicSensor us;
	private SampleProvider distance;

	/**
	 * Constructeur du capteur ultrason
	 */
	public Distance() {
		Brick brick = BrickFinder.getDefault();
		Port s1 = brick.getPort("S1");
		us = new EV3UltrasonicSensor(s1);
		distance = us.getDistanceMode();
		sampleDistance = new float[distance.sampleSize()];
	}
	
	
	/**
	 * Fonction devant calculer la médiane des valeurs du capteur ultrason
	 * cette fonction n'a pas été utilisé
	 */
	public void trier() {
		float[] trie = Arrays.copyOf(sampleDistance, sampleDistance.length);
		Arrays.sort(trie);
		double q1 = trie[((int) (sampleDistance.length + 3) / 4 - 1)];
		double q3 = trie[((int) (3 * sampleDistance.length + 1) / 4 - 1)];
		double borninf = trie[(int) q1] - 1.5 * (trie[(int) q3] - trie[(int) q1]);
		double bornsup = trie[(int) q3] - 1.5 * (trie[(int) q3] - trie[(int) q1]);
		int c = 0;
		for (int i = 0; i < sampleDistance.length; i++) {
			if (sampleDistance[i] < bornsup && sampleDistance[i] > borninf) {
				trie[c] = sampleDistance[i];
				c++;
			}
		}
		sampleDistance = Arrays.copyOf(trie, c);
	}

	/**
	 * @return la distance mesurée
	 */
	public float getValue() {
		//this.trier();
		distance.fetchSample(sampleDistance, 0);
		return sampleDistance[0];
	}

	/**
	 * arrete le capteur
	 */
	public void close() {
		us.close();
	}

}
