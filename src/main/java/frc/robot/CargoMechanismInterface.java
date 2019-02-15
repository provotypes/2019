package frc.robot;

public interface CargoMechanismInterface {

	enum CargoMechanismModes {
		idle,
		hoodIntake,
		floorIntakeBarOut,
		floorIntakeBarIn,
		midIntake,
		shootHigh,
		shootLow,
		flush,
	}
	
	void periodic();
	void idle();    
    void floorIntakeBarOut();    
    void floorIntakeBarIn();    
    void midIntake();    
    void hoodIntake();    
    void shootHigh();    
    void shootLow();	
	void flush();
}