package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionCom {

	// http://10.68.44.2:5800/stream.mjpg
	// C:\Users\casey\Documents\eclipse-workspace
	// java -jar whitetapevision.jar http://10.68.44.2:5800/stream.mjpg
	// java -jar whitetapevision.jar http://169.254.57.251:5800/stream.mjpg

	private NetworkTableInstance inst;
	private NetworkTable table;
	private NetworkTableEntry nearestCube;


	private final int CAMERA_ANGLE = 90;

	private UsbCamera source;
	private MjpegServer server;
	private int exposure = 33;

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

		inst = NetworkTableInstance.getDefault();
	    table = inst.getTable("vision");
	    nearestCube = table.getEntry("nearestCube");
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
	}

	public void doStuff() {
		updateExposure();
		
		String cubeXY = nearestCube.getString((width/2) + ",0");
		double cubeX;
		try {
			cubeX = Integer.parseInt(cubeXY.substring(0, cubeXY.indexOf(",")));
		} catch (Exception e) {
			// e.printStackTrace();
			cubeX = width/2;
		}
		double cubeAngle = ((cubeX * CAMERA_ANGLE) / width) - (CAMERA_ANGLE / 2);

		SmartDashboard.putNumber("line angle", cubeAngle);
		SmartDashboard.putNumber("line X", cubeX);

	}

	public double getAngleToCube() {
		String cubeXY = nearestCube.getString((width/2) + ",0");
		double cubeX;
		try {
			cubeX = Integer.parseInt(cubeXY.substring(0, cubeXY.indexOf(",")));
		} catch (Exception e) {
			// e.printStackTrace();
			cubeX = width/2;
		}
		double cubeAngle = ((cubeX * CAMERA_ANGLE) / width) - (CAMERA_ANGLE / 2);

		return cubeAngle;
	}

	public String get(int request) {

		String output = "";
		return output;

	}

}
