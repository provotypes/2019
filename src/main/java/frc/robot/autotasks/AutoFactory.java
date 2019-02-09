package frc.robot.autotasks;

import java.util.ArrayList;
import java.util.List;

import easypath.FollowPath;
import easypath.PathUtil;
import frc.robot.CargoMechanismInterface;
import frc.robot.DrivetrainInterface;
import frc.robot.HatchPanelMechanismInterface;

import frc.robot.autotasks.EasyPathTask;
import frc.robot.autotasks.HatchMechanismTask;
import frc.robot.autotasks.CargoMechanismTask;

public class AutoFactory {

    DrivetrainInterface drivetrain;
    HatchPanelMechanismInterface hatchPanelMech;
    CargoMechanismInterface cargoMech;

    public AutoFactory(DrivetrainInterface dt, HatchPanelMechanismInterface hp, CargoMechanismInterface cg) {
        this.drivetrain = dt;
        this.hatchPanelMech = hp;
        this.cargoMech = cg;
    }

    public static List<TaskInterface> makeAuto1(){
        List<TaskInterface> taskList = new ArrayList<TaskInterface>();
        taskList.add(new EasyPathTask(new FollowPath(PathUtil.createStraightPath(20), 0.7)));
        return taskList;
    }

}