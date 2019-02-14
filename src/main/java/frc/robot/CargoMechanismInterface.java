package frc.robot;

public interface CargoMechanismInterface {

	public void intakeBarOn();

	public void intakeBarReverse();

	public void intakeBarOff();

	public void launcherOn();

	public void launcherReverse();

	public void launcherOff();

	public void awkwardThirdWheelOn();

	public void awkwardThirdWheelReverse();

	public void awkwardThirdWheelOff();

	public void intakeArmSwitch();

	public void hoodSwitch();

	public boolean hoodState();

	public boolean armState();
}