package frc.robot.autotasks;

import java.util.ArrayList;
import java.util.List;

import easypath.FollowPath;
import easypath.Path;
import easypath.PathUtil;

import frc.robot.CargoMechanismInterface;
import frc.robot.DrivetrainInterface;
import frc.robot.HatchPanelMechanismInterface;
import frc.robot.VisionCom;

public class AutoFactory {

	DrivetrainInterface drivetrain;
	HatchPanelMechanismInterface hatchPanelMech;
	CargoMechanismInterface cargoMech;
	VisionCom vision;

	public AutoFactory(DrivetrainInterface dt, HatchPanelMechanismInterface hp, CargoMechanismInterface cg, VisionCom v) {
		this.drivetrain = dt;
		this.hatchPanelMech = hp;
		this.cargoMech = cg;
		this.vision = v;

	}

	public List<TaskInterface> straightPath(double inches) {
		List<TaskInterface> taskList = new ArrayList<TaskInterface>();
		taskList.add(new EasyPathTask(new FollowPath(PathUtil.createStraightPath(inches), -0.4)));
		// taskList.add(new EasyPathTask(new FollowPath(PathUtil.createStraightPath(20), 0.7)));
		return taskList;
	}

	public List<TaskInterface> centerBayNoVision() {
		List<TaskInterface> taskList = new ArrayList<TaskInterface>();
		taskList.add(new EasyPathTask(new FollowPath(PathUtil.createStraightPath(160), -0.4)));
		taskList.add(new HatchMechanismTask(hatchPanelMech));
		taskList.add(new EasyPathTask(new FollowPath(PathUtil.createStraightPath(20), 0.7)));
		return taskList;
	}

	public List<TaskInterface> rightStartCloseRightBay() {
		// Right side start - Close right bay.
		List<TaskInterface> taskList = new ArrayList<TaskInterface>();

		taskList.add(new EasyPathTask(new FollowPath(new Path(t ->
				/*{"start":{"x":94,"y":208},"mid1":{"x":179,"y":214},"mid2":{"x":267,"y":300},"end":{"x":260,"y":229}} */
				(-711 * Math.pow(t, 2) + 480 * t + 18) / (-294 * Math.pow(t, 2) + 18 * t + 255),
				196.337),
				-0.7)));

		taskList.add(new VisionLineUpTask(drivetrain, vision));
		taskList.add(new HatchMechanismTask(hatchPanelMech));
		taskList.add(new EasyPathTask(new FollowPath(PathUtil.createStraightPath(20), 0.7)));
		return taskList;
	}

	public List<TaskInterface> rightStartMiddleRightBay() {
		// Right side start - Middle right bay.

		List<TaskInterface> taskList = new ArrayList<TaskInterface>();
		taskList.add(new EasyPathTask(new FollowPath(new Path(t ->
				/*{"start":{"x":94,"y":208},"mid1":{"x":233,"y":213},"mid2":{"x":275,"y":279},"end":{"x":280,"y":226}} */
				(-540 * Math.pow(t, 2) + 366 * t + 15) / (180 * Math.pow(t, 2) + -582 * t + 417),
				204.813),
				0.7)));

		taskList.add(new VisionLineUpTask(drivetrain, vision));
		taskList.add(new HatchMechanismTask(hatchPanelMech));
		taskList.add(new EasyPathTask(new FollowPath(PathUtil.createStraightPath(20), 0.7)));
		return taskList;
	}

	public List<TaskInterface> rightStartFarRightBay() {
		//Right side start - Far right bay.

		List<TaskInterface> taskList = new ArrayList<TaskInterface>();
		// taskList.add(new EasyPathTask(new FollowPath(new Path(t ->
		// 		/* {"start":{"x":94,"y":208},"mid1":{"x":266,"y":199},"mid2":{"x":279,"y":278},"end":{"x":302,"y":227}} */
		// 		(-654 * Math.pow(t, 2) + 528 * t + -27) / (507 * Math.pow(t, 2) + -954 * t + 516),
		// 		222.275),
		// 		0.7)));

		taskList.add(new EasyPathTask(new FollowPath(new Path(t -> 
		/* {"start":{"x":87,"y":213},"mid1":{"x":144,"y":215},"mid2":{"x":323,"y":347},"end":{"x":319,"y":210}} */
		(-1197 * Math.pow(t, 2) + 780 * t + 6) / (-915 * Math.pow(t, 2) + 732 * t + 171),
		284.933),
				-0.4)));

				
		// taskList.add(new VisionLineUpTask(drivetrain, vision));
		taskList.add(new HatchMechanismTask(hatchPanelMech));
		taskList.add(new EasyPathTask(new FollowPath(PathUtil.createStraightPath(20), 0.7)));

		return taskList;
	}

	public List<TaskInterface> middleStartRightBay() {
		//Middle Start - Right bay.

		List<TaskInterface> taskList = new ArrayList<TaskInterface>();
		taskList.add(new EasyPathTask(new FollowPath(new Path(t ->
				/* {"start":{"x":97,"y":169},"mid1":{"x":115,"y":172},"mid2":{"x":166,"y":173},"end":{"x":190,"y":173}} */
				(3 * Math.pow(t, 2) + -12 * t + 9) / (-180 * Math.pow(t, 2) + 198 * t + 54),
				93.143),
				0.7)));

		taskList.add(new VisionLineUpTask(drivetrain, vision));
		taskList.add(new HatchMechanismTask(hatchPanelMech));
		taskList.add(new EasyPathTask(new FollowPath(PathUtil.createStraightPath(20), 0.7)));

		return taskList;
	}

	public List<TaskInterface> middleStartLeftBay() {
		//Middle Stat - Left bay.

		List<TaskInterface> taskList = new ArrayList<TaskInterface>();
		taskList.add(new EasyPathTask(new FollowPath(new Path(t ->
				/* {"start":{"x":95,"y":171},"mid1":{"x":128,"y":159},"mid2":{"x":156,"y":156},"end":{"x":187,"y":152}} */
				(-30 * Math.pow(t, 2) + 54 * t + -36) / (24 * Math.pow(t, 2) + -30 * t + 99),
				94.171),
				0.7)));

		taskList.add(new VisionLineUpTask(drivetrain, vision));
		taskList.add(new HatchMechanismTask(hatchPanelMech));
		taskList.add(new EasyPathTask(new FollowPath(PathUtil.createStraightPath(20), 0.7)));

		return taskList;
	}

	public List<TaskInterface> leftStartCloserLeftBay() {
		//Left Side Start - Closer Left Bay.

		List<TaskInterface> taskList = new ArrayList<TaskInterface>();
		taskList.add(new EasyPathTask(new FollowPath(new Path(t ->
				/* {"start":{"x":95,"y":115},"mid1":{"x":186,"y":115},"mid2":{"x":262,"y":52},"end":{"x":258,"y":107}} */
				(543 * Math.pow(t, 2) + -378 * t + 0) / (-195 * Math.pow(t, 2) + -90 * t + 273),
				182.969),
				0.7)));

		taskList.add(new VisionLineUpTask(drivetrain, vision));
		taskList.add(new HatchMechanismTask(hatchPanelMech));
		taskList.add(new EasyPathTask(new FollowPath(PathUtil.createStraightPath(20), 0.7)));

		return taskList;
	}

	public List<TaskInterface> leftStartMiddleLeftBay() {
		//Left Side Start- Middle Left Bay.

		List<TaskInterface> taskList = new ArrayList<TaskInterface>();
		taskList.add(new EasyPathTask(new FollowPath(new Path(t ->
				/* {"start":{"x":95,"y":115},"mid1":{"x":238,"y":126},"mid2":{"x":282,"y":44},"end":{"x":282,"y":107}} */
				(714 * Math.pow(t, 2) + -558 * t + 33) / (165 * Math.pow(t, 2) + -594 * t + 429),
				210.089),
				0.7)));

		taskList.add(new VisionLineUpTask(drivetrain, vision));
		taskList.add(new HatchMechanismTask(hatchPanelMech));
		taskList.add(new EasyPathTask(new FollowPath(PathUtil.createStraightPath(20), 0.7)));

		return taskList;
	}

	public List<TaskInterface> leftStartFarLeftBay() {
		//Left Side Start - Far Left Bay.

		List<TaskInterface> taskList = new ArrayList<TaskInterface>();
		taskList.add(new EasyPathTask(new FollowPath(new Path(t ->
				/* {"start":{"x":95,"y":115},"mid1":{"x":253,"y":134},"mid2":{"x":311,"y":37},"end":{"x":303,"y":107}} */
				(849 * Math.pow(t, 2) + -696 * t + 57) / (102 * Math.pow(t, 2) + -600 * t + 474),
				235.634),
				0.7)));

		taskList.add(new VisionLineUpTask(drivetrain, vision));
		taskList.add(new HatchMechanismTask(hatchPanelMech));
		taskList.add(new EasyPathTask(new FollowPath(PathUtil.createStraightPath(20), 0.7)));

		return taskList;
	}

	public List<TaskInterface> visionHatchPlace() {
		List<TaskInterface> taskList = new ArrayList<TaskInterface>();

		taskList.add(new VisionLineUpTask(drivetrain, vision));
		taskList.add(new HatchMechanismTask(hatchPanelMech));
		taskList.add(new EasyPathTask(new FollowPath(PathUtil.createStraightPath(20), 0.7)));

		return taskList;
	}

	public List<TaskInterface> emptyList() {
		List<TaskInterface> taskList = new ArrayList<TaskInterface>();
		taskList.add(new EmptyTask(hatchPanelMech));
		return taskList;
		
	}

}