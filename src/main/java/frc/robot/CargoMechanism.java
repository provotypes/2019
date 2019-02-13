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

/**
 * Add your docs here.
 */
public class CargoMechanism implements CargoMechanismInterface{
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


    @Override
    public void intakeArmSwitch() {
        if (arm.get() == Value.kReverse) {
            arm.set(Value.kForward);
        } else {
            arm.set(Value.kReverse);
        }
    }

    @Override
    public void hoodSwitch() {
        if (hood.get() == Value.kReverse) {
            hood.set(Value.kForward);
        } else {
            hood.set(Value.kReverse);
        }
    }

    @Override
    public boolean hoodState() {
        if (hood.get() == Value.kForward) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean armState() {
        if (arm.get() == Value.kForward) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void intakeBarOn(){
        intakeBar.set(ControlMode.PercentOutput, -0.7);
    }
    @Override
    public void intakeBarOff(){
        intakeBar.set(ControlMode.PercentOutput, 0);
    }
    @Override
    public void intakeBarReverse(){
        intakeBar.set(ControlMode.PercentOutput, 0.7);
    }

    @Override
    public void awkwardThirdWheelOn(){
        awkwardThirdWheel.set(ControlMode.PercentOutput, -0.9);
    }
    @Override
    public void awkwardThirdWheelOff(){
        awkwardThirdWheel.set(ControlMode.PercentOutput, 0);
    }
    @Override
    public void awkwardThirdWheelReverse(){
        awkwardThirdWheel.set(ControlMode.PercentOutput, 0.9);
    }

    @Override
    public void launcherOn(){
        launcher.set(ControlMode.PercentOutput, 0.9);
    }
    @Override
    public void launcherOff(){
        launcher.set(ControlMode.PercentOutput, 0);
    }
    @Override
    public void launcherReverse(){
        launcher.set(ControlMode.PercentOutput, -0.9);
    }

}
