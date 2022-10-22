package IA;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

class Distance implements Sensor{
	float [] sampleDistance;
	EV3UltrasonicSensor us;
	 Distance(){
		 Brick brick = BrickFinder.getDefault();
		 Port s1 = brick.getPort("S1");
		 us = new EV3UltrasonicSensor(s1);
		 SampleProvider distance = us.getDistanceMode();
		 sampleDistance = new float[distance.sampleSize()];
	}
	 
	 public float getValue() {
		    return sample[0];
	}

	@Override
	public void close() {
		us.close();
	}
}
