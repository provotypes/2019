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


public class HatchPanelMechanism implements HatchPanelMechanismInterface { 
    /* //prototype robot
    VictorSPX rollers = new VictorSPX(1);
    DoubleSolenoid arm = new DoubleSolenoid(2, 3);
    DoubleSolenoid detach = new DoubleSolenoid(0, 1);
    // */

    VictorSPX rollers = new VictorSPX(1);
    DoubleSolenoid detach = new DoubleSolenoid(2, 6, 7);
    DoubleSolenoid arm = new DoubleSolenoid(0, 2, 3);


    States state = States.stow;
   

    @Override
    public void periodic() {
       
        switch (state) {
       
        case floorPickup : 
            armOut();
            rollerIntake();
            detachIn();
            break;
        case stow : 
            armIn();
            rollerStop();
            detachIn();
            break;
        case deposit:
            armIn();
            rollerStop();
            detachOut();
       }
    }

    @Override
    public void floorPickup() {
        state = States.floorPickup;
    }

    @Override
    public void stow() {
        state = States.stow;
    }

    @Override
    public void deposit() {
        state = States.deposit;   
    }
 
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

    private enum States{
        floorPickup,
        stow,
        deposit,
    }

}