/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Compressor;
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
	private FlightStick stick = new FlightStick(0);
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

		// stick.bindButton(ControllerButtons., );

		// Operate
		// cargo mech
		stick.bindButtonPress(ControllerButtons.panelFloorPickup, panel::floorPickup);
		stick.bindButtonPress(ControllerButtons.panelDetach, panel::deposit);
		stick.bindButtonPress(ControllerButtons.panelStow, panel::stow);
		stick.bindButtonPress(ControllerButtons.panelLoadingStationPickup, panel::loadingStationPickup);

		stick.bindButton(ControllerButtons.cargoFloorIntakeBarIn, cargo::floorIntakeBarIn);
		stick.bindButton(ControllerButtons.cargoFloorIntakeBarOut, cargo::floorIntakeBarOut);
		stick.bindButton(ControllerButtons.cargoMidIntake, cargo::midIntake);
		stick.bindButton(ControllerButtons.cargoHoodIntake, cargo::hoodIntake);
		stick.bindButton(ControllerButtons.cargoShootHigh, cargo::shootHigh);
		stick.bindButton(ControllerButtons.cargoShootLow, cargo::shootLow);
		stick.bindButton(ControllerButtons.cargoFlush, cargo::flush);
		stick.bindButton(ControllerButtons.cargoIdle, cargo::idle);

		// Drive
		gamepad.bindAxes(gamepad.LEFT_Y_AXIS, gamepad.RIGHT_X_AXIS, this::arcade);

		// compressor.stop();
	}

	public void runTeleop() {
		driveSelected = driveChooser.getSelected();

		rotateMultiplier = rotateMultiplierSupplier.get(); //SmartDashboard.getNumber("rotate multiplier", 1);
		speedMultiplier = speedMultiplierSupplier.get(); //SmartDashboard.getNumber("speed multiplier", 1);

		panel.periodic();
		cargo.periodic();

		gamepad.run();
		stick.run();

		
		
		/*
		if (stick.getRawButton(ControllerButtons.cargoFloorIntake)) {
			intakeState = true;
			thirdWheelState = true;
			// compressor.stop();
		} else if (stick.getRawButton(ControllerButtons.cargoReverseIntakeBar) || stick.getRawButton(ControllerButtons.cargoReverseThirdWheel)) {
			if (stick.getRawButton(ControllerButtons.cargoReverseIntakeBar)) {
				intakeState = false;
			}
			if (stick.getRawButton(ControllerButtons.cargoReverseThirdWheel)) {
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

		if (stick.getRawButtonPressed(ControllerButtons.cargoArmSwitch)) {
			cargo.intakeArmSwitch();
		}
		if (stick.getRawButtonPressed(ControllerButtons.cargoHoodSwitch)) {
			cargo.hoodSwitch();
		}

		// hatch panels
		if (stick.getRawButtonPressed(ControllerButtons.panelDetach)) {
			panel.deposit();
		}
		if (stick.getRawButtonPressed(ControllerButtons.panelStow)) {
			panel.stow();
		}
		if (stick.getRawButtonPressed(ControllerButtons.panelFloorPickup)) {
			panel.floorPickup();
		}
		if (stick.getRawButtonPressed(4)) {
			panel.loadingStationPickup();
		}
		*/


		//Drive

		/*
		switch (driveSelected) {
			case RobotInit.kFlightStickDrive:
				arcade(stick.getY(), stick.getZ());

			case RobotInit.kGamePadArcadeDrive:
				arcade(gamepad.getRightY(), gamepad.getLeftX());
				break;

			case RobotInit.kGamePadTankDrive:
				tank(gamepad.getLeftY(), gamepad.getRightY());
				break;

			case RobotInit.kGamePadStickDrive:
				arcade(gamepad.getRightY(), gamepad.getRightX());
				break;

			default:
				driveTrain.setLeftRightDriveSpeed(0, 0);
				break;
		}
		// */
	}

	private void arcade(double speed, double rotation) {
		double outSpeed = speedMultiplier * speed;

		// double outRotation = rotation * speed * rotateMultiplier;
		double outRotation = rotation * rotateMultiplier;

		driveTrain.setArcadeDriveSpeed(outSpeed, -outRotation);
	}

	private void tank(double left, double right) {
		double outLeft = -left * speedMultiplier;
		double outRight = -right * speedMultiplier;

		driveTrain.setLeftRightDriveSpeed(outLeft, outRight);
	}
}
