package frc.robot;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class TeleopController {

	CargoMechanismInterface cargo;
	HatchPanelMechanismInterface panel;
	DrivetrainInterface driveTrain;

	Compressor compressor = new Compressor();

	//Controllers
	private Extreme3DProJoystick stick = new Extreme3DProJoystick(0);
	private LogitechGamepadController gamepad = new LogitechGamepadController(1);

	SendableChooser<String> driveChooser;

	private String driveSelected;

	private double rotateMultiplier = 1;
	private double speedMultiplier = 1;

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

		this.rotateMultiplierSupplier = rotateMultiplierSupplier;
		this.speedMultiplierSupplier = speedMultiplierSupplier;

		compressor.start();

		// Operate
		stick.bindButtonToggle(Extreme3DProJoystick.TOP_RIGHT_TOP_BUTTON,     panel::floorPickup,       panel::stow);
		stick.bindButtonToggle(Extreme3DProJoystick.BOTTOM_LEFT_TOP_BUTTON,   panel::deposit,           panel::stow);
		stick.bindButtonToggle(Extreme3DProJoystick.BOTTOM_LEFT_TOP_BUTTON,   panel::stationPickup,     panel::stow);
		stick.bindButtonPress(Extreme3DProJoystick.BOTTOM_RIGHT_TOP_BUTTON,   panel::stow);

		stick.bindButtonToggle(Extreme3DProJoystick.TRIGGER,                  cargo::shootHigh,         cargo::idle);
		stick.bindButtonToggle(Extreme3DProJoystick.THUMB_BUTTON,             cargo::shootLow,          cargo::idle);
		stick.bindButtonToggle(Extreme3DProJoystick.TOP_LEFT_BASE_BUTTON,     cargo::floorIntakeBarIn,  cargo::idle);
		stick.bindButtonToggle(Extreme3DProJoystick.TOP_RIGHT_BASE_BUTTON,    cargo::floorIntakeBarOut, cargo::idle);
		stick.bindButtonToggle(Extreme3DProJoystick.MIDDLE_LEFT_BASE_BUTTON,  cargo::midIntake,         cargo::idle);
		stick.bindButtonToggle(Extreme3DProJoystick.MIDDLE_RIGHT_BASE_BUTTON, cargo::hoodIntake,        cargo::idle);
		stick.bindButtonToggle(Extreme3DProJoystick.BOTTOM_LEFT_BASE_BUTTON,  cargo::flush,             cargo::idle);
		stick.bindButtonPress(Extreme3DProJoystick.BOTTOM_RIGHT_BASE_BUTTON,  cargo::idle);

		// Drive
		gamepad.bindAxes(gamepad.LEFT_Y_AXIS, gamepad.RIGHT_X_AXIS, this::arcade);
	}

	public void runTeleop() {
		driveSelected = driveChooser.getSelected();

		rotateMultiplier = rotateMultiplierSupplier.get();
		speedMultiplier = speedMultiplierSupplier.get();

		panel.periodic();
		cargo.periodic();

		gamepad.run();
		stick.run();
	}

	private void arcade(double speed, double rotation) {
		double outSpeed = speedMultiplier * speed;
		double outRotation = rotation * rotateMultiplier;

		driveTrain.setArcadeDriveSpeed(outSpeed, -outRotation);
	}

	private void tank(double left, double right) {
		double outLeft = -left * speedMultiplier;
		double outRight = -right * speedMultiplier;

		driveTrain.setLeftRightDriveSpeed(outLeft, outRight);
	}
}
