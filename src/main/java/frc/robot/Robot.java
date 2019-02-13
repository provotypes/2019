/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.autotasks.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  DrivetrainInterface drivetrain = new Drivetrain();
  CargoMechanismInterface cargo = new CargoMechanism();
  HatchPanelMechanismInterface panel = new HatchPanelMechanism();

  TaskInterface autoRoutine;
  TaskInterface visionAutoTask;
  /** Keeps track of auto */
  int taskState;
  
  AutoChooser autoChooser;
  TeleopController teleController;

  Joystick gamepadDriver = new Joystick(4);
  Joystick gamepadOperator = new Joystick(5);

  VisionCom vision = new VisionCom();

  private boolean autoTogglePressed = false;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    RobotInit.init(drivetrain);
    teleController = new TeleopController(drivetrain, panel, cargo, 
      RobotInit.getDriveChooser(), 
      RobotInit.getOperateChooser(),
      RobotInit.getSideChooser(), 
      () -> SmartDashboard.getNumber("rotate multiplier", 1),
      () -> SmartDashboard.getNumber("speed multiplier", 1)
    );

    autoChooser = new AutoChooser(new AutoFactory(drivetrain, panel, cargo, vision));

      //visionAutoTask = new VisionLineUpTask();
    vision.beginCamera();
    // vision.start();
    SmartDashboard.putBoolean("calibrate gyro", false);
    drivetrain.calibrateGyro();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("gyro angle", drivetrain.getCurrentAngle());
    SmartDashboard.putNumber("inches", drivetrain.getInchesTraveled());

    SmartDashboard.putNumber("Left encoder", drivetrain.getLeftEncoderDistance());
    SmartDashboard.putNumber("Right encoder", drivetrain.getRightEncoderDistance());

    SmartDashboard.putNumber("line angle", vision.getLineAngle());
    if (SmartDashboard.getBoolean("calibrate gyro", false)) {
      drivetrain.calibrateGyro();
      SmartDashboard.putBoolean("calibrate gyro", false);
    }
  }

  @Override
  public void autonomousInit() {
    autoTogglePressed = false;

    autoRoutine = autoChooser.getChosenAuto();
    autoRoutine.start();
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

    if(!autoTogglePressed){
      drivetrain.arcadeDrive(gamepadDriver.getY() * .7, -gamepadDriver.getZ() * .7);
    } else {
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

    /* //This is the code that switches from human controlled to autonomous 
    int visionState = 0;

    if (gamepadDriver.getRawButtonPressed(2)){
      autoTogglePressed = !autoTogglePressed;
    }
    // drivetrain.arcadeDrive(gamepadDriver.getY() * 0.7, -gamepadDriver.getZ() *
    // 0.7);

    if(!autoTogglePressed){
      visionState = 0;
      drivetrain.arcadeDrive(gamepadDriver.getY() * .7, -gamepadDriver.getZ() * .7);
    } else {
      if (visionState == 0) {
        visionAutoTask.start();
        visionState += 1;
      } else {
        visionAutoTask.execute();
      }
    } */
  }


 
  @Override
  public void testInit() {
    
    panel.stow();
    panel.periodic();

    boolean hoodState = cargo.hoodState();
    boolean armState = cargo.armState();
    
    if (hoodState == true) {
      cargo.hoodSwitch();    
    } else if (hoodState == false) {
      //just leave it
    }

    if (armState == true) { 
      cargo.intakeArmSwitch();
    } else if (armState == false) {
      //just leave it
    }

  }
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
   
  }
}
