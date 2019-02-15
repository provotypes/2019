/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
// import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class CargoMechanism implements CargoMechanismInterface {
    /* //prototype code
    private VictorSP awkwardThirdWheel = new VictorSP(4);
    private VictorSP launcher = new VictorSP(5);
    private TalonSRX intakeBar = new TalonSRX(5);
    // */

	private TalonSRX awkwardThirdWheel = new TalonSRX(4);
	private TalonSRX launcher = new TalonSRX(6);
	VictorSPX intakeBar = new VictorSPX(2);
	DoubleSolenoid hood = new DoubleSolenoid(0, 7, 6);
	DoubleSolenoid arm = new DoubleSolenoid(0, 4, 5);

	CargoMechanismModes state = CargoMechanismModes.idle;
	
	public void idle(){

    }
    
    public void floorIntakeBarOut(){

    }
    
    public void floorIntakeBarIn(){

    }
    
    public void midIntake(){

    }
    
    public void stationIntake(){

    }
    
    public void hoodIntake(){

    }
    
    public void shootHigh(){
	
    }
    
    public void shootLow(){

	}
	
	public void flush(){

	}

	@Override
	public void intakeArmIn() {
		arm.set(Value.kReverse);
	}

	@Override
	public void intakeArmOut() {
		arm.set(Value.kForward);
	}

	@Override
	public void hoodOut(){
		hood.set(Value.kForward);
	}

	@Override
	public void hoodIn(){
		hood.set(Value.kReverse);
	}

	@Override
	public void intakeBarOn() {
		intakeBar.set(ControlMode.PercentOutput, -0.7);
	}

	@Override
	public void intakeBarOff() {
		intakeBar.set(ControlMode.PercentOutput, 0);
	}

	@Override
	public void intakeBarReverse() {
		intakeBar.set(ControlMode.PercentOutput, 0.7);
	}

	@Override
	public void awkwardThirdWheelOn() {
		awkwardThirdWheel.set(ControlMode.PercentOutput, -0.9);
	}

	@Override
	public void awkwardThirdWheelOff() {
		awkwardThirdWheel.set(ControlMode.PercentOutput, 0);
	}

	@Override
	public void awkwardThirdWheelReverse() {
		awkwardThirdWheel.set(ControlMode.PercentOutput, 0.9);
	}

	@Override
	public void launcherOn() {
		launcher.set(ControlMode.PercentOutput, SmartDashboard.getNumber("launcher speed", 0));
		// launcher.set(ControlMode.Velocity, -10000);
		// launcher.set(ControlMode.MotionMagic, demand);
	}

	@Override
	public void launcherOff() {
		launcher.set(ControlMode.PercentOutput, 0);
	}

	@Override
	public void launcherReverse() {
		launcher.set(ControlMode.PercentOutput, -0.5);
	}

}
