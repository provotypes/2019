package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
// import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CargoMechanism implements CargoMechanismInterface {

	private static final double STAGING_WHEEL_PERCENT_VOLTAGE = 0.9;
	private static final double INTAKE_BAR_PERCENT_VOLTAGE = 1;
	private static final double LAUNCHER_LOW_PERCENT_VOLTAGE = 0.4;
	private static final double LAUNCHER_HIGH_ENCODER_SPEED = 7500;
	private static final double LAUNCHER_REVERSE_PERCENT_VOLTAGE = -0.5;

	private double varLauncherSet = 0.6;

	private TalonSRX stagingWheel;
	private TalonSRX launcher;
	VictorSPX intakeBar;
	DoubleSolenoid hood;
	DoubleSolenoid arm;

	public CargoMechanism(TalonSRX stagingWheel, 
						  TalonSRX launcher, 
						  VictorSPX intakeBar, 
						  DoubleSolenoid hood, 
						  DoubleSolenoid arm) {
		this.stagingWheel = stagingWheel;
		this.launcher = launcher;
		this.intakeBar = intakeBar;
		this.hood = hood;
		this.arm = arm;

		launcher.configVoltageCompSaturation(12.0);
		launcher.enableVoltageCompensation(true);

		SmartDashboard.putNumber("launcher set point", varLauncherSet);
	}

	CargoMechanismModes state = CargoMechanismModes.idle;

	@Override
	public void periodic() {
		
		switch (state) {

			case idle:
				stagingWheelOff();
				launcherOff();
				intakeBarOff();
				intakeArmIn();
				hoodUp();
				break;
			case floorIntakeBarOut:
				stagingWheelOn();
				launcherOff();
				intakeBarOn();
				intakeArmOut();
				hoodUp();
				break;
			case floorIntakeBarIn:
				stagingWheelOn();
				launcherOff();
				intakeBarOff();
				intakeArmIn();
				hoodUp();
				break;
			case midIntake:
				stagingWheelOn();
				launcherOff();
				intakeBarReverse();
				intakeArmOut();
				hoodUp();
				break;
			case hoodIntake:
				stagingWheelOff();
				launcherReverse();
				intakeBarOff();
				intakeArmIn();
				hoodUp();
				break;
			case shootMax:
				stagingWheelOn();
				launcherOnMax();
				intakeBarOff();
				intakeArmIn();
				hoodUp();
				break;
			case shootHigh:
				stagingWheelOn();
				launcherOnHigh();
				intakeBarOff();
				intakeArmIn();
				hoodUp();
				break;
			case shootLow:
				stagingWheelOn();
				launcherOnLow();
				intakeBarOff();
				intakeArmIn();
				hoodDown();
				break;
			case flush:
				stagingWheelReverse();
				launcherReverse();
				intakeBarReverse();
				intakeArmIn();
				hoodUp();
				break;
		}
	}
	
	@Override
	public void idle() {
		state = CargoMechanismModes.idle;
    }
    
	@Override
	public void floorIntakeBarOut() {
		state = CargoMechanismModes.floorIntakeBarOut;
    }
    
	@Override
	public void floorIntakeBarIn() {
		state = CargoMechanismModes.floorIntakeBarIn;
    }
    
	@Override
	public void midIntake() {
		state = CargoMechanismModes.midIntake;
    }
    
	@Override
	public void hoodIntake() {
		state = CargoMechanismModes.hoodIntake;
    }
	
	@Override
	public void shootMax() {
		state = CargoMechanismModes.shootMax;
	}

	@Override
	public void shootHigh() {
		state = CargoMechanismModes.shootHigh;
    }
    
	@Override
	public void shootLow() {
		state = CargoMechanismModes.shootLow;
	}
	
	@Override
	public void flush() {
		state = CargoMechanismModes.flush;
	}

	private void intakeArmIn() {
		arm.set(Value.kReverse);
	}

	private void intakeArmOut() {
		arm.set(Value.kForward);
	}

	private void hoodDown() {
		hood.set(Value.kReverse);
	}

	private void hoodUp() {
		hood.set(Value.kForward);
	}

	private void intakeBarOn() {
		intakeBar.set(ControlMode.PercentOutput, -INTAKE_BAR_PERCENT_VOLTAGE);
	}

	private void intakeBarOff() {
		intakeBar.set(ControlMode.PercentOutput, 0);
	}

	private void intakeBarReverse() {
		intakeBar.set(ControlMode.PercentOutput, INTAKE_BAR_PERCENT_VOLTAGE);
	}

	private void stagingWheelOn() {
		stagingWheel.set(ControlMode.PercentOutput, -STAGING_WHEEL_PERCENT_VOLTAGE);
	}

	private void stagingWheelOff() {
		stagingWheel.set(ControlMode.PercentOutput, 0);
	}

	private void stagingWheelReverse() {
		stagingWheel.set(ControlMode.PercentOutput, STAGING_WHEEL_PERCENT_VOLTAGE);
	}

	private void launcherOnMax() {
		launcher.set(ControlMode.PercentOutput, 1);
	}

	private void launcherOnHigh() {
		double velocity = launcher.getSelectedSensorVelocity();
		SmartDashboard.putNumber("launcher Speed", velocity);
		varLauncherSet = SmartDashboard.getNumber("launcher set point", varLauncherSet);

		launcher.set(ControlMode.PercentOutput, varLauncherSet);

		/*
		if (velocity < varLauncherSet) {
			launcher.set(ControlMode.PercentOutput, 0.8);
		} else {
			launcher.set(ControlMode.PercentOutput, 0);
		}
		*/
	}

	private void launcherOnLow() {
		launcher.set(ControlMode.PercentOutput, LAUNCHER_LOW_PERCENT_VOLTAGE);
	}

	private void launcherOff() {
		launcher.set(ControlMode.PercentOutput, 0);
	}

	private void launcherReverse() {
		launcher.set(ControlMode.PercentOutput, LAUNCHER_REVERSE_PERCENT_VOLTAGE);
	}

}
