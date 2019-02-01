package frc.robot;

public interface HatchPanelMechanismInterface{
    public void floorPickup();

    public void stow();

    public void deposit(); 

    //  if this needs to be diffrent then stow()
    // public void loadingStationPickup();
}