/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;

import frc.robot.autotasks.TestTask;

/**
 * The doer for auto stuff
 */
public class AutoStuffDoer {

    ArrayList<TaskInterface> taskList = new ArrayList<TaskInterface>();
    /** Keeps track of which task to run */
    int taskCounter;
    /** Keeps track of an individual tasks state */
    int taskState;

    AutoStuffDoer(){
        taskList.add(new TestTask());
    }

    /**
     * should be run once at the begining of auto
     */
    public void autoInit(){
        taskCounter = 0;
        taskState = 0;
        
    }

    /**
     * should be run repediatly during auto
     */
    public void runAuto(){
        if(taskCounter < taskList.size()) {

            TaskInterface currTask = taskList.get(taskCounter);

            if (taskState < 1){
                currTask.start();
                taskState++;
            }
            else {
                currTask.execute();
                taskState++;
            }

            if (currTask.isFinished()){
                currTask.end();
                taskState = 0;
            }
            
        }
    }

}


