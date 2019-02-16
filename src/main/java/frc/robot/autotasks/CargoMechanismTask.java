package frc.robot.autotasks;

import frc.robot.CargoMechanismInterface;

public class CargoMechanismTask implements TaskInterface {

	private CargoMechanismInterface mechanism;
	private Runnable modeMethod;
	private boolean isFinished;
	private int numTicks;
	private int duration;

	//TODO: make unit of duration variable seconds instead of ticks

	public CargoMechanismTask(CargoMechanismInterface c, Runnable modeMethod, int duration) {
		this.mechanism = c;
		this.modeMethod = modeMethod;
		this.duration = duration;
	}

	@Override
	public void start() {
		numTicks = 0;
	}

	@Override
	public void execute() {
		if (numTicks < duration) {
			modeMethod.run();
			mechanism.periodic();
		} else {
			end();
		}
		numTicks++;
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public void end() {
		mechanism.idle();
		mechanism.periodic();
		isFinished = true;
	}
}