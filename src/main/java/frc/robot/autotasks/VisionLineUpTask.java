/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autotasks;

import frc.robot.DrivetrainInterface;
import frc.robot.VisionCom;

/**
 * Add your docs here.
 */
public class VisionLineUpTask implements TaskInterface {
	private DrivetrainInterface dt;
	private VisionCom vision;

	public VisionLineUpTask(DrivetrainInterface d, VisionCom v) {
		dt = d;
		vision = v;
	}

	@Override
	public void start() {
	}

	@Override
	public void execute() {
		double turn = -vision.getLineAngle() / 45;
		if (turn > 0.7) {
			turn = 0.7;
		} else if (turn < -0.7) {
			turn = -0.7;
		}

		dt.setArcadeDriveSpeed(0.67, turn);
	}

	@Override
	public boolean isFinished() {
		/*
		double lineCount = vision.getLineCount();

		if (Double.isNaN(lineCount)) {
			lineCount = 1.0;
		}
		return lineCount < 1;
		*/
		return true;
	}

	@Override
	public void end() {
		dt.setArcadeDriveSpeed(0.0, 0.0);
	}


}


    
