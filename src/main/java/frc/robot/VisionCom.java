package frc.robot;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class VisionCom extends Thread {

	private static VisionCom instance;

	private final String hostName = "10.68.44.5"; // static IP for the jetson
	private final int visionPortNumber = 1185;
	
	private DatagramSocket socket;
	private DatagramPacket packet;
	private byte[] recieveData = new byte[16];
	private double x;
	private double y;
	private final int CAMERA_ANGLE = 90;
	private final int WIDTH = 320;
	
	public static VisionCom getInstance() {
		if(instance == null){
			instance = new VisionCom();
		}
		return instance;
	}

	private VisionCom() {}

	public void run() {
		try{
			socket = new DatagramSocket();
			socket.connect(InetAddress.getByName(hostName), visionPortNumber);
			packet = new DatagramPacket(recieveData , recieveData.length);
	
			while(true) {
				socket.receive(packet);
				x = ByteBuffer.wrap(Arrays.copyOfRange(packet.getData(), 0, 8)).getDouble();
				y = ByteBuffer.wrap(Arrays.copyOfRange(packet.getData(), 8, 16)).getDouble();
			}
		}catch(Exception e) {
			System.out.println(e);
		}
	}

	public double getX() {
		 return x;
	}

	public double getY() {
		return y;
	}

	public double getLineAngle() {
		return ((x * CAMERA_ANGLE) / WIDTH ) - (CAMERA_ANGLE / 2); 
	}
}