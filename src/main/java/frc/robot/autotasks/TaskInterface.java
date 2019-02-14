/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autotasks;

/**
 * interface for Task Objects
 */
public interface TaskInterface {

	/**
	 * Should be called once at the begining of the task
	 */
	public void start();

	/**
	 * Should be called repeatedly while a task is being executed
	 */
	public void execute();

	/**
	 * Should return true when the task is finished
	 *
	 * @return whether the task is finished
	 */
	public boolean isFinished();

	//FIXME would be cool to make this link work:

	/**
	 * Should be run once after {@link isFinished()} returns true
	 */
	public void end();

}

