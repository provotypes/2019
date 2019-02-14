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

/**
 * Add your docs here.
 */
public class TeleopController {

    CargoMechanismInterface cargo;    
    HatchPanelMechanismInterface panel;
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

    public TeleopController(DrivetrainInterface d, 
                            HatchPanelMechanismInterface p, 
                            CargoMechanismInterface c,
                            SendableChooser<String> driveChooser,
                            SendableChooser<String> operateChooser,
                            SendableChooser<String> sideChooser,
                            Supplier<Double> rotateMultiplierSupplier,
                            Supplier<Double> speedMultiplierSupplier) {
        cargo = c;
        panel = p;
        driveTrain = d;
        this.driveChooser = driveChooser;
        this.operateChooser = operateChooser;
        this.sideChooser = sideChooser;

        this.rotateMultiplierSupplier = rotateMultiplierSupplier;
        this.speedMultiplierSupplier = speedMultiplierSupplier;
        compressor.start();

        // compressor.stop();

        //SmartDashboard.putNumber("rotate multiplier", tc.getRotateMultiplier);
        //SmartDashboard.putNumber("speed multiplier", tc.getSpeedMultiplier);


    }

    public void runTeleop(){
        driveSelected = driveChooser.getSelected();
        operateSelected = operateChooser.getSelected();
        sidePreference = sideChooser.getSelected();

        rotateMultiplier = rotateMultiplierSupplier.get(); //SmartDashboard.getNumber("rotate multiplier", 1);
        speedMultiplier = speedMultiplierSupplier.get(); //SmartDashboard.getNumber("speed multiplier", 1);

        panel.periodic();

        //Operate
        // cargo mech
        Boolean intakeState = null;
        Boolean thirdWheelState = null;
        Boolean launcherState = null;
        if (operateSelected == RobotInit.kFlightStickOperate) {
            // cargo
            if (stick.getRawButton(ControllerButtons.cargoFloorIntake)) {
                intakeState = true;
                thirdWheelState = true;
                // compressor.stop();
            } else if (stick.getRawButton(ControllerButtons.cargoReverseIntakeBar) || stick.getRawButton(ControllerButtons.cargoReverseThirdWheel)){
                if (stick.getRawButton(ControllerButtons.cargoReverseIntakeBar)){
                    intakeState = false;
                } 
                if (stick.getRawButton(ControllerButtons.cargoReverseThirdWheel)){
                    thirdWheelState = false;
                }
            }

            if (stick.getRawButton(ControllerButtons.cargoMidIntake)) {
                intakeState = false;
                thirdWheelState = true;
                
            }

            if (stick.getRawButton(ControllerButtons.cargoLaunch)) {
                launcherState = true;
                thirdWheelState = true;
                // compressor.stop();
            } else if (stick.getRawButton(ControllerButtons.cargoReverseLauncher) || stick.getRawButton(ControllerButtons.cargoReverseThirdWheel)) {
                if (stick.getRawButton(ControllerButtons.cargoReverseLauncher)) {
                    launcherState = false;
                }
                if (stick.getRawButton(ControllerButtons.cargoReverseThirdWheel)) {
                    thirdWheelState = false;
                }
            }

            if (stick.getRawButtonPressed(ControllerButtons.cargoArmSwitch)){
                cargo.intakeArmSwitch();
            }
            if (stick.getRawButtonPressed(ControllerButtons.cargoHoodSwitch)){
                cargo.hoodSwitch();
            }

            // hatch panels
            if (stick.getRawButtonPressed(ControllerButtons.panelDetach)){
                panel.deposit();
            }
            if (stick.getRawButtonPressed(ControllerButtons.panelStow)){
                panel.stow();
            }
            if (stick.getRawButtonPressed(ControllerButtons.panelFloorPickup)){
                panel.floorPickup();
            }
            if (stick.getRawButtonPressed(4)){
                panel.loadingStationPickup();
            }

        } else {
            if (gamepad.getLeftBumper()){
                cargo.intakeBarOn();
                cargo.awkwardThirdWheelOn();
            } else {
                cargo.intakeBarOff();
                cargo.awkwardThirdWheelOff();
            }

            if (gamepad.getRightBumper()){
                cargo.launcherOn();
                cargo.awkwardThirdWheelOn();
            } else if(gamepad.getXButton()){
                cargo.launcherReverse();
                // cargo.awkwardThirdWheelReverse();
            } else {
                cargo.launcherOff();
                cargo.awkwardThirdWheelOff();
            }

            if (gamepad.getYButton()){
                panel.deposit();
            } else if (gamepad.getAButton()){
                panel.stow();
            }

        }

        // set motors
        if (launcherState != null) {
            if (launcherState.booleanValue()) {
                cargo.launcherOn();
            } else {
                cargo.launcherReverse();
            }
        } else {
            cargo.launcherOff();
        }
        if (thirdWheelState != null) {
            if (thirdWheelState.booleanValue()) {
                cargo.awkwardThirdWheelOn();
            } else {
                cargo.awkwardThirdWheelReverse();
            }
        } else {
            cargo.awkwardThirdWheelOff();
        }
        if (intakeState != null && cargo.armState()) {
            if (intakeState.booleanValue()) {
                cargo.intakeBarOn();
            } else {
                cargo.intakeBarReverse();
            }
        } else {
            cargo.intakeBarOff();
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
