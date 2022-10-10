package IA;

interface Sensor {
	float getValue(float[] sample);	
	void close();
	void run(boolean run);
	
}
