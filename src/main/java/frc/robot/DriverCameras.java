package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class DriverCameras {
    
    private DriverCameras(){}
    
    private static DriverCameras instance;

    private UsbCamera cameraCargo;

	private UsbCamera cameraPanel;
	
	private NetworkTableEntry cameraKey;

    public static DriverCameras getInstance(){
        if(instance == null){
			instance = new DriverCameras();
		}
		return instance;
    }

    public void start(){
        cameraCargo = CameraServer.getInstance().startAutomaticCapture();
        cameraPanel = CameraServer.getInstance().startAutomaticCapture();

        cameraCargo.setResolution(320, 240);
		cameraPanel.setResolution(320, 240);
		
		cameraKey = NetworkTableInstance.getDefault().getTable("").getEntry("CameraSelection");
		
    }
    
    public void setCameraCargo() {
		
		cameraKey.setString(cameraCargo.getName());
		
    }

    public void setCameraPanel(){
		
		cameraKey.setString(cameraPanel.getName());
   
	}
    
    
}
