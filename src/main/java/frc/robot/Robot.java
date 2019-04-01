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

	DrivetrainInterface drivetrain = DrivetrainInterface.getDefaultDrivetrain();
	CargoMechanismInterface cargo = CargoMechanismInterface.getDefaulCargoMechanism();
	HatchPanelMechanismInterface panel = HatchPanelMechanismInterface.getDefaultHatchPanelMechanism();

	TaskInterface autoRoutine;
	TaskInterface visionAutoTask;
	/**
	 * Keeps track of auto
	 */
	int taskState;

	AutoChooser autoChooser;
	TeleopController teleController;
	DriverCameras cameras = DriverCameras.getInstance();
	Joystick gamepadDriver = new Joystick(4);
	Joystick gamepadOperator = new Joystick(5);

	VisionCom vision = VisionCom.getInstance();


	private boolean autoTogglePressed = false;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {

		System.out.println("Program started!");

		AutoFactory autoFactory = new AutoFactory(drivetrain, panel, cargo, vision);

		RobotInit.init(drivetrain);
		SmartDashboard.putNumber("rotate multiplier", 0.5);
		SmartDashboard.putNumber("speed multiplier", 0.8);
		teleController = getDefaultTeleopController(autoFactory);

		autoChooser = new AutoChooser(autoFactory);
		//visionAutoTask = new VisionLineUpTask();
		// vision.run();
		//vision.start();
		SmartDashboard.putBoolean("calibrate gyro", false);
		drivetrain.calibrateGyro();

		SmartDashboard.putNumber("launcher speed", 0);
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

		if (SmartDashboard.getBoolean("calibrate gyro", false)) {
			drivetrain.calibrateGyro();
			SmartDashboard.putBoolean("calibrate gyro", false);
		}
	}

	@Override
	public void disabledInit() {
		drivetrain.setCoast();
	}

	@Override
	public void autonomousInit() {
		drivetrain.setBrake();

		autoTogglePressed = false;

		autoRoutine = autoChooser.getChosenAuto();
		autoRoutine.start();

		teleController.autoControlsInit();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		if (!teleController.autoEnded){
			if (!autoRoutine.isFinished()) {
				autoRoutine.execute();
			} else {
				autoRoutine.end();
				teleController.endAuto();
			}
		}
		
    teleController.run();
	}

	@Override
	public void teleopInit() {
		teleController.endAuto();
		drivetrain.setBrake();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {

		teleController.run();

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
	
		drivetrain.setBrake();

		panel.startingConfig();
		panel.periodic();

		cargo.idle();
		cargo.periodic();

		drivetrain.resetEncodersAndGyro();

		teleController.startCompressor();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		/*
		double turn = (90 - drivetrain.getCurrentAngle()) / 45;
		if (turn > 0.6) {
			turn = 0.6;
		} else if (turn < -0.6) {
			turn = -0.6;
		}

		drivetrain.setArcadeDriveSpeed(0, turn);
		*/
	}

	private TeleopController getDefaultTeleopController(AutoFactory autoFactory){
		return new TeleopController(new Extreme3DProJoystick(0), 
									new LogitechGamepadController(1), 
									drivetrain, 
									panel, 
									cargo, 
									cameras,
									autoFactory,
									() -> SmartDashboard.getNumber("rotate multiplier", 0.5),
									() -> SmartDashboard.getNumber("speed multiplier", 0.85)
							);
	}
}
