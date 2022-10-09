package IA;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;


class Touch implements Sensor {
	boolean[] samplePressed;
	EV3TouchSensor ts;

	public Touch() {
		Brick brick = BrickFinder.getDefault();
		Port s3 = brick.getPort("S3");
		ts = new EV3TouchSensor(s3);
		SampleProvider touch = ts.getTouchMode();
		samplePressed=new boolean[touch.sampleSize()];
	}

	boolean getValue(boolean[] sample ) {
		return samplePressed[0];
	}

	@Override
	public float getValue(float[] sample) {
		return -1;
	}

	@Override
	public void close() {
		ts.close();
	}
}
