package frc.robot.autotasks;

import frc.robot.HatchPanelMechanismInterface;

public class HatchMechanismTask implements TaskInterface {

	HatchPanelMechanismInterface panel;

	int count;

	public HatchMechanismTask(HatchPanelMechanismInterface p) {
		panel = p;
		count = 0;
	}

	@Override
	public void start() {
		panel.setMode(HatchPanelMechanismInterface.HatchPanelMode.deposit);
	}

	@Override
	public void execute() {
		panel.periodic();
		count++;
	}

	@Override
	public boolean isFinished() {
		return count >= 50;
	}

	@Override
	public void end() {
		panel.setMode(HatchPanelMechanismInterface.HatchPanelMode.holdPanel);
		panel.periodic();
	}

}