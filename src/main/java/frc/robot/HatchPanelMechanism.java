package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import java.util.Map;

import static java.util.Map.entry;

public class HatchPanelMechanism implements HatchPanelMechanismInterface {
	private final IMotorController rollers;
	private final DoubleSolenoid detach;
	private final DoubleSolenoid arm;

	/* //prototype robot
        VictorSPX rollers = new VictorSPX(1);
        DoubleSolenoid arm = new DoubleSolenoid(2, 3);
        DoubleSolenoid detach = new DoubleSolenoid(0, 1);
        // */
	public HatchPanelMechanism(IMotorController rollers, DoubleSolenoid detach, DoubleSolenoid arm) {
		this.rollers = rollers;
		this.detach = detach;
		this.arm = arm;
	}

	private HatchPanelMode hatchPanelMode = HatchPanelMode.stow;

	private final Map<HatchPanelMode, Runnable> hatchModes = Map.ofEntries(
			entry(HatchPanelMode.deposit, this::executeDeposit),
			entry(HatchPanelMode.floorPickup, this::executeFloorPickup),
			entry(HatchPanelMode.stationPickup, this::executeStationPickup),
			entry(HatchPanelMode.stow, this::executeStow)
	);

	@Override
	public void setMode(HatchPanelMode mode) {
		this.hatchPanelMode = mode;
	}

	@Override
	public void floorPickup() {
		this.hatchPanelMode = HatchPanelMode.floorPickup;
	}

	@Override
	public void stow() {
		this.hatchPanelMode = HatchPanelMode.stow;
	}

	@Override
	public void deposit() {
		this.hatchPanelMode = HatchPanelMode.deposit;
	}

	@Override
	public void stationPickup() {
		this.hatchPanelMode = HatchPanelMode.stationPickup;
	}

	@Override
	public void periodic() {
		hatchModes.get(hatchPanelMode).run();
	}

	private void executeStationPickup() {
		armIn();
		rollerReverse();
		detachIn();
	}

	private void executeDeposit() {
		armIn();
		rollerStop();
		detachOut();
	}

	private void executeFloorPickup() {
		armOut();
		rollerIntake();
		detachIn();
	}

	private void executeStow() {
		armIn();
		rollerStop();
		detachIn();
	}

	//Arm Methonds
	public void armNeutral() {
		arm.set(DoubleSolenoid.Value.kOff);
	}

	public void armOut() {
		arm.set(DoubleSolenoid.Value.kForward);
	}

	public void armIn() {
		arm.set(DoubleSolenoid.Value.kReverse);
	}

	//Detach Methonds
	public void detachNeutral() {
		detach.set(DoubleSolenoid.Value.kOff);
	}

	public void detachOut() {
		detach.set(DoubleSolenoid.Value.kForward);
	}

	public void detachIn() {
		detach.set(DoubleSolenoid.Value.kReverse);
	}

	//Rollers
	public void rollerIntake() {
		rollers.set(ControlMode.PercentOutput, 1);
	}

	public void rollerReverse() {
		rollers.set(ControlMode.PercentOutput, -0.8);
	}

	public void rollerStop() {
		rollers.set(ControlMode.PercentOutput, 0);
	}

	public HatchPanelMode getHatchPanelMode() {
		return this.hatchPanelMode;
	}

}