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
public class AutoController {

    //Auto chooser things
    private static final String kDefaultAuto = "Default";
    private static final String kCustomAuto = "My Auto";
    private String m_autoSelected;
    private final SendableChooser<String> m_chooser;

    EasyPathConfig pathConfig;

    ArrayList<TaskInterface> taskList = new ArrayList<TaskInterface>();
    /** Keeps track of which task to run */
    int taskCounter;
    /** Keeps track of an individual tasks state */
    int taskState;

    public AutoController(DrivetrainInterface dt, SendableChooser<String> chooser){

        m_chooser = chooser;

        pathConfig = new EasyPathConfig(
            dt::setLeftRightDriveSpeed, 
            dt::getInchesTraveled, 
            dt::getCurrentAngle, 
            dt::resetEncodersAndGyro, 
            0.05
        );

        EasyPath.configure(pathConfig);
        
        m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
        m_chooser.addOption("My Auto", kCustomAuto);
        //SmartDashboard.putData("Auto choices", m_chooser);



        //test path
        /*
        taskList.add(new EasyPathTask(new FollowPath(PathUtil.createStraightPath(20), 0.7)));
        taskList.add(new EasyPathTask(new FollowPath(PathUtil.createStraightPath(10), 0.7)));
        // */

        taskList.add(new EasyPathTask(new FollowPath(new Path(t ->
		/* {"start":{"x":0,"y":165},"mid1":{"x":46,"y":163},"mid2":{"x":51,"y":224},"end":{"x":100,"y":223}} */
		(-375 * Math.pow(t, 2) + 378 * t + -6) / (255 * Math.pow(t, 2) + -246 * t + 138),
        120.274),
        0.6)));

        taskList.add(new EasyPathTask(new FollowPath(new Path(t ->
		/* {"start":{"x":117,"y":222},"mid1":{"x":60,"y":223},"mid2":{"x":72,"y":165},"end":{"x":12,"y":166}} */
		(354 * Math.pow(t, 2) + -354 * t + 3) / (-423 * Math.pow(t, 2) + 414 * t + -171),
		124.578),
        -0.6)));

        taskList.add(new EasyPathTask(new FollowPath(new Path(t ->
		/* {"start":{"x":0,"y":165},"mid1":{"x":46,"y":165},"mid2":{"x":35,"y":168},"end":{"x":79,"y":167}} */
		(-21 * Math.pow(t, 2) + 18 * t + 0) / (336 * Math.pow(t, 2) + -342 * t + 138),
		79.059),
        0.6)));

        taskList.add(new EasyPathTask(new FollowPath(new Path(t ->
		/* {"start":{"x":83,"y":166},"mid1":{"x":50,"y":167},"mid2":{"x":58,"y":167},"end":{"x":23,"y":165}} */
		(-3 * Math.pow(t, 2) + -6 * t + 3) / (-252 * Math.pow(t, 2) + 246 * t + -99),
		60.054),
        -0.6)));

        taskList.add(new EasyPathTask(new FollowPath(new Path(t ->
		/* {"start":{"x":0,"y":165},"mid1":{"x":46,"y":163},"mid2":{"x":51,"y":224},"end":{"x":100,"y":223}} */
		(-375 * Math.pow(t, 2) + 378 * t + -6) / (255 * Math.pow(t, 2) + -246 * t + 138),
        120.274),
        0.6)));


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


