package frc.robot;

public interface DrivetrainInterface {
    /** 
     * @param left power to which left motors will be set
     * @param right power to which right motors will be set
     */
    public void setLeftRightDriveSpeed(double left, double right);

    /** 
     * @param left power to which left motors will be set
     * @param right power to which right motors will be set
     */
    public void setArcadeDriveSpeed(double speed, double turn);
}