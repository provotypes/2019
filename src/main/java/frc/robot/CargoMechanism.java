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

/**
 * Add your docs here.
 */
public class CargoMechanism implements CargoMechanismInterface{
    /* //prototype code
    private VictorSP awkwardThirdWheel = new VictorSP(4);
    private VictorSP launcher = new VictorSP(5);
    private TalonSRX intakeBar = new TalonSRX(5);
    // */

    private TalonSRX awkwardThirdWheel = new TalonSRX(6);
    private TalonSRX launcher = new TalonSRX(4);
    VictorSPX intakeBar = new VictorSPX(2);
    DoubleSolenoid hood = new DoubleSolenoid(0, 6, 7);
    DoubleSolenoid arm = new DoubleSolenoid(0, 4, 5);



    @Override
    public void intakeBall(){
        awkwardThirdWheelOn();
        intakeOn();
        
    }
    @Override
    public void intakeBallOff(){
        intakeOff();
        awkwardThirdWheelOff();
    }

    @Override
    public void launchBall(){
        awkwardThirdWheelOn();
        launcherOn();
    }
    @Override
    public void launchBallOff(){
        awkwardThirdWheelOff();
        launcherOff();
    }

    @Override
    public void reverse(){
        launcherReverse();
        awkwardThirdWheelReverse();
        intakeReverse();
    }

    public void intakeOn(){
        intakeBar.set(ControlMode.PercentOutput, 0.7);
    }
    public void intakeOff(){
        intakeBar.set(ControlMode.PercentOutput, 0);
    }
    public void intakeReverse(){
        intakeBar.set(ControlMode.PercentOutput, -0.7);
    }

    public void awkwardThirdWheelOn(){
        awkwardThirdWheel.set(ControlMode.PercentOutput, -0.9);
    }
    public void awkwardThirdWheelOff(){
        awkwardThirdWheel.set(ControlMode.PercentOutput, 0);
    }
    public void awkwardThirdWheelReverse(){
        awkwardThirdWheel.set(ControlMode.PercentOutput, 0.9);
    }

    public void launcherOn(){
        launcher.set(ControlMode.PercentOutput, -0.9);
    }
    public void launcherOff(){
        launcher.set(ControlMode.PercentOutput, 0);
    }
    public void launcherReverse(){
        launcher.set(ControlMode.PercentOutput, 0.9);
    }
}
