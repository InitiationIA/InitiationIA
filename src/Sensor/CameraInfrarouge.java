package Sensor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class CameraInfrarouge  {
	protected String sampleRed;
	protected DatagramSocket dsocket;
	protected String[] tabSampleRed;
	protected byte[] buffer;
	protected DatagramPacket packet;

	public CameraInfrarouge() {
		int port = 8888;
		try {

			dsocket = new DatagramSocket(port);
			InetAddress serveur = InetAddress.getByName("192.168.1.255");
			buffer = new byte[2048];
			packet = new DatagramPacket(buffer, buffer.length, serveur, port);
			dsocket.receive(packet);
			sampleRed = new String(buffer, 0, packet.getLength());
			tabSampleRed = sampleRed.split("\n");
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String[] getValue() throws IOException {
		packet.setLength(buffer.length);
		dsocket.receive(packet);
		sampleRed = new String(buffer, 0, packet.getLength());
		return tabSampleRed = sampleRed.split("\n");
	}

	public void close() {
		dsocket.close();

	}




}
