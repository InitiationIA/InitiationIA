package IA;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;


class Touch implements Sensor {
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

	@Override
	public float getValue(float[] sample) {
		return sample[0];
	}

	@Override
	public void close() {
		ts.close();
	}
	
	public void run(boolean run) {
		Touch ts = new Touch();
		while(run) {
			ts.touch.fetchSample(samplePressed, 0);
		}
		ts.close();
	}
}
