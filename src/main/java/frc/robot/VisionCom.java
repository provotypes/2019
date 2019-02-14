package frc.robot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionCom extends Thread {

	// http://10.68.44.2:5800/stream.mjpg
	// C:\Users\casey\Documents\eclipse-workspace
	// java -jar whitetapevision.jar http://10.68.44.2:5800/stream.mjpg
	// java -jar whitetapevision.jar http://169.254.57.251:5800/stream.mjpg

	private final String hostName = "10.68.44.5"; // static IP for the driver station
	private final int visionPortNumber = 5801;

	private final int CAMERA_ANGLE = 90;

	private UsbCamera source;
	private MjpegServer server;
	private int exposure = 33;

	private double width = 320;
	private double lineAngle = 0;
	private double lineCount = Double.NaN;

	public void beginCamera() {
		source = CameraServer.getInstance().startAutomaticCapture();
		source.setResolution(320, 240);
		source.setFPS(60);
		source.setExposureManual(exposure);
		SmartDashboard.putNumber("exposure", exposure);

		server = CameraServer.getInstance().addServer("VisionCam", 5800);
		server.setSource(source);
		server.getListenAddress();
	}

	@Override
	public void run() {
		while (!isInterrupted()) {
			updateExposure();
			double[] lineInfo = getLineInfo();
			lineAngle = lineInfo[0];
			lineCount = lineInfo[1];
			SmartDashboard.putNumber("line num", lineCount);

		}
	}

	public double getLineAngle() {
		return lineAngle;
	}

	public double getLineCount() {
		return lineCount;
	}

	public double[] getLineInfo() {
		Timer t = new Timer();
		t.reset();
		t.start();
		double LineAngle;
		double lineNum;
		try {
			String lineXY = get(Requests.NEAREST_LINE);
			int lineX = Integer.parseInt(lineXY.substring(0, lineXY.indexOf(",")));
			lineNum = Integer.parseInt(lineXY.substring(lineXY.indexOf(";") + 1));
			LineAngle = ((lineX * CAMERA_ANGLE) / width) - (CAMERA_ANGLE / 2);
		} catch (Exception e) {
			lineNum = Double.NaN;
			LineAngle = Double.NaN;
		}

		SmartDashboard.putNumber("vision com time", t.get());
		return new double[]{LineAngle, lineNum};
	}

	public String get(int request) {

		String output = "";
		// connect
		try {
			Socket socket = new Socket(hostName, visionPortNumber);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			socket.setSoTimeout(40);

			// send and receive
			out.println(request);
			// System.out.println("Server: " + in.readLine());
			output = in.readLine();

			socket.close();

		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			// System.out.println("conection to vision code timeout");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;

	}

	public void updateExposure() {
		exposure = (int) SmartDashboard.getNumber("exposure", -1);
		if (exposure < 0) {
			source.setExposureAuto();
		} else if (exposure > 100) {
			exposure = 100;
			source.setExposureManual(exposure);
		} else {
			source.setExposureManual(exposure);
		}
	}

	public void getStreamWidth() {
		try {
			width = Integer.parseInt(get(Requests.WIDTH));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

}
