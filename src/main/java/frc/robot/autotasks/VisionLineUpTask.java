/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autotasks;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
		double diff = -vision.getLineAngle() / 45.0d;
		double turn = diff / 45.0d;
		double drive = 0.4;

		if (Math.abs(turn) > 0.4) {
			turn = Math.copySign(0.4, turn);
			// drive = 0;
		}

		if ((Math.abs(diff) > 1) && (Math.abs(turn) < 0.25)) {
			turn = Math.copySign(0.25, turn);
			// drive = 0.3;
		}

		dt.setArcadeDriveSpeed(drive, turn);
	}

	@Override
	public boolean isFinished() {
		double lineCount = vision.getLineCount();

		if (Double.isNaN(lineCount)) {
			lineCount = 1.0;
		}
		return lineCount < 1;
	}

	@Override
	public void end() {
		dt.setArcadeDriveSpeed(0.0, 0.0);
	}


}


    
