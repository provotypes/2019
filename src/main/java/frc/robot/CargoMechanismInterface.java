package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public interface CargoMechanismInterface {

	static CargoMechanismInterface getDefaulCargoMechanism(){
		return new CargoMechanism(
			new TalonSRX(4),
			new TalonSRX(6),
			new VictorSPX(2),
			new DoubleSolenoid(0, 7, 6),
			new DoubleSolenoid(0, 4, 5));
			
	}
		
	enum CargoMechanismModes {
		idle,
		hoodIntake,
		floorIntakeBarOut,
		floorIntakeBarIn,
		midIntake,
		shootHigh,
		shootMax,
		shootLow,
		flush,
	}
	
	void periodic();
	void idle();    
    void floorIntakeBarOut();    
    void floorIntakeBarIn();    
    void midIntake();    
	void hoodIntake();
	void shootMax();    
    void shootHigh();    
    void shootLow();	
	void flush();
}