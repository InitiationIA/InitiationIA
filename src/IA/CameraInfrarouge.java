package IA;

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

class CameraInfrarouge implements Sensor {
	String sampleRed;
	DatagramSocket dsocket;
	String[] tabSampleRed;
	byte[] buffer;
	DatagramPacket packet;

	public CameraInfrarouge() {
		/*
		 * try { int port = 8888; dsocket = new DatagramSocket(port);
		 * 
		 * byte[] buffer = new byte[2048];
		 * 
		 * 
		 * DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		 * 
		 * while (true) { dsocket.receive(packet);
		 * 
		 * String msg = new String(buffer, 0, packet.getLength());
		 * 
		 * String[] palets = msg.split("\n");
		 * 
		 * for (int i = 0; i < palets.length; i = i+2) { String[] coord =
		 * palets[i].split(";"); int x = Integer.parseInt(coord[1]); int y =
		 * Integer.parseInt(coord[2]); sampleRed[i] = x; sampleRed[i+1] = y; }
		 * 
		 * 
		 * packet.setLength(buffer.length); }
		 * 
		 * } catch (Exception e) { System.err.println(e); }
		 */
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
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public String getCoord() {
		return sampleRed;
	}

	@Override

	public float getValue(float[] sample) {
		// TODO Auto-generated method stub
		return -1;
	}
	
	

	public void close() {
		dsocket.close();

	}
	
	public void getData() throws IOException{
		packet.setLength(buffer.length);
		dsocket.receive(packet);
		sampleRed = new String(buffer, 0, packet.getLength());
		tabSampleRed = sampleRed.split("\n");
	}

	static int findMySelf(String[] tabSampleRed) {
		int id = 1;
		return id;
	}
	
	static int findNearMe(String[] tabSampleRed) {
		return 2;
	}

	public static void main(String[] args) {
		CameraInfrarouge cir = new CameraInfrarouge();
		RegulatedMotor left = new EV3LargeRegulatedMotor(MotorPort.B);
		RegulatedMotor right = new EV3LargeRegulatedMotor(MotorPort.C);
	    left.setAcceleration(800);
	    right.setAcceleration(800);
	    left.forward();
        right.forward();
	    left.resetTachoCount();
	    right.resetTachoCount();
		Touch t = new Touch();
		while (t.getValue(t.samplePressed) == 0) {
			int tachleft = left.getTachoCount();
			int tachright = right.getTachoCount();
			
			left.resetTachoCount();
		    right.resetTachoCount();
			left.setSpeed(400-(tachleft-tachright)*2);
		    right.setSpeed(400-(tachright-tachleft)*2);
		    
			int id = findMySelf(cir.tabSampleRed);
			int ne = findNearMe(cir.tabSampleRed);
			String[] coord = cir.tabSampleRed[id].split(";");
			String[] direct = cir.tabSampleRed[ne].split(";");
			int selfx =  Integer.parseInt(coord[1]);
			int selfy =  Integer.parseInt(coord[2]);
			int directionx = Integer.parseInt(direct[1]); 
			int directiony = Integer.parseInt(direct[2]);
			
			Delay.msDelay(500);
			try {
				cir.getData();
			}catch(Exception e) {
				continue;
			}
			
			String[] coord2 = cir.tabSampleRed[id].split(";");
			directionx -= selfx;
			directiony -= selfy;
			selfx -= Integer.parseInt(coord2[1]);
			selfx -= Integer.parseInt(coord2[2]);
			double vectself = Math.sqrt(selfx*selfx+selfy*selfy);
			double vectdirect = Math.sqrt(directionx*directionx+directiony*directiony);
			double scalaire = directionx*selfx + directiony* selfy;
			double angle = Math.acos(scalaire/(vectself*vectdirect));
			if(selfy>directiony) {
				left.setSpeed((int) (left.getSpeed()-angle));
		        left.forward();
		        right.forward();
			}else if(directiony>selfy) {
		        right.setSpeed((int) (right.getSpeed()-angle));
		        left.forward();
		        right.forward();
			}else {
		         left.forward();
		         right.forward();
			}
			Delay.msDelay(500);
			try {
				cir.getData();
			}catch(Exception e) {
				continue;
			
		}
		left.stop();
        right.stop();
		left.close();
		right.close();
		}
	}


	@Override
	public void run(boolean run) {
		// TODO Auto-generated method stub
		
	}
}