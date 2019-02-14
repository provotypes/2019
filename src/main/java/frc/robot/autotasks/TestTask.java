/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autotasks;


/**
 * The testing task!
 */
public class TestTask implements TaskInterface {

	private int count;

	/**
	 * Should be called once at the begining of the task
	 */
	public void start() {
		count = 0;
		System.out.println("The testing task has started!");
	}

	/**
	 * Should be called repeatedly while a task is being executed
	 */
	public void execute() {
		count++;
		System.out.println("The testing task has executed " + count + " times!");
	}

	/**
	 * Should return true when the task is finished
	 *
	 * @return whether the task is finished
	 */
	public boolean isFinished() {

		if (count >= 10) {
			System.out.println("The testing task is finished!");
			System.out.println("count = " + count);
			return true;
		} else {
			return false;
		}
	}

	//FIXME would be cool to make this link work:

	/**
	 * should be run once after {@link isFinished()} returns true
	 */
	public void end() {
		System.out.println("The testing task has ended!");
	}

}
