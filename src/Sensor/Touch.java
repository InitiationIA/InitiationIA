package Sensor;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;


class Touch {
	float[] samplePressed;
	EV3TouchSensor ts;
	SampleProvider touch;

	public Touch() {
		Brick brick = BrickFinder.getDefault();
		Port s3 = brick.getPort("S3");
		ts = new EV3TouchSensor(s3);
		touch = ts.getTouchMode();
		samplePressed=new float[touch.sampleSize()];
	}

	public float getValue() {
		touch.fetchSample(samplePressed, 0);
		return samplePressed[0];
	}


}
