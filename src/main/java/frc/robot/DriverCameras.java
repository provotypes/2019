package frc.robot;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.cameraserver.CameraServer;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriverCameras {
    
    private DriverCameras(){}
    
    private static DriverCameras instance;

    private UsbCamera cameraCargo;

	private UsbCamera cameraPanel;
    
    private MjpegServer stream;

	private NetworkTableEntry cameraKey;

    public static DriverCameras getInstance(){
        if(instance == null){
			instance = new DriverCameras();
		}
		return instance;
    }

    public void start(){
        cameraCargo = CameraServer.getInstance().startAutomaticCapture(1);
        cameraPanel = CameraServer.getInstance().startAutomaticCapture(0);

        cameraCargo.setResolution(320, 240);
        cameraPanel.setResolution(320, 240);
        
        // cameraCargo.setConnectionStrategy(ConnectionStrategy.kForceClose);
        // cameraPanel.setConnectionStrategy(ConnectionStrategy.kForceClose);

        stream = CameraServer.getInstance().addSwitchedCamera("Driver Cameras");

        SmartDashboard.putString("stream info", stream.getPort() + " " + stream.getListenAddress());
        stream.setSource(cameraPanel);

		cameraKey = NetworkTableInstance.getDefault().getTable("").getEntry("CameraSelection");
    }
    
    public void setCameraCargo() {
		
        cameraKey.setString(cameraCargo.getName());
        stream.setSource(cameraCargo);
        
        
        System.out.println("camera set cargo");
    }

    public void setCameraPanel(){
		
        cameraKey.setString(cameraPanel.getName());
        stream.setSource(cameraPanel);
        


        System.out.println("camera set panel");
   
	}
    
    
}
