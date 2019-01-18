package frc.robot;

import easypath.EasyPathDriveTrain;

public interface DrivetrainInterface extends EasyPathDriveTrain {

    /** 
     * @param left power to which left motors will be set
     * @param right power to which right motors will be set
     */
    public void setArcadeDriveSpeed(double speed, double turn);
}