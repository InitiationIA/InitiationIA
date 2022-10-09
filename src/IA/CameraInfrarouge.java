package IA;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

class CameraInfrarouge implements Sensor{
	String sampleRed;
	DatagramSocket dsocket;
	public CameraInfrarouge(){
	/*  	
	try {
      int port = 8888;
      dsocket = new DatagramSocket(port);

      byte[] buffer = new byte[2048];


      DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

      while (true) 
      {
        dsocket.receive(packet);

        String msg = new String(buffer, 0, packet.getLength());

        String[] palets = msg.split("\n");
        
        for (int i = 0; i < palets.length; i = i+2) 
        {
        	String[] coord = palets[i].split(";");
        	int x = Integer.parseInt(coord[1]);
        	int y = Integer.parseInt(coord[2]);
            sampleRed[i] = x;
            sampleRed[i+1] = y;
        }
        
        
        packet.setLength(buffer.length);
      }
     
    } 
    catch (Exception e) 
    {
      System.err.println(e);
    }
	*/	
	int port = 8888; 
      try {
		dsocket = new DatagramSocket(port);
		InetAddress serveur = InetAddress.getByName("192.168.1.255");
		 byte[] buffer = new byte[2048];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serveur,port);
		sampleRed =  new String(buffer, 0, packet.getLength());
	} catch (SocketException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
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
}
