/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class TeleopController {

    CargoMechanismInterface cargo;    
    HatchPanelMechanism panel;
    DrivetrainInterface driveTrain;

    Compressor compressor = new Compressor();

    //Controllers
    private Joystick stick = new Joystick(0);
    private LogitechGamepadController gamepad = new LogitechGamepadController(1);

    SendableChooser<String> driveChooser;
    SendableChooser<String> operateChooser;
    SendableChooser<String> sideChooser;

    private String driveSelected;
    private String operateSelected;
    private String sidePreference;

    private double rotateMultiplier = 0.6;
    private double speedMultiplier = 0.85;

    private final Supplier<Double> rotateMultiplierSupplier;
    private final Supplier<Double> speedMultiplierSupplier;

    public TeleopController(CargoMechanismInterface c, 
                            HatchPanelMechanism p, 
                            DrivetrainInterface d,
                            SendableChooser<String> driveChooser,
                            SendableChooser<String> operateChooser,
                            SendableChooser<String> sideChooser,
                            Supplier<Double> rotateMultiplierSupplier,
                            Supplier<Double> speedMultiplierSupplier) {
        cargo = c;
        panel = p;
        driveTrain = d;

        this.rotateMultiplierSupplier = rotateMultiplierSupplier;
        this.speedMultiplierSupplier = speedMultiplierSupplier;

        compressor.stop();

        //SmartDashboard.putNumber("rotate multiplier", tc.getRotateMultiplier);
        //SmartDashboard.putNumber("speed multiplier", tc.getSpeedMultiplier);


    }

    public void runTeleop(){
        driveSelected = driveChooser.getSelected();
        operateSelected = operateChooser.getSelected();
        sidePreference = sideChooser.getSelected();

        rotateMultiplier = rotateMultiplierSupplier.get(); //SmartDashboard.getNumber("rotate multiplier", 1);
        speedMultiplier = speedMultiplierSupplier.get(); //SmartDashboard.getNumber("speed multiplier", 1);

        //Operate
        if (operateSelected == RobotInit.kFlightStickDrive){
            if (stick.getRawButton(2)){
                cargo.intakeBall();
                compressor.stop();
            } else {
                cargo.intakeBallOff();
                compressor.start();
            }

            if (stick.getRawButton(1)){
                cargo.launchBall();
                compressor.stop();
            } else if(stick.getRawButton(11)){
                cargo.reverse();
            } else {
                cargo.launchBallOff();
                compressor.start();
            }

            if (stick.getRawButtonPressed(5)){
                panel.detachOut();
            }
            if (stick.getRawButtonPressed(3)){
                panel.detachIn();
            }
            

        } else {
            if (gamepad.getLeftBumper()){
                cargo.intakeBall();
            } else {
                cargo.intakeBallOff();
            }

            if (gamepad.getRightBumper()){
                cargo.launchBall();
            } else if(gamepad.getXButton()){
                cargo.reverse();
            } else {
                cargo.launchBallOff();
            }

            if (gamepad.getYButton()){
                panel.detachOut();
            } else if (gamepad.getAButton()){
                panel.detachIn();
            }

        }

        //Drive
        switch (driveSelected) {
        case RobotInit.kFlightStickDrive:
            if (sidePreference == RobotInit.kLeftPreference) {
            arcade(stick.getY(), stick.getZ());
            } else if (sidePreference == RobotInit.kRightPreference){
            arcade(stick.getY(), stick.getX());
            }
            break;

        case RobotInit.kGamePadArcadeDrive:
        if (sidePreference == RobotInit.kLeftPreference) {
            arcade(gamepad.getLeftY(), gamepad.getRightX());
        } else if (sidePreference == RobotInit.kRightPreference){
            arcade(gamepad.getRightY(), gamepad.getLeftX());
        }
            break;

        case RobotInit.kGamePadTankDrive:
            tank(gamepad.getLeftY(), gamepad.getRightY());
            break;

        case RobotInit.kGamePadStickDrive:
        if (sidePreference == RobotInit.kLeftPreference) {
            arcade(gamepad.getLeftY(), gamepad.getLeftX());
        } else if (sidePreference == RobotInit.kRightPreference){
            arcade(gamepad.getRightY(), gamepad.getRightX());
        }
            break;

        default:
            driveTrain.setLeftRightDriveSpeed(0, 0);
            break;

        }
    }

    private void arcade(double speed, double rotation) {
        double outSpeed = speedMultiplier * speed;
    
        // double outRotation = rotation * speed * rotateMultiplier;
        double outRotation = rotation * rotateMultiplier;
    
        driveTrain.setArcadeDriveSpeed(outSpeed, -outRotation);
      }
    
      private void tank(double left, double right) {
        double outLeft = - left * speedMultiplier;
        double outRight = - right * speedMultiplier;
    
        driveTrain.setLeftRightDriveSpeed(outLeft, outRight);
      }
}
