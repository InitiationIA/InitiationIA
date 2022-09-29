package IA;

import org.lejos.ev3.sample.sensorfilter.SensorAndFilterSample;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Key;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.Delay;

public class Sensors {
	boolean [] samplePressed;
	float [] sampleDistance;
	float[] sampleColor;
	float[] sampleRed;
	
	public Sensors(){
		Brick brick = BrickFinder.getDefault();
		Port s1 = brick.getPort("S1");
	    Port s3 = brick.getPort("S3");
	    Port s4 = brick.getPort("S4");   
	    
	    EV3TouchSensor ts = new EV3TouchSensor(s3); 
	    EV3UltrasonicSensor us = new EV3UltrasonicSensor(s1);
	    EV3ColorSensor cs = new EV3ColorSensor (s4);
	    
		SampleProvider touch =ts.getTouchMode();
		SampleProvider distance = us.getDistanceMode();
		SampleProvider csRGB = cs.getRGBMode();
		SampleProvider csRed = cs.getRedMode();
		
		samplePressed = new boolean[touch.sampleSize()];
		sampleDistance = new float[distance.sampleSize()];
		sampleColor = new float[csRGB.sampleSize()];
		sampleRed = new float[csRed.sampleSize()];
		
		SampleProvider average = new MeanFilter(csRGB, 5);
		float[] sampleColorMean = new float[average.sampleSize()];
		
	}
	
	boolean isPressed(boolean[] sample) {
		return sample[0];
	}
	
	float getDistance(float[] sample) {
	    return sample[0];
	}
	
	int getColor(int[] sample) {
	    return sample[0]; 
	}


	float getRGBMode(float[] sample) {
	    return sample[0];
	 
	}
	
	public boolean[] getSamplePressed() {
		return samplePressed;
	}

	public float[] getSampleDistance() {
		return sampleDistance;
	}

	public float[] getSampleColor() {
		return sampleColor;
	}

	public float[] getSampleRed() {
		return sampleRed;
	}
	
	 public static void main(String[] args) {
	 }
	  
	  
}
