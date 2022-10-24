package Sensor;

import java.util.Arrays;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

class Distance {
	float [] sampleDistance;
	EV3UltrasonicSensor us;
	SampleProvider distance;
	 Distance(){
		 Brick brick = BrickFinder.getDefault();
		 Port s1 = brick.getPort("S1");
		 us = new EV3UltrasonicSensor(s1);
		 distance = us.getDistanceMode();
		 sampleDistance = new float[distance.sampleSize()];
	}
	 
public void trier() {
		 double [] trie = Arrays.copyOf(sampleDistance, sampleDistance.length);
		 Arrays.sort(trie);
		 double q1 = trie[((int)(sampleDistance.length+3)/4-1)];
		 double q3 = trie[((int)(3*sampleDistance.length+1)/4-1)];
		 double borninf = trie[q1] - 1.5*(trie[q3]- trie[q1]);
		 double bornsup = trie[q3] - 1.5*(trie[q3]- trie[q1]);
		 int c = 0;
		 for(int i = 0; i<sampleDistance.length; i++) {
			 if(sampleDistance[i]<bornsup && sampleDistance[i]>borninf) {
				 trie[c] = sampleDistance[i];
				 c++;
			 }
		 }
		 sampleDistance = Arrays.copyOf(trie, c);
	 }
	 
	 public float getValue() {
		 distance.fetchSample(sampleDistance, 0);
		 return sampleDistance[0];
	}
	
	 

	
	public void close() {
		us.close();
	}
	
	
}
