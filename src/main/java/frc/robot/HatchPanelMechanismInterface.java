package frc.robot;

public interface HatchPanelMechanismInterface {
	enum HatchPanelState {
		floorPickup,
		stow,
		deposit,
		stationPickup,
	}

	void floorPickup();
	void stow();
	void deposit();
	void periodic();
	void detachOut();
	void detachIn();

	//  if this needs to be diffrent then stow()
	void loadingStationPickup();
}