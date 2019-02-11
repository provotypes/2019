package frc.robot;

import java.util.ArrayList;

import frc.robot.autotasks.*;

/**
 * The class to translate the SmartDashboard input to an AutoRoutine object 
 */
public class AutoChooser {

    ArrayList<TaskInterface> taskList = new ArrayList<TaskInterface>();

    AutoFactory autoFactory;

    public AutoChooser(AutoFactory factory){
        autoFactory = factory;
    }

    public AutoRoutine getChosenAuto(){
        String autoSelected = RobotInit.getAutoChooser().getSelected();
        System.out.println("Auto selected: " + autoSelected);

        AutoRoutine chosenRoutine;

        switch (autoSelected) {
            case RobotInit.kCustomAuto:
              chosenRoutine = new AutoRoutine(autoFactory.rightStartFarRightBay());
              break;
            case RobotInit.kDefaultAuto:
            default:
                chosenRoutine = new AutoRoutine(autoFactory.straightPath());
              break;
        }
        
        return chosenRoutine;
    }

}