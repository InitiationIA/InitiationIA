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

class CameraInfrarouge  {
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

	public void getValue() throws IOException {
		packet.setLength(buffer.length);
		dsocket.receive(packet);
		sampleRed = new String(buffer, 0, packet.getLength());
		tabSampleRed = sampleRed.split("\n");
	}

	public void close() {
		dsocket.close();

	}


	static int findMySelf(String[] tabSampleRed) {
		int id = 0;
		return id;
	}

	static int findNearMe(String[] tabSampleRed) {
		return 1;
	}

	public static void main(String[] args) {
		//CameraInfrarouge cir = new CameraInfrarouge();
		RegulatedMotor left = new EV3LargeRegulatedMotor(MotorPort.B);
		RegulatedMotor right = new EV3LargeRegulatedMotor(MotorPort.C);
		left.forward();
		right.forward();
		
		Touch tc = new Touch();
		left.setAcceleration(800);
		right.setAcceleration(800);

		left.resetTachoCount();
		right.resetTachoCount();
	
		while(tc.getValue()==0.0) {
			try {
				//int id = findMySelf(cir.tabSampleRed);
				//int ne = findNearMe(cir.tabSampleRed);
				//String[] coord = cir.tabSampleRed[id].split(";");
				//String[] direct = cir.tabSampleRed[ne].split(";");
				String r = new String("a,b,c,d,e");
				String[] coord = r.split(",");
				coord[1]="0";
				coord[2]="0";
				coord[3]="2";
				coord[4]="2";
				String[] direct= r.split(",");
				direct[1]="0";
				direct[2]="3";
				
				int selfx = Integer.parseInt(coord[1]);
				int selfy = Integer.parseInt(coord[2]);
				int directionx = Integer.parseInt(direct[1]);
				int directiony = Integer.parseInt(direct[2]);

				Delay.msDelay(100);
				//cir.getData();
				
				//String[] coord2 = cir.tabSampleRed[id].split(";");
				//vecteur de direction = position actuelle - position du palet
				//vecteur de déplacement = position actuelle - ancienne position
				directionx -= Integer.parseInt(coord[3]);
				directiony -= Integer.parseInt(coord[4]);
				selfx = Integer.parseInt(coord[3])-selfx;
				selfy = Integer.parseInt(coord[4])-selfy;
				double vectself = Math.sqrt(selfx * selfx + selfy * selfy);
				double vectdirect = Math.sqrt(directionx * directionx + directiony * directiony);//à utiliser pour arcforward(angle,distance);
				double scalaire = directionx * selfx + directiony * selfy;
				double angle = Math.acos(scalaire / (vectself * vectdirect));
				angle = Math.toDegrees(angle);
				if (selfy > directiony) {
					right.setSpeed((int) (right.getSpeed() - (angle * 10)));
				} else if (directiony > selfy) {
					left.setSpeed((int) (left.getSpeed() - (angle * 10)));
				}
				Delay.msDelay(100);
				//cir.getData();
			System.out.println(angle);
				tc.getValue();
			} catch (Exception e) {
				continue;
			}

	}

}
}
