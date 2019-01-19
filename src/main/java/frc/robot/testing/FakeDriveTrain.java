/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.testing;
import frc.robot.DrivetrainInterface;

/**
 * class to simulate the drivetrain encoders and gyro
 */
public class FakeDriveTrain implements DrivetrainInterface{
    double encoderLeft;
    double encoderRight;
    double gyro;

    @Override
    public void setLeftRightDriveSpeed(double left, double right) {
        
    }

    @Override
    public double getInchesTraveled() {
        return 0;
    }

    @Override
    public double getCurrentAngle() {
        return 0;
    }

    @Override
    public void resetEncodersAndGyro() {

    }

    @Override
    public void setArcadeDriveSpeed(double speed, double turn) {

    }

}
