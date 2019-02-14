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
    DoubleSolenoid detach = new DoubleSolenoid(2, 7, 6);
    DoubleSolenoid arm = new DoubleSolenoid(0, 2, 3);

    HatchPanelState state = HatchPanelState.stow;
   
    @Override
    public void periodic() {
       
        switch (state) {
        
        case floorPickup: 
            armOut();
            rollerIntake();
            detachIn();
            break;
        case stow: 
            armIn();
            rollerStop();
            detachIn();
            break;
        case deposit:
            armIn();
            rollerStop();
            detachOut();
            break;
        case stationPickup:
            armIn();
            rollerReverse();
            detachIn();
            break;
       }
    }

    @Override
    public void floorPickup() {
        state = HatchPanelState.floorPickup;
    }

    @Override
    public void stow() {
        state = HatchPanelState.stow;
    }

    @Override
    public void deposit() {
        state = HatchPanelState.deposit;   
    }

    @Override
    public void loadingStationPickup() {
        state = HatchPanelState.stationPickup;
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

    private enum HatchPanelState {
        floorPickup,
        stow,
        deposit,
        stationPickup,
    }

}