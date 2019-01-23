/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;


public class HatchPanelMechanism {
   
    DoubleSolenoid arm = new DoubleSolenoid(1, 2);

    //Arm Methonds
    public void armNeutral(){
        arm.set(DoubleSolenoid.Value.kOff);
    }
    
    public void armOut(){    
        arm.set(DoubleSolenoid.Value.kForward);
    }
    
    public void armIn(){
        arm.set(DoubleSolenoid.Value.kReverse);
    }

    DoubleSolenoid detach = new DoubleSolenoid(1, 2);

    //Detach Methonds
    public void detachNeutral(){
        detach.set(DoubleSolenoid.Value.kOff);
    }
    
    public void detachOut(){    
        detach.set(DoubleSolenoid.Value.kForward);
    }
    
    public void detachIn(){
        detach.set(DoubleSolenoid.Value.kReverse); 
    
    
    }

}