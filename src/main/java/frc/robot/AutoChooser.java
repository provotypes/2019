package frc.robot;

import java.util.ArrayList;

import frc.robot.autotasks.*;


/**
 * The class to get the chosen auto 
 */
public class AutoChooser {

    ArrayList<TaskInterface> taskList = new ArrayList<TaskInterface>();

    public static AutoRoutineController getChosenAuto(){
        String autoSelected = RobotInit.getAutoChooser().getSelected();
        System.out.println("Auto selected: " + autoSelected);

        AutoRoutineController chosenRoutine;

        switch (autoSelected) {
            case RobotInit.kCustomAuto:
              chosenRoutine = new AutoRoutineController(AutoFactory.makeAuto1());
              break;
            case RobotInit.kDefaultAuto:
            default:
                chosenRoutine = new AutoRoutineController(AutoFactory.makeAuto1());
              break;
        }
        
        return chosenRoutine;
    }

}


