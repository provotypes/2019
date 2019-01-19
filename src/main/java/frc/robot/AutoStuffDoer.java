package frc.robot;

import java.util.ArrayList;

import easypath.EasyPath;
import easypath.EasyPathConfig;
import easypath.FollowPath;
import easypath.Path;
import easypath.PathUtil;
import frc.robot.autotasks.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The doer for auto stuff
 */
public class AutoStuffDoer {

    //Auto chooser things
    private static final String kDefaultAuto = "Default";
    private static final String kCustomAuto = "My Auto";
    private String m_autoSelected;
    private final SendableChooser<String> m_chooser = new SendableChooser<>();

    EasyPathConfig pathConfig;

    ArrayList<TaskInterface> taskList = new ArrayList<TaskInterface>();
    /** Keeps track of which task to run */
    int taskCounter;
    /** Keeps track of an individual tasks state */
    int taskState;

    public AutoStuffDoer(DrivetrainInterface dt){
        
        pathConfig = new EasyPathConfig(
            dt::setLeftRightDriveSpeed, 
            dt::getInchesTraveled, 
            dt::getCurrentAngle, 
            dt::resetEncodersAndGyro, 
            0.7
        );

        EasyPath.configure(pathConfig);
        
        m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
        m_chooser.addOption("My Auto", kCustomAuto);
        SmartDashboard.putData("Auto choices", m_chooser);

        
        
        //test path
        taskList.add(new EasyPathTask(new FollowPath(PathUtil.createStraightPath(10), 0.7)));
    }

    /**
     * should be run once at the begining of auto
     */
    public void autoInit(){
        taskCounter = 0;
        taskState = 0;
        
        m_autoSelected = m_chooser.getSelected();
        System.out.println("Auto selected: " + m_autoSelected);

    }

    /**
     * should be run repediatly during auto
     */
    public void runAuto(){

        switch (m_autoSelected) {
            case kCustomAuto:
              // Put custom auto code here
              break;
            case kDefaultAuto:
            default:
              // Put default auto code here
              break;
        }

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
                taskCounter++;
            }
            
        }
    }

}


