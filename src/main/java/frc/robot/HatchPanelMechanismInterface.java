package frc.robot;

public interface HatchPanelMechanismInterface {
	public void floorPickup();

	public void stow();

	public void deposit();

	public void periodic();

	public void detachOut();

	public void detachIn();

	//  if this needs to be diffrent then stow()
	public void loadingStationPickup();
}