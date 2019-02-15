package frc.robot;

public interface CargoMechanismInterface {

	enum CargoMechanismModes {
		idle,
		floorIntakeBarOut,
		floorIntakeBarIn,
		midIntake,
		hoodIntake,
		shootHigh,
		shootLow,
		flush,
	}

	void intakeBarOn();

	void intakeBarReverse();

	void intakeBarOff();

	void launcherOn();

	void launcherReverse();

	void launcherOff();

	void awkwardThirdWheelOn();

	void awkwardThirdWheelReverse();

	void awkwardThirdWheelOff();

	void intakeArmSwitch();

	void hoodSwitch();

	boolean hoodState();

	boolean armState();
}