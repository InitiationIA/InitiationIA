package Sensor;

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
	 
	 public float getValue() {
		 distance.fetchSample(sampleDistance, 0);
		 return sampleDistance[0];
	}

	
	public void close() {
		us.close();
	}
	
	
}
