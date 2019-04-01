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
	private final DoubleSolenoid claw;

	/* //prototype robot
        VictorSPX rollers = new VictorSPX(1);
        DoubleSolenoid arm = new DoubleSolenoid(2, 3);
        DoubleSolenoid detach = new DoubleSolenoid(0, 1);
        // */
	public HatchPanelMechanism(IMotorController rollers, DoubleSolenoid detach, DoubleSolenoid arm, DoubleSolenoid claw) {
		this.rollers = rollers;
		this.detach = detach;
		this.arm = arm;
		this.claw = claw;
	}

	private HatchPanelMode hatchPanelMode = HatchPanelMode.startingConfig;

	private final Map<HatchPanelMode, Runnable> hatchModes = Map.ofEntries(
			entry(HatchPanelMode.deposit, this::executeDeposit),
			entry(HatchPanelMode.floorPickup, this::executeFloorPickup),
			entry(HatchPanelMode.stationPickup, this::executeStationPickup),
			entry(HatchPanelMode.startingConfig, this::executeStartingConfig),
			entry(HatchPanelMode.holdPanel, this::executeHoldPanel),
			entry(HatchPanelMode.rollersForward, this::executeForwardRollers)
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
	public void startingConfig() {
		this.hatchPanelMode = HatchPanelMode.startingConfig;
	}

	@Override
	public void holdPanel() {
		this.hatchPanelMode = HatchPanelMode.holdPanel;
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
	public void rollersForward() {
		this.hatchPanelMode = HatchPanelMode.rollersForward;
	}

	@Override
	public void periodic() {
		hatchModes.get(hatchPanelMode).run();
	}

	private void executeStationPickup() {
		armIn();
		rollerStop();
		detachIn();
		clawStow();
	}

	private void executeForwardRollers() {
		armIn();
		rollerIntakeSlow();
		detachIn();
		clawStow();
	}

	private void executeDeposit() {
		armIn();
		rollerStop();
		detachOut();
		clawStow();
	}

	private void executeFloorPickup() {
		armOut();
		rollerIntake();
		detachIn();
		clawGrab();
	}

	private void executeStartingConfig() {
		armIn();
		rollerStop();
		detachIn();
		clawStow();
	}

	private void executeHoldPanel() {
		armIn();
		rollerStop();
		detachIn();
		clawGrab();
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

	//Claw
	public void clawNeutral() {
		claw.set(DoubleSolenoid.Value.kOff);
	}

	public void clawGrab() {
		claw.set(DoubleSolenoid.Value.kForward);
	}

	public void clawStow() {
		claw.set(DoubleSolenoid.Value.kReverse);
	}

	//Rollers
	public void rollerIntake() {
		rollers.set(ControlMode.PercentOutput, 1);
	}

	public void rollerIntakeSlow() {
		rollers.set(ControlMode.PercentOutput, 0.5);
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