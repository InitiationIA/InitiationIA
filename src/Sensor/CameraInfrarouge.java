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

/**
 * @author Vassili 
 * Classe permettant l'utilisation des données d'un camera infrarouge 
 * via un socket
 */
public class CameraInfrarouge  {
	private String sampleRed;
	private DatagramSocket dsocket;
	private String[] tabSampleRed;
	private byte[] buffer;
	private DatagramPacket packet;

	/**
	 * Constructeur de la camera infrarouge
	 */
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

	/**
	 * @return un tableau de String sous la forme [id;Y;X\n]
	 * @throws IOException si il y a une erreur de lecture du socket sur le flux
	 */
	public String[] getValue() throws IOException {
		packet.setLength(buffer.length);
		dsocket.receive(packet);
		sampleRed = new String(buffer, 0, packet.getLength());
		return tabSampleRed = sampleRed.split("\n");
	}

	/**
	 * ferme le capteur
	 */
	public void close() {
		dsocket.close();

	}




}
