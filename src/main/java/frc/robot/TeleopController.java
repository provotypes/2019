package frc.robot;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Compressor;
import frc.robot.operatorinterface.Extreme3DProJoystick;
import frc.robot.operatorinterface.LogitechGamepadController;
import frc.robot.subsystems.CargoMechanismInterface;
import frc.robot.subsystems.DrivetrainInterface;
import frc.robot.subsystems.HatchPanelMechanismInterface;

public class TeleopController {

	CargoMechanismInterface cargo;
	HatchPanelMechanismInterface panel;
	DrivetrainInterface driveTrain;

	public Compressor compressor = new Compressor();

	//Controllers
	private Extreme3DProJoystick stick;
	private LogitechGamepadController gamepad;

	private boolean isHumanControlled;
	private boolean teleControlsBound;
	public boolean autoEnded;

	private boolean isCargoForward;

	private double rotateMultiplier = 1;
	private double speedMultiplier = 1;

	private final Supplier<Double> rotateMultiplierSupplier;
	private final Supplier<Double> speedMultiplierSupplier;

	private DriverCameras camera;

	public TeleopController(Extreme3DProJoystick j,
							LogitechGamepadController g,
							DrivetrainInterface d,
							HatchPanelMechanismInterface p,
							CargoMechanismInterface c,
							DriverCameras dc,
							Supplier<Double> rotateMultiplierSupplier,
							Supplier<Double> speedMultiplierSupplier) {
		stick = j;
		gamepad = g;
		cargo = c;
		panel = p;
		driveTrain = d;
		camera = dc;
		this.rotateMultiplierSupplier = rotateMultiplierSupplier;
		this.speedMultiplierSupplier = speedMultiplierSupplier;

		camera.start();
		compressor.start();

		autoEnded = false;

		teleControlsBound = false;
		isHumanControlled = false;
	}

	public void autoControlsInit(){
		gamepad.bindButtonPress(gamepad.Companion.getY_BUTTON(), () -> autoEnded = true);
	}

	public void teleopInit() {
		System.out.println("TELEOP WAS INIT");

		autoEnded = true;
		isHumanControlled = true;
		isCargoForward = false;
		camera.setCameraPanel();

		stick.bindButtonToggle(Extreme3DProJoystick.getBottomTopButton,   panel::floorPickup,       panel::holdPanel);
		stick.bindButtonToggle(Extreme3DProJoystick.Companion.getTOP_LEFT_TOP_BUTTON(),      panel::deposit,           panel::holdPanel);
		stick.bindButtonToggle(Extreme3DProJoystick.Companion.getTOP_RIGHT_TOP_BUTTON(),     panel::stationPickup,     panel::holdPanel);
		stick.bindButtonToggle(Extreme3DProJoystick.Companion.getBOTTOM_RIGHT_TOP_BUTTON(),  panel::rollersForward,    panel::holdPanel);

		stick.bindButtonToggle(Extreme3DProJoystick.Companion.getTRIGGER(),                  cargo::shootHigh,         cargo::idle);
		stick.bindButtonToggle(Extreme3DProJoystick.Companion.getTHUMB_BUTTON(),             cargo::shootLow,          cargo::idle);
		stick.bindButtonToggle(Extreme3DProJoystick.Companion.getTOP_LEFT_BASE_BUTTON(),     cargo::floorIntakeBarIn,  cargo::idle);
		stick.bindButtonToggle(Extreme3DProJoystick.Companion.getTOP_RIGHT_BASE_BUTTON(),    cargo::floorIntakeBarOut, cargo::idle);
		stick.bindButtonToggle(Extreme3DProJoystick.Companion.getMIDDLE_LEFT_BASE_BUTTON(),  cargo::midIntake,         cargo::idle);
		stick.bindButtonToggle(Extreme3DProJoystick.Companion.getMIDDLE_RIGHT_BASE_BUTTON(), cargo::hoodIntake,        cargo::idle);
		stick.bindButtonToggle(Extreme3DProJoystick.Companion.getBOTTOM_LEFT_BASE_BUTTON(),  cargo::flush,             cargo::idle);
		stick.bindButtonToggle(Extreme3DProJoystick.Companion.getBOTTOM_RIGHT_BASE_BUTTON(), cargo::shootMax,          cargo::idle);

		// Drive
		gamepad.bindAxes(gamepad.Companion.getLEFT_Y_AXIS(), gamepad.Companion.getRIGHT_X_AXIS(), this::arcade);
		gamepad.bindButtonPress(gamepad.Companion.getLEFT_STICK_IN(), () -> isCargoForward = !isCargoForward);
		gamepad.bindButtonPress(gamepad.Companion.getA_BUTTON(), () -> { isCargoForward = !isCargoForward;
		                                                  if (isCargoForward){
															  camera.setCameraCargo();
														  } else {
															  camera.setCameraPanel();
														  }
		                                                });
		gamepad.bindButton(gamepad.Companion.getLEFT_BUMPER(), this::quickTurnleft);
		gamepad.bindButton(gamepad.Companion.getRIGHT_BUMPER(), this::quickTurnRight);
		gamepad.bindButtonPress(gamepad.Companion.getB_BUTTON(), () -> isHumanControlled = true);

		teleControlsBound = true;
	}

	private void runAuto(){
		gamepad.run();
	}

	private void runTeleop() {
		rotateMultiplier = rotateMultiplierSupplier.get();
		speedMultiplier = speedMultiplierSupplier.get();

		gamepad.run();
		stick.run();

		panel.periodic();
		cargo.periodic();
	}

	private void arcade(double speed, double rotation) {
		double outSpeed = speedMultiplier * speed;
		double outRotation = rotation * rotateMultiplier;
		if (isHumanControlled) {
			if (isCargoForward){
				driveTrain.setArcadeDriveSpeed(outSpeed, -outRotation);
			} else {
				driveTrain.setArcadeDriveSpeed(-outSpeed, -outRotation);
			}
		}
	}

	private void quickTurnleft() {
		driveTrain.setArcadeDriveSpeed(0, 0.5);
	}

	private void quickTurnRight() {
		driveTrain.setArcadeDriveSpeed(0, -0.5);
	}

	private void tank(double left, double right) {
		double outLeft = -left * speedMultiplier;
		double outRight = -right * speedMultiplier;

		driveTrain.setLeftRightDriveSpeed(outLeft, outRight);
	}

	public void startCompressor(){
		compressor.start();
	}

	public void run(){
		if (autoEnded){
			if (!teleControlsBound){
				teleopInit();
			} else {
				runTeleop();
			}
		} else {
			runAuto();
		}
	}

	public void endAuto(){
		autoEnded = true;
	}
}
