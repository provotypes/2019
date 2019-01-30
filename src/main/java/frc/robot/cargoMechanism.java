/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.VictorSP;

/**
 * Add your docs here.
 */
public class cargoMechanism {
    //prototype code
    private VictorSP awkwardThirdWheel = new VictorSP(4);
    private VictorSP launcher = new VictorSP(5);
    private TalonSRX intakeBar = new TalonSRX(5);


    public void intakeBall(){
        intakeOn();
        awkwardThirdWheelOn();
    }
    public void intakeBallOff(){
        intakeOff();
        awkwardThirdWheelOff();
    }

    public void launchBall(){
        awkwardThirdWheelOn();
        launcherOn();
    }
    public void launchBallOff(){
        awkwardThirdWheelOff();
        launcherOff();
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
        awkwardThirdWheel.set(0.9);
    }
    public void awkwardThirdWheelOff(){
        awkwardThirdWheel.set(0);
    }
    public void awkwardThirdWheelReverse(){
        awkwardThirdWheel.set(-0.9);
    }

    public void launcherOn(){
        launcher.set(0.7);
    }
    public void launcherOff(){
        launcher.set(0);
    }
    public void launcherReverse(){
        launcher.set(-0.7);
    }
}
