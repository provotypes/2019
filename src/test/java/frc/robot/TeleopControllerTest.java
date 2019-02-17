package frc.robot;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.first.wpilibj.Compressor;
import frc.robot.autotasks.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

import java.util.function.Supplier;

public class TeleopControllerTest {
	/*CargoMechanismInterface cargo;
	HatchPanelMechanismInterface panel;
    DrivetrainInterface driveTrain;
    
    public Compressor compressor = new Compressor();

    private Extreme3DProJoystick stick;
    private LogitechGamepadController gamepad;
    
    private boolean isHumanControlled;
	private boolean teleControlsBound;
	private TaskInterface visionHatchPlaceRoutine;
	private AutoFactory autoFactory;

	private boolean isCargoForward;

	private double rotateMultiplier = 1;
	private double speedMultiplier = 1;

	private final Supplier<Double> rotateMultiplierSupplier;
	private final Supplier<Double> speedMultiplierSupplier;

	@Before
	public void init() {
		//motorController = mock(IMotorController.class);
		//arm = mock(DoubleSolenoid.class);
		//detach = mock(DoubleSolenoid.class);
	}

	@Test
	public void defaultPeriodic() {

		//HatchPanelMechanism hatchPanelMechanism = new HatchPanelMechanism(motorController, detach, arm);

		// Default mode should be stow
		hatchPanelMechanism.periodic();
		verifyStow(motorController, arm, detach);

		assertThat(hatchPanelMechanism.getHatchPanelMode(), org.hamcrest.CoreMatchers.is(HatchPanelMechanismInterface.HatchPanelMode.stow));
	}

	@Test
	public void stowPeriodic() {

		HatchPanelMechanism hatchPanelMechanism = new HatchPanelMechanism(motorController, detach, arm);

		hatchPanelMechanism.setMode(HatchPanelMechanismInterface.HatchPanelMode.stationPickup);
		hatchPanelMechanism.periodic();
		//verifyStationPickup(motorController, arm, detach);

		hatchPanelMechanism.setMode(HatchPanelMechanismInterface.HatchPanelMode.stow);
		hatchPanelMechanism.periodic();
		verifyStow(motorController, arm, detach);

		assertThat(hatchPanelMechanism.getHatchPanelMode(), org.hamcrest.CoreMatchers.is(HatchPanelMechanismInterface.HatchPanelMode.stow));
	}

	@Test
	public void depositPeriodic() {

		HatchPanelMechanism hatchPanelMechanism = new HatchPanelMechanism(motorController, detach, arm);

		hatchPanelMechanism.setMode(HatchPanelMechanismInterface.HatchPanelMode.deposit);
		hatchPanelMechanism.periodic();
		verifyDeposit(motorController, arm, detach);

		assertThat(hatchPanelMechanism.getHatchPanelMode(), org.hamcrest.CoreMatchers.is(HatchPanelMechanismInterface.HatchPanelMode.deposit));
	}

	@Test
	public void stationPickupPeriodic() {

		HatchPanelMechanism hatchPanelMechanism = new HatchPanelMechanism(motorController, detach, arm);

		hatchPanelMechanism.setMode(HatchPanelMechanismInterface.HatchPanelMode.stationPickup);
		//verifyStationPickup(motorController, arm, detach);
		// Default mode should be stow
		hatchPanelMechanism.periodic();
		//verifyStationPickup(motorController, arm, detach);

		assertThat(hatchPanelMechanism.getHatchPanelMode(), org.hamcrest.CoreMatchers.is(HatchPanelMechanismInterface.HatchPanelMode.stationPickup));
	}

	@Test
	public void floorPickupPeriodic() {

		HatchPanelMechanism hatchPanelMechanism = new HatchPanelMechanism(motorController, detach, arm);

		hatchPanelMechanism.setMode(HatchPanelMechanismInterface.HatchPanelMode.floorPickup);
		hatchPanelMechanism.periodic();
		verifyFloorPickup(motorController, arm, detach);

		assertThat(hatchPanelMechanism.getHatchPanelMode(), org.hamcrest.CoreMatchers.is(HatchPanelMechanismInterface.HatchPanelMode.floorPickup));
	}

	private void verifyFloorPickup(IMotorController motorController, DoubleSolenoid arm, DoubleSolenoid detach) {
		verify(arm, atLeast(1)).set(DoubleSolenoid.Value.kForward);
		verify(detach, atLeast(1)).set(DoubleSolenoid.Value.kReverse);
		verify(motorController, atLeast(1)).set(ControlMode.PercentOutput, 0.5d);
	}

	private void verifyStationPickup(IMotorController motorController, DoubleSolenoid arm, DoubleSolenoid detach) {
		verify(arm, atLeast(1)).set(DoubleSolenoid.Value.kReverse);
		verify(detach, atLeast(1)).set(DoubleSolenoid.Value.kReverse);
		verify(motorController, atLeast(1)).set(ControlMode.PercentOutput, -0.5d);
	}

	private void verifyStow(IMotorController motorController, DoubleSolenoid arm, DoubleSolenoid detach) {
		verify(arm, atLeast(1)).set(DoubleSolenoid.Value.kReverse);
		verify(detach, atLeast(1)).set(DoubleSolenoid.Value.kReverse);
		verify(motorController, atLeast(1)).set(ControlMode.PercentOutput, 0);
	}

	private void verifyDeposit(IMotorController motorController, DoubleSolenoid arm, DoubleSolenoid detach) {
		verify(arm, atLeast(1)).set(DoubleSolenoid.Value.kReverse);
		verify(detach, atLeast(1)).set(DoubleSolenoid.Value.kForward);
		verify(motorController, atLeast(1)).set(ControlMode.PercentOutput, 0);
	}*/
}