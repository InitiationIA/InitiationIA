package Sensor;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

class Color implements Sensor{
	float[] sampleColor;
	EV3ColorSensor cs;
	
	Color(){
		 Brick brick = BrickFinder.getDefault();
		 Port s4 = brick.getPort("S4");
		 cs = new EV3ColorSensor (s4);
		 SampleProvider csRGB = cs.getRGBMode();
		 sampleColor = new float[csRGB.sampleSize()];
	}
	public float getValue() {
		return sample[0]; 
	}
	public void close() {
		cs.close();
	}
}
