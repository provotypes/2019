/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;


public class HatchPanelMechanism {
   
    DoubleSolenoid arm = new DoubleSolenoid(1, 2);

    // This is the method that Brennon said we don't really need. I guess I'll just leave it for now.
    public void armNeutral(){
        arm.set(DoubleSolenoid.Value.kOff);
    }
    
    public void armOut(){    
        arm.set(DoubleSolenoid.Value.kForward);
    }
    
    public void armIn(){
        arm.set(DoubleSolenoid.Value.kReverse);
    }

}