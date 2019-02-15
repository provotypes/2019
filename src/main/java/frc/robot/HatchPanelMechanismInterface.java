package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public interface HatchPanelMechanismInterface {

	void setMode(HatchPanelMode mode);

	void periodic();

	static HatchPanelMechanismInterface getDefaultHatchPanelMechnism() {
		return new HatchPanelMechanism(
				new VictorSPX(1),
				new DoubleSolenoid(2, 7, 6),
				new DoubleSolenoid(0, 2, 3));
	}

	enum HatchPanelMode {
		floorPickup,
		stow,
		deposit,
		stationPickup,
	}
}