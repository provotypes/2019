/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;


public class HatchPanelMechanism implements HatchPanelMechanismInterface{ 
   
    VictorSPX rollers = new VictorSPX(1);

    DoubleSolenoid arm = new DoubleSolenoid(2, 3);

    DoubleSolenoid detach = new DoubleSolenoid(1, 0);



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

    //Rollers
    public void rollerIntake(){
        rollers.set(ControlMode.PercentOutput, 0.5);
    }

    public void rollerReverse(){
        rollers.set(ControlMode.PercentOutput, -0.5);
    }

    public void rollerStop(){
        rollers.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public void floorPickup() {
        armOut();
        rollerIntake();
        detachIn();
    }

    @Override
    public void stow() {
        armIn();
        rollerStop();
        detachNeutral();
    }

    @Override
    public void deposit() {
        armIn();
        rollerStop();
        detachOut();
        
    }
 

}