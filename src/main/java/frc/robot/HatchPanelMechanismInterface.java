package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public interface HatchPanelMechanismInterface {

	void setMode(HatchPanelMode mode);

	void floorPickup();

	void stow();

	void deposit();

	void stationPickup();

	void rollersForward();

	void periodic();

	static HatchPanelMechanismInterface getDefaultHatchPanelMechanism() {
		return new HatchPanelMechanism(
				new VictorSPX(1),
				new DoubleSolenoid(2, 6, 7),
				new DoubleSolenoid(0, 2, 3));
	}

	enum HatchPanelMode {
		floorPickup,
		stow,
		deposit,
		stationPickup,
		rollersForward,
	}
}