package frc.robot.autotasks;

import java.util.ArrayList;

import frc.robot.RobotInit;


/**
 * The class to translate the SmartDashboard input to an AutoRoutine object
 */
public class AutoChooser {

	ArrayList<TaskInterface> taskList = new ArrayList<TaskInterface>();

	AutoFactory autoFactory;

	public AutoChooser(AutoFactory factory) {
		autoFactory = factory;
	}

	public AutoRoutine getChosenAuto() {
		String autoSelected = RobotInit.getAutoChooser().getSelected();
		System.out.println("Auto selected: " + autoSelected);

		AutoRoutine chosenRoutine;

		switch (autoSelected) {
			case RobotInit.kRightStartCloseRightBay:
				chosenRoutine = new AutoRoutine(autoFactory.rightStartCloseRightBay());
				break;
			case RobotInit.kRightStartMiddleRightBay:
				chosenRoutine = new AutoRoutine(autoFactory.rightStartMiddleRightBay());
				break;
			case RobotInit.kRightStartFarRightBay:
				chosenRoutine = new AutoRoutine(autoFactory.rightStartFarRightBay());
				break;
			case RobotInit.kMiddleStartRightBay:
				chosenRoutine = new AutoRoutine(autoFactory.middleStartRightBay());
				break;
			case RobotInit.kMiddleStartLeftBay:
				chosenRoutine = new AutoRoutine(autoFactory.middleStartLeftBay());
				break;
			case RobotInit.kLeftStartCloserLeftBay:
				chosenRoutine = new AutoRoutine(autoFactory.leftStartCloserLeftBay());
				break;
			case RobotInit.kLeftStartMiddleLeftBay:
				chosenRoutine = new AutoRoutine(autoFactory.leftStartMiddleLeftBay());
				break;
			case RobotInit.kLeftStartFarLeftBay:
				chosenRoutine = new AutoRoutine(autoFactory.leftStartFarLeftBay());
				break;
			case RobotInit.kCenterBayNoVision:
				chosenRoutine = new AutoRoutine(autoFactory.centerBayNoVision());
				break;
			case RobotInit.kDoNothing:
			default:
				chosenRoutine = new AutoRoutine(autoFactory.emptyList());
				break;
		}

		return chosenRoutine;
	}

}