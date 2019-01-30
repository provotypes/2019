/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

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

    //Controllers
    private Joystick stick;
    private LogitechGamepadController gamepad;

    //SendableChooser for Drive controller
    private static final String kFlightStickDrive = "Flight Stick Drive";
    private static final String kGamePadArcadeDrive = "Game Pad Arcade Drive";
    private static final String kGamePadTankDrive = "Game Pad Tank Drive";
    private static final String kGamePadStickDrive = "Game Pad Stick Drive";
    private String m_DriveSelected;
    private final SendableChooser<String> driveChooser = new SendableChooser<>();

    //SendableChooser for Operate controller
    private static final String kFlightStickOperate = "Flight Stick Operate";
    private static final String kGamePadOperate = "Game Pad Operate";
    private String m_OperateSelected;
    private final SendableChooser<String> operateChooser = new SendableChooser<>();

    //SendableChooser for Side preference
    private static final String kLeftPreference = "Left";
    private static final String kRightPreference = "Right";
    private String m_SidePreference;
    private final SendableChooser<String> SideChooser = new SendableChooser<>();

    private double rotateMultiplier = 0.6;
    private double speedMultiplier = 0.85;


    public TeleopController(CargoMechanismInterface c, HatchPanelMechanism p, DrivetrainInterface d){
        cargo = c;
        panel = p;
        driveTrain = d;

        driveChooser.setDefaultOption(kFlightStickDrive, kFlightStickDrive);
        driveChooser.addOption(kGamePadArcadeDrive, kGamePadArcadeDrive);
        driveChooser.addOption(kGamePadTankDrive, kGamePadTankDrive);
        driveChooser.addOption(kGamePadStickDrive, kGamePadStickDrive);
        SmartDashboard.putData("Drive Choice", driveChooser);

        operateChooser.setDefaultOption(kFlightStickOperate, kFlightStickOperate);
        operateChooser.addOption(kGamePadOperate, kGamePadOperate);
        SmartDashboard.putData("Operate Choice", operateChooser);

        SideChooser.addOption(kLeftPreference, kLeftPreference);
        SideChooser.setDefaultOption(kRightPreference, kRightPreference);
        SmartDashboard.putData("Side Choice", SideChooser);
    }

    public void runTeleop(){
        m_DriveSelected = driveChooser.getSelected();
        m_OperateSelected = operateChooser.getSelected();
        m_SidePreference = SideChooser.getSelected();

        rotateMultiplier = SmartDashboard.getNumber("rotate multiplier", 1);
        speedMultiplier = SmartDashboard.getNumber("speed multiplier", 1);

        //Operate
        if (m_OperateSelected == kFlightStickOperate){
        if (stick.getRawButton(2)){
            cargo.intakeBall();
        } else {
            cargo.intakeBallOff();
        }

        if (stick.getRawButton(1)){
            cargo.launchBall();
        } else if(stick.getRawButton(11)){
            cargo.launchBallOff();
        } else {
            cargo.launchBallOff();
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
            cargo.launchBallOff();
        } else {
            cargo.launchBallOff();
        }

        }

        //Drive
        switch (m_DriveSelected) {
        case kFlightStickDrive:
            if (m_SidePreference == kLeftPreference) {
            arcade(stick.getY(), stick.getZ());
            } else if (m_SidePreference == kRightPreference){
            arcade(stick.getY(), stick.getX());
            }
            break;

        case kGamePadArcadeDrive:
        if (m_SidePreference == kLeftPreference) {
            arcade(gamepad.getLeftY(), gamepad.getRightX());
        } else if (m_SidePreference == kRightPreference){
            arcade(gamepad.getRightY(), gamepad.getLeftX());
        }
            break;

        case kGamePadTankDrive:
            tank(gamepad.getLeftY(), gamepad.getRightY());
            break;

        case kGamePadStickDrive:
        if (m_SidePreference == kLeftPreference) {
            arcade(gamepad.getLeftY(), gamepad.getLeftX());
        } else if (m_SidePreference == kRightPreference){
            arcade(gamepad.getRightY(), gamepad.getRightX());
        }
            break;

        default:
            driveTrain.setLeftRightDriveSpeed(0, 0);
            break;

        }
    }

    private void arcade(double speed, double rotation) {
        double outSpeed = - speedMultiplier * speed;
    
        // double outRotation = rotation * speed * rotateMultiplier;
        double outRotation = rotation * rotateMultiplier;
    
        driveTrain.setArcadeDriveSpeed(outSpeed, outRotation);
      }
    
      private void tank(double left, double right) {
        double outLeft = - left * speedMultiplier;
        double outRight = - right * speedMultiplier;
    
        driveTrain.setLeftRightDriveSpeed(outLeft, outRight);
      }
}
