/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.autotasks.AutoRoutineController;
import frc.robot.autotasks.*;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot implements DrivetrainInterface, CargoMechanismInterface {

  Drivetrain drivetrain = new Drivetrain();
  CargoMechanism cargo = new CargoMechanism();

  HatchPanelMechanism panel = new HatchPanelMechanism();

  TaskInterface autoRoutine;
  /** Keeps track of auto */
  int taskState;

  TeleopController teleController;

  Joystick gamepadDriver = new Joystick(4);
  Joystick gamepadOperator = new Joystick(5);

  private boolean autoTogglePressed = false;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    RobotInit.init(drivetrain);
    teleController = new TeleopController(this, panel, this, 
      RobotInit.getDriveChooser(), 
      RobotInit.getOperateChooser(), 
      RobotInit.getSideChooser(), 
      () -> SmartDashboard.getNumber("rotate multiplier", 1),
      () -> SmartDashboard.getNumber("speed multiplier", 1));

  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("angle", drivetrain.getCurrentAngle());
    SmartDashboard.putNumber("inches", drivetrain.getInchesTraveled());

    SmartDashboard.putNumber("Left encoder", drivetrain.getLeftEncoderDistance());
    SmartDashboard.putNumber("Right encoder", drivetrain.getRightEncoderDistance());

  }

  @Override
  public void autonomousInit() {
    autoRoutine = AutoChooser.getChosenAuto();

    autoRoutine.start();

    autoTogglePressed = false;
  }

  /**
   * This function is called periodically during autonomous.
   */


  @Override
  public void autonomousPeriodic() {
  if (!autoRoutine.isFinished()) {
    autoRoutine.execute();
  } 
  else {
    autoRoutine.end();
  }

    /* //This is the code that switches from autonomous to human controlled
    if (gamepadDriver.getRawButtonPressed(2)){
      autoTogglePressed = !autoTogglePressed;
    }

    if(autoTogglePressed == false){
    } else {
      drivetrain.arcadeDrive(gamepadDriver.getY() * .7, -gamepadDriver.getZ() * .7);
    } 
    // */
  }

  @Override
  public void teleopInit() {
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

    teleController.runTeleop();

    // drivetrain.arcadeDrive(gamepadDriver.getY() * 0.7, -gamepadDriver.getZ() * 0.7);


  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {}

  @Override
  public void setLeftRightDriveSpeed(double left, double right) {
    drivetrain.setLeftRightDriveSpeed(left, right);
  }

  @Override
  public void setArcadeDriveSpeed(double speed, double turn) {
    drivetrain.setArcadeDriveSpeed(speed, turn);
  }

  @Override
  public double getInchesTraveled() {
    return drivetrain.getInchesTraveled();
  }

  @Override
  public double getCurrentAngle() {
    return drivetrain.getCurrentAngle();
  }

  @Override
  public void resetEncodersAndGyro() {
    drivetrain.resetEncodersAndGyro();
  }

  @Override
  public void intakeBall() {
    cargo.intakeBall();
  }

  @Override
  public void intakeBallOff() {
    cargo.intakeBallOff();
  }

  @Override
  public void launchBall() {
    cargo.launchBall();
  }

  @Override
  public void launchBallOff() {
    cargo.launchBallOff();
  }

  @Override
  public void reverse(){
    cargo.reverse();
  }



}
