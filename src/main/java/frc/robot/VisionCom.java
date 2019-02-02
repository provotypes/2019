package frc.robot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionCom {

	// http://10.68.44.2:5800/stream.mjpg
	// C:\Users\casey\Documents\eclipse-workspace
	// java -jar whitetapevision.jar http://10.68.44.2:5800/stream.mjpg
	// java -jar whitetapevision.jar http://169.254.57.251:5800/stream.mjpg
	private final String hostName = "10.68.44.77";
	private final int visionPortNumber = 5801;

	private final int CAMERA_ANGLE = 90;

	private UsbCamera source;
	private MjpegServer server;
	private int exposure = 50;

	private double width = 390;

	public void beginCamera() {
		source = CameraServer.getInstance().startAutomaticCapture();
		source.setResolution(390, 260);
		source.setFPS(60);
		source.setExposureManual(exposure);
		SmartDashboard.putNumber("exposure", exposure);

		server = CameraServer.getInstance().addServer("VisionCam", 5800);
		server.setSource(source);
		server.getListenAddress();
	}

	public void updateExposure() {
		exposure = (int) SmartDashboard.getNumber("exposure", -1);
		if (exposure < 0){
			source.setExposureAuto();
		} else if (exposure > 100){
			exposure = 100;
			source.setExposureManual(exposure);
		}	else {
			source.setExposureManual(exposure);
		}
	}

	public void getStreamWidth() {
		try {
			width = Integer.parseInt(get(Requests.WIDTH));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doStuff() {

		updateExposure();
		
		String cubeXY = get(Requests.NEAREST_CUBE);
		double cubeX = Integer.parseInt(cubeXY.substring(0, cubeXY.indexOf(",")));
		double cubeAngle = ((cubeX * CAMERA_ANGLE) / width) - (CAMERA_ANGLE / 2);

		SmartDashboard.putNumber("line angle", cubeAngle);
		SmartDashboard.putNumber("line X", cubeX);

	}

	public double getAngleToCube() {

		String cubeXY = get(Requests.NEAREST_CUBE);
		double cubeX = Integer.parseInt(cubeXY.substring(0, cubeXY.indexOf(",")));
		double cubeAngle = ((cubeX * CAMERA_ANGLE) / width) - (CAMERA_ANGLE / 2);

		return cubeAngle;
	}

	public String get(int request) {

		String output = "";
		// connect
		try {
			Socket socket = new Socket(hostName, visionPortNumber);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// send and receive
			out.println(request);
			// System.out.println("Server: " + in.readLine());
			output = in.readLine();

			socket.close();

		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;

	}

}
